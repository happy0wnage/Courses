package ua.nure.petrov.Course.db.dao;

import ua.nure.petrov.Course.db.entity.db.Role;

import java.util.List;

/**
 * Created by Владислав on 01.08.2015.
 */
public interface RoleDao {

    Role getRoleById(int id);

    Role getRoleByName(String name);

    List<Role> getAllRoles();
}
