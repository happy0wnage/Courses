package ua.nure.petrov.Course.db.entity.db;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Владислав on 28.07.2015.
 */
public class TestUser {

    User user;

    @Before
    public void before() {
        user = new User();
        user.setFirstName("fName");
        user.setSecondName("sName");
        user.setMiddleName("mName");
        user.setLogin("login");
        user.setPassword("password");
        user.setSalt("salt");
        user.setEmail("email");
        user.setPhone("phone");
        user.setPhoto("path");
        user.setStudentCardNumber("card");
        user.setStatusBlocked(true);

    }

    @Test
    public void setGetTest() {

        String expectedFirstName = "fName";
        String expectedSecondName = "sName";
        String expectedMiddleName = "mName";
        String expectedLogin = "login";
        String expectedPassword = "password";
        String expectedSalt = "salt";
        String expectedEmail = "email";
        String expectedPhone = "phone";
        String expectedPhoto = "path";
        String expectedStudentCardNumber = "card";
        boolean expectedStatusBlocked = false;

        assertEquals(expectedFirstName, user.getFirstName());
        assertEquals(expectedSecondName, user.getSecondName());
        assertEquals(expectedMiddleName, user.getMiddleName());
        assertEquals(expectedLogin, user.getLogin());
        assertEquals(expectedPassword, user.getPassword());
        assertEquals(expectedSalt, user.getSalt());
        assertEquals(expectedEmail, user.getEmail());
        assertEquals(expectedPhone, user.getPhone());
        assertEquals(expectedPhoto, user.getPhoto());
        assertEquals(expectedStudentCardNumber, user.getStudentCardNumber());
        assertEquals(expectedStatusBlocked, user.isStatusBlocked());
    }
}

