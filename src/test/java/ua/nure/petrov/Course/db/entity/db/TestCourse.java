package ua.nure.petrov.Course.db.entity.db;

import org.junit.Before;
import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by Владислав on 28.07.2015.
 */
public class TestCourse {

    Course course;

    @Before
    public void before() {
        course = new Course();
        course.setName("Name");
        course.setDescription("Description");
        course.setStartDate(Date.valueOf("2015-08-07"));
        course.setEndDate(Date.valueOf("2015-08-07"));
        course.setUsersCount(1);
    }

    @Test
    public void setGetTest() {
        String expectedName = "Name";
        String expectedDescription = "Description";
        Date expectedStartDate = Date.valueOf("2015-08-07");
        Date expectedEndDate = Date.valueOf("2015-08-07");
        int expectedUserCount = 1;

        assertEquals(expectedName, course.getName());
        assertEquals(expectedDescription, course.getDescription());
        assertEquals(expectedStartDate, course.getStartDate());
        assertEquals(expectedEndDate, course.getEndDate());
        assertEquals(expectedUserCount, course.getUsersCount());
    }

}
