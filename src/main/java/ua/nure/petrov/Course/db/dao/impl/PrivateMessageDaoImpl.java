package ua.nure.petrov.Course.db.dao.impl;

import ua.nure.petrov.Course.db.MySqlConnection;
import ua.nure.petrov.Course.db.dao.DAO;
import ua.nure.petrov.Course.db.dao.PrivateMessageDao;
import ua.nure.petrov.Course.db.dao.UserDao;
import ua.nure.petrov.Course.db.entity.constants.Fields;
import ua.nure.petrov.Course.db.entity.constants.Query;
import ua.nure.petrov.Course.db.entity.db.PrivateMessage;
import ua.nure.petrov.Course.db.entity.db.User;
import ua.nure.petrov.Course.db.entity.exception.DBLayerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 29.07.2015.
 */
public class PrivateMessageDaoImpl extends DAO implements PrivateMessageDao {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public void insertPrivateMessage(PrivateMessage pm) {
        try {
            con = MySqlConnection.getWebConnection();
            int k = 1;
            pst = con.prepareStatement(Query.INSERT_PRIVATE_MESSAGE);
            pst.setInt(k++, pm.getUser().getId());
            pst.setInt(k++, pm.getToUser().getId());
            pst.setString(k++, pm.getBody());
            pst.setTimestamp(k, pm.getPostDate());
            pst.executeUpdate();
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to insert Private Message " + pm, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public void deletePrivateMessageById(int id) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.DELETE_PRIVATE_MESSAGE_BY_ID);
            pst.setInt(FIRST, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to delete Private Message with id = " + id, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<PrivateMessage> getPrivateMessagesByIdUser(int idUser) {

        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_PRIVATE_MESSAGE_BY_ID_USER);
            pst.setInt(FIRST, idUser);
            rs = pst.executeQuery();
            return getMsgsFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get Private Message with idUser = " + idUser, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<PrivateMessage> getConversationByIdUsers(int idUser, int idToUser) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_CONVERSATION_BY_ID_USER);
            int k = 1;
            pst.setInt(k++, idUser);
            pst.setInt(k++, idToUser);
            pst.setInt(k++, idUser);
            pst.setInt(k, idToUser);
            rs = pst.executeQuery();
            return getMsgsFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get Conversation with idUser and idToUser = " + idUser + ", " + idToUser, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<PrivateMessage> getPrivateMessagesByIdUsers(int idUser, int idToUser) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_PRIVATE_MESSAGE_BY_ID_USERS);
            int k = FIRST;
            pst.setInt(k++, idUser);
            pst.setInt(k, idToUser);
            rs = pst.executeQuery();
            return getMsgsFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get Private Message with idUser and idToUser = " + idUser + ", " + idToUser, e);
        } finally {
            commit(con);
        }
    }

    @Override
    public List<PrivateMessage> getPrivateMessagesByIdUsersOR(int idUser, int idToUser) {
        try {
            con = MySqlConnection.getWebConnection();
            pst = con.prepareStatement(Query.GET_PRIVATE_MESSAGE_BY_ID_USERS_OR);
            int k = FIRST;
            pst.setInt(k++, idUser);
            pst.setInt(k, idToUser);
            rs = pst.executeQuery();
            return getMsgsFrom(rs);
        } catch (SQLException e) {
            rollBack(con);
            throw new DBLayerException("Failed to get Private Message with idUser and idToUser = " + idUser + ", " + idToUser, e);
        } finally {
            commit(con);
        }
    }

    private List<PrivateMessage> getMsgsFrom(ResultSet resultSet) throws SQLException {
        List<PrivateMessage> pms = new ArrayList<>();
        while (resultSet.next()) {
            pms.add(extractPrivateMessage(resultSet));
        }
        return pms;
    }

    private PrivateMessage extractPrivateMessage(ResultSet rs) throws SQLException {
        User user = userDao.getUserById(rs.getInt(Fields.PM_ID_USER));
        User toUser = userDao.getUserById(rs.getInt(Fields.PM_ID_TO_USER));
        PrivateMessage pm = new PrivateMessage(rs.getInt(Fields.PM_ID_PRIVATE_MESSAGE));

        pm.setUser(user);
        pm.setToUser(toUser);
        pm.setBody(rs.getString(Fields.PM_BODY));
        pm.setPostDate(rs.getTimestamp(Fields.PM_POST_DATE));
        return pm;
    }
}
