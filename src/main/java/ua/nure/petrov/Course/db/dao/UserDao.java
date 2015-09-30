package ua.nure.petrov.Course.db.dao;

import ua.nure.petrov.Course.db.entity.db.Role;
import ua.nure.petrov.Course.db.entity.db.User;

import java.util.List;

/**
 * Created by Владислав on 29.07.2015.
 */
public interface UserDao {

    User getUserById(int id);

    User getUserByLogin(String login);

    User getUserByStudentCard(String studentCard);

    User getUserByLoginPassword(User user);

    List<User> getUserByIdCourse(int idCourse);

    void updateUser(User user);

    void insertRoleUser(User user, List<Role> roles);

    void deleteUserById(int id);

    void checkForMatches(User user);

}
