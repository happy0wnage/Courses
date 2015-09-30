package ua.nure.petrov.Course.db.entity.db;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

/**
 * Created by Владислав on 28.07.2015.
 */
public class TestNews {

    News news;
    Course course;
    User user;

    @Before
    public void before() {
        user = new User();
        news = new News();
        course = new Course();

        news.setCourse(course);
        news.setUser(user);
        news.setTitle("title");
        news.setBody("body");
        news.setPostDate(Timestamp.valueOf("2015-08-07 12:00:00"));
        news.setPicture("path");
    }

    @Test
    public void setGetTest() {

        Course expectedCourse = new Course();
        User expectedUser = new User();
        String expectedTitle = "title";
        String expectedBody = "body";
        Timestamp expectedPostDate = Timestamp.valueOf("2015-08-07 12:00:00");
        String expectedPicture = "path";

        assertEquals(expectedCourse, news.getCourse());
        assertEquals(expectedUser, news.getUser());
        assertEquals(expectedTitle, news.getTitle());
        assertEquals(expectedBody, news.getBody());
        assertEquals(expectedPostDate, news.getPostDate());
        assertEquals(expectedPicture, news.getPicture());
    }
}
