package ua.nure.petrov.Course.db.dao.impl;

import ua.nure.petrov.Course.db.MySqlConnection;
import ua.nure.petrov.Course.db.dao.CourseDao;
import ua.nure.petrov.Course.db.dao.DAO;
import ua.nure.petrov.Course.db.dao.JournalDao;
import ua.nure.petrov.Course.db.entity.constants.Fields;
import ua.nure.petrov.Course.db.entity.constants.Query;
import ua.nure.petrov.Course.db.entity.db.Course;
import ua.nure.petrov.Course.db.entity.db.Journal;
import ua.nure.petrov.Course.db.entity.exception.DBLayerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 28.07.2015.
 */
public class JournalDaoImpl extends DAO implements JournalDao {
    private CourseDao courseDao = new CourseDaoImpl();

    private List<Journal> journals;
    @Override
    public void insertJournal(Journal journal) {
        try {
            con = MySqlConnection.getWebConnection();
            int k = 1;
            pst = con.prepareStatement(Query.INSERT_JOURNAL);
            pst.setInt(k, journal.getCourse().getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to insert Journal " + journal, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public void deleteJournalById(int id) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.DELETE_JOURNAL);
            pst.setInt(FIRST, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to delete Journal with id = " + id, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public Journal getJournalById(int id) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_JOURNAL_BY_ID);
            pst.setInt(FIRST, id);
            rs = pst.executeQuery();
            rs.relative(FIRST);
            return extractJournal(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get Journal with id = " + id, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<Journal> getAllJournals() {
        journals = new ArrayList<>();
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_ALL_JOURNALS);
            rs = pst.executeQuery();
            return getJournalsFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get Journals", e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<Journal> getJournalsByIdLecturer(int idUserLecturer) {
        journals = new ArrayList<>();
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_JOURNALS_BY_ID_USER_LECTURER);
            pst.setInt(FIRST, idUserLecturer);
            rs = pst.executeQuery();
            return getJournalsFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get Journals with idUserLecturer = " + idUserLecturer, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<Journal> getJournalByIdCourse(int idCourse) {

        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_JOURNAL_BY_ID_COURSE);
            pst.setInt(FIRST, idCourse);
            rs = pst.executeQuery();
            return getJournalsFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get Journal with idCourse = " + idCourse, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<Journal> getJournalsByIdUser(int idUser) {

        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_JOURNALS_BY_ID_USER);
            pst.setInt(FIRST, idUser);
            rs = pst.executeQuery();
            return getJournalsFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get Journals with idUser = " + idUser, e);
        } finally {
            commit(con);
        }
    }

    private List<Journal> getJournalsFrom(ResultSet resultSet) throws SQLException {
        List<Journal> journals = new ArrayList<>();
        while (resultSet.next()) {
            journals.add(extractJournal(resultSet));
        }
        return journals;
    }

    private Journal extractJournal(ResultSet rs) throws SQLException {
        Course course = courseDao.getCourseById(rs.getInt(Fields.JOURNAL_ID_COURSE));
        Journal journal = new Journal(rs.getInt(Fields.JOURNAL_ID_JOURNAL));
        journal.setCourse(course);
        return journal;
    }
}
