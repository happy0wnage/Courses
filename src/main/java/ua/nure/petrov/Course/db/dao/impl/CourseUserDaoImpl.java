package ua.nure.petrov.Course.db.dao.impl;

import ua.nure.petrov.Course.db.MySqlConnection;
import ua.nure.petrov.Course.db.dao.CourseDao;
import ua.nure.petrov.Course.db.dao.CourseUserDao;
import ua.nure.petrov.Course.db.dao.DAO;
import ua.nure.petrov.Course.db.dao.UserDao;
import ua.nure.petrov.Course.db.entity.constants.Fields;
import ua.nure.petrov.Course.db.entity.constants.Query;
import ua.nure.petrov.Course.db.entity.db.Course;
import ua.nure.petrov.Course.db.entity.db.CourseUser;
import ua.nure.petrov.Course.db.entity.db.User;
import ua.nure.petrov.Course.db.entity.exception.DBLayerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 29.07.2015.
 */
public class CourseUserDaoImpl extends DAO implements CourseUserDao {

    private UserDao userDao = new UserDaoImpl();
    private CourseDao courseDao = new CourseDaoImpl();

    @Override
    public void insertCourseUser(CourseUser cu) {
        try {
            con = MySqlConnection.getWebConnection();
            int k = 1;
            pst = con.prepareStatement(Query.INSERT_COURSEUSER);
            pst.setInt(k++, cu.getCourse().getId());
            pst.setInt(k, cu.getUser().getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to insert CourseUser " + cu, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public void deleteCourseUserByIdUser(int idUser) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.DELETE_COURSEUSER_BY_ID_USER);
            pst.setInt(FIRST, idUser);
            pst.executeUpdate();

        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to delete CourseUser by idUser = " + idUser, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public void deleteCourseUserByIdCourseAndIdUser(int idUser, int idCourse) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.DELETE_COURSEUSER_BY_ID_COURSE_AND_ID_USER);
            int k = 1;
            pst.setInt(k++, idCourse);
            pst.setInt(k, idUser);
            pst.executeUpdate();

        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to delete CourseUser by idUser and idCourse = " + idCourse, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public void deleteCourseUserByIdCourse(int idCourse) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.DELETE_COURSEUSER_BY_ID_COURSE);
            pst.setInt(FIRST, idCourse);
            pst.executeUpdate();

            pst = con.prepareStatement(Query.DELETE_COURSE_BY_ID);
            pst.setInt(FIRST, idCourse);
            pst.executeUpdate();

        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to delete CourseUser by idCourse = " + idCourse, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<CourseUser> getCourseUserByIdUser(int idUser) {

        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_COURSEUSER_BY_ID_USER);
            pst.setInt(FIRST, idUser);
            rs = pst.executeQuery();
            return getCourseUserFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get CourseUsers with idUser = " + idUser, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<CourseUser> getCourseUserByIdCourse(int idCourse) {

        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_COURSEUSER_BY_ID_COURSE);
            pst.setInt(FIRST, idCourse);
            rs = pst.executeQuery();
            return getCourseUserFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get CourseUsers with idCourse = " + idCourse, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<CourseUser> getAllCourseUsers() {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_ALL_COURSEUSERS);
            rs = pst.executeQuery();
            return getCourseUserFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get CourseUsers", e);
        } finally {
            commit(con);
        }
    }

    private List<CourseUser> getCourseUserFrom(ResultSet resultSet) throws SQLException {
        List<CourseUser> courseUsers = new ArrayList<>();
        while (resultSet.next()) {
            courseUsers.add(extractCourseUser(resultSet));
        }
        return courseUsers;
    }

    private CourseUser extractCourseUser(ResultSet rs) throws SQLException {
        CourseUser courseUser = new CourseUser();
        User user = userDao.getUserById(rs.getInt(Fields.COURSEUSER_ID_USER));
        Course course = courseDao.getCourseById(rs.getInt(Fields.COURSEUSER_ID_COURSE));

        courseUser.setCourse(course);
        courseUser.setUser(user);
        return courseUser;
    }

}
