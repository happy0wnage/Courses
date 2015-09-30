package ua.nure.petrov.Course.db.entity.db;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

/**
 * Created by Владислав on 28.07.2015.
 */
public class TestPrivateMessage {

    PrivateMessage privateMessage;
    User user;
    User toUser;

    @Before
    public void before() {
        user = new User();
        toUser = new User();
        privateMessage = new PrivateMessage();
        privateMessage.setBody("body");
        privateMessage.setPostDate(Timestamp.valueOf("2015-08-07"));
    }

    @Test
    public void setGetTest() {
        User expectedUser = new User();
        User expectedToUser = new User();
        String expectedBody = "body";
        Timestamp expectedPostDate = Timestamp.valueOf("2015-08-07");

        assertEquals(expectedUser, privateMessage.getUser());
        assertEquals(expectedToUser, privateMessage.getToUser());
        assertEquals(expectedBody, privateMessage.getBody());
        assertEquals(expectedPostDate, privateMessage.getPostDate());

    }
}

