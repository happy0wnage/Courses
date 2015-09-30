package ua.nure.petrov.Course.db.entity.db;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Владислав on 28.07.2015.
 */
public class TestRoleUser {

    RoleUser roleUser;
    Role role;
    User user;

    @Before
    public void before() {
        roleUser = new RoleUser();
        role = new Role();
        user = new User();

        roleUser.setRole(role);
        roleUser.setUser(user);
    }

    @Test
    public void setGetTest() {
        Role expectedRole = new Role();
        User expectedUser = new User();

        assertEquals(expectedRole, roleUser.getRole());
        assertEquals(expectedUser, roleUser.getUser());
    }

}
