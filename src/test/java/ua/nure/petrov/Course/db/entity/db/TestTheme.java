package ua.nure.petrov.Course.db.entity.db;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Владислав on 28.07.2015.
 */
public class TestTheme {

    Theme theme;

    @Before
    public void before() {
        theme = new Theme();
        theme.setName("name");
        theme.setDescription("description");
    }

    @Test
    public void setGetTest() {
        String expectedName = "name";
        String expectedDescription = "description";

        assertEquals(expectedName, theme.getName());
        assertEquals(expectedDescription, theme.getDescription());
    }
}
