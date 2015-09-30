package ua.nure.petrov.Course.db.dao.impl;

import ua.nure.petrov.Course.db.MySqlConnection;
import ua.nure.petrov.Course.db.dao.CourseDao;
import ua.nure.petrov.Course.db.dao.DAO;
import ua.nure.petrov.Course.db.dao.NewsDao;
import ua.nure.petrov.Course.db.dao.UserDao;
import ua.nure.petrov.Course.db.entity.constants.Fields;
import ua.nure.petrov.Course.db.entity.constants.Query;
import ua.nure.petrov.Course.db.entity.db.Course;
import ua.nure.petrov.Course.db.entity.db.News;
import ua.nure.petrov.Course.db.entity.db.User;
import ua.nure.petrov.Course.db.entity.exception.DBLayerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 28.07.2015.
 */
public class NewsDaoImpl extends DAO implements NewsDao {

    private CourseDao courseDao = new CourseDaoImpl();
    private UserDao userDao = new UserDaoImpl();

    @Override
    public List<News> getNewsByIdAuthor(int idUser) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_NEWS_BY_ID_AUTHOR);
            pst.setInt(FIRST, idUser);
            rs = pst.executeQuery();
            return getNewsFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get News with idUser = " + idUser, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public News getNewsById(int id) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_NEWS_BY_ID);
            pst.setInt(FIRST, id);
            rs = pst.executeQuery();
            rs.relative(FIRST);
            return extractNews(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get News with id = " + id, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<News> getNewsByIdCourse(int idCourse) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_NEWS_BY_ID_COURSE);
            pst.setInt(FIRST, idCourse);
            rs = pst.executeQuery();
            return getNewsFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get News with idCourse = " + idCourse, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<News> getNewsByIdUser(int idUser) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_NEWS_BY_ID_USER);
            pst.setInt(FIRST, idUser);
            rs = pst.executeQuery();
            return getNewsFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get News with idUser = " + idUser, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<News> getAllNews() {

        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_ALL_NEWS);
            rs = pst.executeQuery();
            return getNewsFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get News", e);
        } finally {
            commit(con);
        }
    }

    @Override
    public void deleteNewsById(int id) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.DELETE_NEWS_BY_ID);
            pst.setInt(FIRST, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get News with id = " + id, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public void updateNews(News news) {
        try {
            con = MySqlConnection.getWebConnection();
            int k = 1;
            pst = con.prepareStatement(Query.UPDATE_NEWS);
            pst.setInt(k++, news.getCourse().getId());
            pst.setInt(k++, news.getUser().getId());
            pst.setString(k++, news.getTitle());
            pst.setString(k++, news.getBody());
            pst.setTimestamp(k++, news.getPostDate());
            pst.setString(k++, news.getPicture());
            pst.setInt(k++, news.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to update News " + news, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public void insertNews(News news) {
        try {
            con = MySqlConnection.getWebConnection();
            int k = 1;
            pst = con.prepareStatement(Query.INSERT_NEWS);
            pst.setInt(k++, news.getCourse().getId());
            pst.setInt(k++, news.getUser().getId());
            pst.setString(k++, news.getTitle());
            pst.setString(k++, news.getBody());
            pst.setTimestamp(k++, news.getPostDate());
            pst.setString(k, news.getPicture());
            pst.executeUpdate();
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to insert a piece of News " + news, e);
        } finally {
            commit(con);
        }
    }

    private List<News> getNewsFrom(ResultSet resultSet) throws SQLException {
        List<News> news = new ArrayList<>();
        while (resultSet.next()) {
            news.add(extractNews(resultSet));
        }
        return news;
    }

    private News extractNews(ResultSet rs) throws SQLException {
        Course course = courseDao.getCourseById(rs.getInt(Fields.NEWS_ID_COURSE));
        User user = userDao.getUserById(rs.getInt(Fields.NEWS_ID_USER));

        News pNews = new News(rs.getInt(Fields.NEWS_ID_NEWS));
        pNews.setCourse(course);
        pNews.setUser(user);
        pNews.setTitle(rs.getString(Fields.NEWS_TITLE));
        pNews.setBody(rs.getString(Fields.NEWS_BODY));
        pNews.setPostDate(rs.getTimestamp(Fields.NEWS_POST_DATE));
        pNews.setPicture(rs.getString(Fields.NEWS_PICTURE));
        return pNews;
    }
}
