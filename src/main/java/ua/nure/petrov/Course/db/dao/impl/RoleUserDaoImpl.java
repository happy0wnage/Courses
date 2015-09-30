package ua.nure.petrov.Course.db.dao.impl;

import ua.nure.petrov.Course.db.MySqlConnection;
import ua.nure.petrov.Course.db.dao.DAO;
import ua.nure.petrov.Course.db.dao.RoleDao;
import ua.nure.petrov.Course.db.dao.RoleUserDao;
import ua.nure.petrov.Course.db.dao.UserDao;
import ua.nure.petrov.Course.db.entity.constants.Fields;
import ua.nure.petrov.Course.db.entity.constants.Query;
import ua.nure.petrov.Course.db.entity.db.Role;
import ua.nure.petrov.Course.db.entity.db.RoleUser;
import ua.nure.petrov.Course.db.entity.db.User;
import ua.nure.petrov.Course.db.entity.exception.DBLayerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 29.07.2015.
 */
public class RoleUserDaoImpl extends DAO implements RoleUserDao {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public void insertRoleUser(RoleUser ru) {
        try {
            con = MySqlConnection.getWebConnection();
            int k = 1;
            pst = con.prepareStatement(Query.INSERT_ROLEUSER);
            pst.setInt(k++, ru.getRole().getId());
            pst.setInt(k, ru.getUser().getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("UserRole " + ru + "wasn't inserted", e);
        } finally {
            commit(con);
        }
    }

    @Override
    public void deleteRoleUserByIdUser(int idUser) {
        try {
            con = MySqlConnection.getWebConnection();
            int k = 1;
            pst = con.prepareStatement(Query.DELETE_ROLEUSER_BY_ID_USER);
            pst.setInt(k, idUser);
            pst.executeUpdate();
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("User with id " + idUser + " wasn't deleted", e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<RoleUser> getRoleUserByIdUser(int idUser) {

        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_ROLEUSER_BY_ID_USER);
            pst.setInt(FIRST, idUser);
            rs = pst.executeQuery();
            List<RoleUser> roleUsers = new ArrayList<RoleUser>();
            while (rs.next()) {
                roleUsers.add(extractRoleUser(rs));
            }
            return roleUsers;
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get RoleUser with idUser = " + idUser, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<RoleUser> getRoleUserByIdRole(int idRole) {
        List<RoleUser> roleUsers = new ArrayList<RoleUser>();

        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_ROLEUSER_BY_ID_ROLE);
            pst.setInt(FIRST, idRole);
            rs = pst.executeQuery();
            while (rs.next()) {
                roleUsers.add(extractRoleUser(rs));
            }
            return roleUsers;
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Filed to get RoleUser with idRole = " + idRole, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<RoleUser> getAllRoleUsers() {
        List<RoleUser> roleUsers = new ArrayList<RoleUser>();

        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_ALL_ROLE_USERS);
            rs = pst.executeQuery();
            while (rs.next()) {
                roleUsers.add(extractRoleUser(rs));
            }
            return roleUsers;
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Filed to get all RoleUser", e);
        } finally {
            commit(con);
        }
    }


    private RoleUser extractRoleUser(ResultSet rs) throws SQLException {
        RoleUser roleUser = new RoleUser();
        User user = userDao.getUserById(rs.getInt(Fields.ROLEUSER_ID_USER));

        RoleDao roleDao = new RoleDaoImpl();
        Role role = roleDao.getRoleById(rs.getInt(Fields.ROLEUSER_ID_ROLE));

        roleUser.setRole(role);
        roleUser.setUser(user);
        return roleUser;
    }


}
