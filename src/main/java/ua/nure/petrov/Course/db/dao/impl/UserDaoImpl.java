package ua.nure.petrov.Course.db.dao.impl;

import ua.nure.petrov.Course.db.MySqlConnection;
import ua.nure.petrov.Course.db.dao.DAO;
import ua.nure.petrov.Course.db.dao.UserDao;
import ua.nure.petrov.Course.db.entity.constants.Fields;
import ua.nure.petrov.Course.db.entity.constants.Query;
import ua.nure.petrov.Course.db.entity.db.Role;
import ua.nure.petrov.Course.db.entity.db.User;
import ua.nure.petrov.Course.db.entity.exception.DBLayerException;
import ua.nure.petrov.Course.db.entity.exception.DataRepeatException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 29.07.2015.
 */
public class UserDaoImpl extends DAO implements UserDao {

    @Override
    public User getUserById(int id) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_USER_BY_ID);
            pst.setInt(FIRST, id);
            rs = pst.executeQuery();
            rs.relative(FIRST);
            return extractUser(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get User with id = " + id, e);
        } finally {
            commit(con);
        }
    }


    @Override
    public User getUserByLogin(String login) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_USER_BY_LOGIN);
            pst.setString(FIRST, login);
            rs = pst.executeQuery();
            rs.relative(FIRST);
            return extractUser(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Invalid login " + login, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public User getUserByStudentCard(String studentCard) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_USER_BY_CARD);
            pst.setString(FIRST, studentCard);
            rs = pst.executeQuery();
            rs.relative(FIRST);
            return extractUser(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get User with Student card = " + studentCard, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<User> getUserByIdCourse(int idCourse) {
        List<User> users = new ArrayList<>();

        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_USER_BY_ID_COURSE);
            pst.setInt(FIRST, idCourse);
            rs = pst.executeQuery();
            while (rs.next()) {
                users.add(extractUser(rs));
            }
            return users;
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get User by idCourse = " + idCourse, e);
        } finally {
            commit(con);
        }
    }


    @Override
    public void updateUser(User user) {
        try {
            con = MySqlConnection.getWebConnection();
            int k = 1;
            pst = con.prepareStatement(Query.UPDATE_USER);
            pst.setString(k++, user.getFirstName());
            pst.setString(k++, user.getSecondName());
            pst.setString(k++, user.getMiddleName());
            pst.setString(k++, user.getLogin());
            pst.setString(k++, user.getPassword());
            pst.setString(k++, user.getSalt());
            pst.setString(k++, user.getEmail());
            pst.setString(k++, user.getPhone());
            pst.setString(k++, user.getPhoto());
            pst.setString(k++, user.getStudentCardNumber());
            pst.setBoolean(k++, user.isStatusBlocked());
            pst.setInt(k, user.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to update User " + user, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public void insertRoleUser(User user, List<Role> roles) {
        try {
            con = MySqlConnection.getWebConnection();
            String generatedColumns[] = {Fields.USER_ID_USER};
            int k = 1;
            pst = con.prepareStatement(Query.INSERT_USER, generatedColumns);
            pst.setString(k++, user.getFirstName());
            pst.setString(k++, user.getSecondName());
            pst.setString(k++, user.getMiddleName());
            pst.setString(k++, user.getLogin());
            pst.setString(k++, user.getPassword());
            pst.setString(k++, user.getSalt());
            pst.setString(k++, user.getEmail());
            pst.setString(k++, user.getPhone());
            pst.setString(k++, user.getPhoto());
            pst.setString(k++, user.getStudentCardNumber());
            pst.setBoolean(k, user.isStatusBlocked());
            pst.executeUpdate();

            rs = pst.getGeneratedKeys();
            rs.next();
            int idUser = rs.getInt(FIRST);

            pst = con.prepareStatement(Query.INSERT_ROLEUSER);

            for (Role role : roles) {
                k = 1;
                pst.setInt(k++, role.getId());
                pst.setInt(k, idUser);
                pst.executeUpdate();
            }

        } catch (SQLException e) {

            rollBack(con);
            throw new DBLayerException("Other type of error" + e);
        } finally {
            commit(con);
        }
    }

    @Override
    public void deleteUserById(int id) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.DELETE_USER_BY_ID);
            pst.setInt(FIRST, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to delete User", e);
        } finally {
            commit(con);
        }
    }

    @Override
    public User getUserByLoginPassword(User user) {
        user = new User();
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_USER_BY_LOGIN_PASSWORD);
            int k = 1;
            pst.setString(k++, user.getLogin());
            pst.setString(k, user.getPassword());
            rs = pst.executeQuery();
            rs.relative(FIRST);
            return extractUser(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Matches login and password not found", e);
        } finally {
            commit(con);
        }
    }

    @Override
    public void checkForMatches(User user) {

        List<User> users = new ArrayList<>();

        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_USER_BY_LOGIN_EMAIL_CARD_PHONE);
            int k = 1;
            pst.setString(k++, user.getLogin());
            pst.setString(k++, user.getEmail());
            pst.setString(k++, user.getStudentCardNumber());
            pst.setString(k++, user.getPhone());
            rs = pst.executeQuery();

            while (rs.next()) {
                users.add(extractUser(rs));
            }

            for (User u : users) {
                if (u.getLogin().equals(user.getLogin())) {
                    throw new DataRepeatException("User with this login is already exists");
                }
                if (u.getEmail().equals(user.getEmail())) {
                    throw new DataRepeatException("User with this email is already exists");
                }
                if (u.getStudentCardNumber().equals(user.getStudentCardNumber())) {
                    throw new DataRepeatException("User with this student card number is already exists");
                }
                if (u.getPhone().equals(user.getPhone())) {
                    throw new DataRepeatException("User with this phone number is already exists");
                }
            }
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Matches login and password not found", e);
        } finally {
            commit(con);
        }
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User(rs.getInt(Fields.USER_ID_USER));
        user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
        user.setSecondName(rs.getString(Fields.USER_SECOND_NAME));
        user.setMiddleName(rs.getString(Fields.USER_MIDDLE_NAME));
        user.setLogin(rs.getString(Fields.USER_LOGIN));
        user.setPassword(rs.getString(Fields.USER_PASSWORD));
        user.setSalt(rs.getString(Fields.USER_SALT));
        user.setEmail(rs.getString(Fields.USER_EMAIL));
        user.setPhone(rs.getString(Fields.USER_PHONE));
        user.setPhoto(rs.getString(Fields.USER_PHOTO));
        user.setStudentCardNumber(rs.getString(Fields.USER_STUDENT_CARD_NUMBER));
        user.setStatusBlocked(rs.getBoolean(Fields.USER_STATUS_BLOCKED));
        return user;
    }
}
