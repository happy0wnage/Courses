package ua.nure.petrov.Course.db.dao;

import ua.nure.petrov.Course.db.entity.db.RoleUser;

import java.util.List;

/**
 * Created by Владислав on 29.07.2015.
 */
public interface RoleUserDao {

    void insertRoleUser(RoleUser ru);

    void deleteRoleUserByIdUser(int idUser);

    List<RoleUser> getRoleUserByIdUser(int idUser);

    List<RoleUser> getRoleUserByIdRole(int idRole);

    List<RoleUser> getAllRoleUsers();

}
