package ua.nure.petrov.Course.db.dao.impl;

import ua.nure.petrov.Course.db.MySqlConnection;
import ua.nure.petrov.Course.db.dao.DAO;
import ua.nure.petrov.Course.db.dao.JournalDao;
import ua.nure.petrov.Course.db.dao.MarksDao;
import ua.nure.petrov.Course.db.dao.UserDao;
import ua.nure.petrov.Course.db.entity.constants.Fields;
import ua.nure.petrov.Course.db.entity.constants.Query;
import ua.nure.petrov.Course.db.entity.db.Journal;
import ua.nure.petrov.Course.db.entity.db.Marks;
import ua.nure.petrov.Course.db.entity.db.User;
import ua.nure.petrov.Course.db.entity.exception.DBLayerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 29.07.2015.
 */
public class MarksDaoImpl extends DAO implements MarksDao {
    private UserDao userDao = new UserDaoImpl();
    private JournalDao journalDao = new JournalDaoImpl();

    @Override
    public List<Marks> getMarksByIdUser(int idUser) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_MARKS_BY_ID_USER);
            pst.setInt(FIRST, idUser);
            rs = pst.executeQuery();
            return getMarksFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get Marks with idUser = " + idUser, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<Marks> getMarksByIdUserAndJournal(int idUser, int idJournal) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_MARKS_BY_ID_USE_AND_JOURNAL);
            int k = 1;
            pst.setInt(k++, idUser);
            pst.setInt(k, idJournal);
            rs = pst.executeQuery();
            return getMarksFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get Marks with idUser and idJournal = " + idUser + ", " + idJournal, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<Marks> getMarksByIdJournal(int idJournal) {

        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_MARKS_BY_ID_JOURNAL);
            pst.setInt(FIRST, idJournal);
            rs = pst.executeQuery();
            return getMarksFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get Marks with idJournal = " + idJournal, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<Marks> getAllMarksByIdLecturer(int idUserLecturer) {

        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_ALL_MARKS_BY_ID_USER_LECTURER);
            pst.setInt(FIRST, idUserLecturer);
            rs = pst.executeQuery();
            return getMarksFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get Marks with idUserLecturer = " + idUserLecturer, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public Marks getMarkById(int idMark) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_MARK_BY_ID);
            pst.setInt(FIRST, idMark);
            rs = pst.executeQuery();
            rs.relative(FIRST);
            return extractMarks(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get Mark with id = " + idMark, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<Marks> getAllMarks() {

        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_ALL_MARKS);
            rs = pst.executeQuery();
            return getMarksFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get Marks", e);
        } finally {
            commit(con);
        }
    }

    @Override
    public void insertMark(Marks mark) {
        try {
            con = MySqlConnection.getWebConnection();
            int k = 1;
            pst = con.prepareStatement(Query.INSERT_MARK);
            pst.setInt(k++, mark.getUser().getId());
            pst.setInt(k++, mark.getJournal().getId());
            pst.setInt(k++, mark.getMark());
            pst.setDate(k, mark.getDay());
            pst.executeUpdate();
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to insert Mark " + mark, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public void updateMark(Marks mark) {
        try {
            con = MySqlConnection.getWebConnection();
            int k = 1;
            pst = con.prepareStatement(Query.UPDATE_MARK);
            pst.setInt(k++, mark.getUser().getId());
            pst.setInt(k++, mark.getJournal().getId());
            pst.setInt(k++, mark.getMark());
            pst.setDate(k++, mark.getDay());
            pst.setInt(k++, mark.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to update Mark " + mark, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public void deleteMarkById(int id) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.DELETE_MARK);
            pst.setInt(FIRST, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to delete Mark with id = " + id, e);
        } finally {
            commit(con);
        }
    }

    private List<Marks> getMarksFrom(ResultSet resultSet) throws SQLException {
        List<Marks> marks = new ArrayList<>();
        while (resultSet.next()) {
            marks.add(extractMarks(resultSet));
        }
        return marks;
    }

    private Marks extractMarks(ResultSet rs) throws SQLException {
        User user = userDao.getUserById(rs.getInt(Fields.MARKS_ID_USER));
        Journal journal = journalDao.getJournalById(rs.getInt(Fields.MARKS_ID_JOURNAL));

        Marks mark = new Marks(rs.getInt(Fields.MARKS_ID_MARKS));
        mark.setUser(user);
        mark.setJournal(journal);
        mark.setMark(rs.getInt(Fields.MARKS_MARK));
        mark.setDay(rs.getDate(Fields.MARKS_DAY));
        return mark;
    }
}
