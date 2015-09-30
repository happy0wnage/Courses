package ua.nure.petrov.Course.db.dao;

import ua.nure.petrov.Course.db.entity.db.PrivateMessage;

import java.util.List;

/**
 * Created by Владислав on 29.07.2015.
 */
public interface PrivateMessageDao {

    void insertPrivateMessage(PrivateMessage pm);

    void deletePrivateMessageById(int id);

    List<PrivateMessage> getPrivateMessagesByIdUser(int idUser);

    List<PrivateMessage> getConversationByIdUsers(int idUser, int idToUser);

    List<PrivateMessage> getPrivateMessagesByIdUsers(int idUser, int idToUser);

    List<PrivateMessage> getPrivateMessagesByIdUsersOR(int idUser, int idToUser);
}
