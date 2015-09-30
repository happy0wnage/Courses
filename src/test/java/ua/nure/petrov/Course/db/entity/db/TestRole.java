package ua.nure.petrov.Course.db.entity.db;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Владислав on 28.07.2015.
 */
public class TestRole {

    Role role;

    @Before
    public void before() {
        role = new Role();
        role.setName("name");
    }

    @Test
    public void setGetTest() {
        String expectedName = "name";
        assertEquals(expectedName, role.getName());
    }
}
