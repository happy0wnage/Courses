package ua.nure.petrov.Course.db.dao.impl;

import ua.nure.petrov.Course.db.MySqlConnection;
import ua.nure.petrov.Course.db.dao.CourseDao;
import ua.nure.petrov.Course.db.dao.DAO;
import ua.nure.petrov.Course.db.dao.ThemeDao;
import ua.nure.petrov.Course.db.dao.UserDao;
import ua.nure.petrov.Course.db.entity.constants.Fields;
import ua.nure.petrov.Course.db.entity.constants.Query;
import ua.nure.petrov.Course.db.entity.db.Course;
import ua.nure.petrov.Course.db.entity.db.Theme;
import ua.nure.petrov.Course.db.entity.db.User;
import ua.nure.petrov.Course.db.entity.exception.DBLayerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 28.07.2015.
 */
public class CourseDaoImpl extends DAO implements CourseDao {

    private UserDao userDao = new UserDaoImpl();
    private ThemeDao themeDao = new ThemeDaoImpl();


    @Override
    public List<Course> getAllCourses() {

        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_ALL_COURSES);
            rs = pst.executeQuery();
            return getCoursesFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get courses", e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<Course> getCoursesByIdUser(int idUser) {

        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_COURSES_BY_ID_USER);
            pst.setInt(FIRST, idUser);
            rs = pst.executeQuery();
            return getCoursesFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get courses by idUser = " + idUser, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<Course> getCoursesByIdTheme(int idTheme) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_COURSES_BY_ID_THEME);
            pst.setInt(FIRST, idTheme);
            rs = pst.executeQuery();
            return getCoursesFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get courses by idTheme = " + idTheme, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<Course> getCoursesWithoutIdUser(int idUser) {

        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_COURSES_WITHOUT_ID_USER);
            pst.setInt(FIRST, idUser);
            rs = pst.executeQuery();
            return getCoursesFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get courses without idUser = " + idUser, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<Course> getCoursesByIdUserLecturer(int idUserLecturer) {

        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_COURSES_BY_ID_USER_LECTURER);
            pst.setInt(FIRST, idUserLecturer);
            rs = pst.executeQuery();

            return getCoursesFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get courses by idUserLecturer = " + idUserLecturer, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public int getUserCountByIdCourse(int idCourse) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_USER_COUNT_BY_ID_COURSE);
            pst.setInt(FIRST, idCourse);
            rs = pst.executeQuery();
            rs.relative(FIRST);
            return rs.getInt(Fields.COURSE_COUNT);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get count of users by idCourse = " + idCourse, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public void insertCourse(Course course) {
        try {
            con = MySqlConnection.getWebConnection();
            int k = 1;
            String generatedColumns[] = {Fields.COURSE_ID_COURSE};
            pst = con.prepareStatement(Query.INSERT_COURSES, generatedColumns);
            pst.setInt(k++, course.getUserLecturer().getId());
            pst.setInt(k++, course.getTheme().getId());
            pst.setString(k++, course.getName());
            pst.setString(k++, course.getDescription());
            pst.setDate(k++, course.getStartDate());
            pst.setDate(k, course.getEndDate());
            pst.executeUpdate();

            rs = pst.getGeneratedKeys();
            rs.next();
            int idCourse = rs.getInt(FIRST);

            k = 1;
            pst = con.prepareStatement(Query.INSERT_JOURNAL);
            pst.setInt(k, idCourse);
            pst.executeUpdate();

        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Course " + course + "wasn't inserted", e);
        } finally {
            commit(con);
        }
    }

    @Override
    public void updateCourse(Course course) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.UPDATE_COURSE);
            int k = 1;
            pst.setInt(k++, course.getUserLecturer().getId());
            pst.setInt(k++, course.getTheme().getId());
            pst.setString(k++, course.getName());
            pst.setString(k++, course.getDescription());
            pst.setDate(k++, course.getStartDate());
            pst.setDate(k++, course.getEndDate());
            pst.setInt(k++, course.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Course " + course + "wasn't updated", e);
        } finally {
            commit(con);
        }
    }

    @Override
    public void deleteCourseById(int id) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.DELETE_COURSE_BY_ID);
            pst.setInt(FIRST, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Filed to delete Course by id = " + id, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public Course getCourseById(int id) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_COURSE_BY_ID);
            pst.setInt(FIRST, id);
            rs = pst.executeQuery();
            rs.relative(FIRST);
            return extractCourse(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Filed to get Course by id = " + id, e);
        } finally {
            commit(con);
        }
    }

    private List<Course> getCoursesFrom(ResultSet resultSet) throws SQLException {
        List<Course> courses = new ArrayList<>();
        while (resultSet.next()) {
            courses.add(extractCourse(resultSet));
        }
        return courses;
    }


    private Course extractCourse(ResultSet rs) throws SQLException {
        User user = userDao.getUserById(rs.getInt(Fields.COURSE_ID_USER_LECTURER));
        Theme theme = themeDao.getThemeById(rs.getInt(Fields.COURSE_ID_THEME));

        Course course = new Course(rs.getInt(Fields.COURSE_ID_COURSE));
        course.setUserLecturer(user);
        course.setTheme(theme);
        course.setName(rs.getString(Fields.COURSE_NAME));
        course.setDescription(rs.getString(Fields.COURSE_DESCRIPTION));
        course.setStartDate(rs.getDate(Fields.COURSE_START_DATE));
        course.setEndDate(rs.getDate(Fields.COURSE_END_DATE));
        return course;
    }

}
