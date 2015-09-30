package ua.nure.petrov.Course.db.entity.db;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Created by Владислав on 28.07.2015.
 */
public class TestCourseUser {

    CourseUser courseUser;
    User user;
    Course course;

    @Before
    public void before() {
        courseUser = new CourseUser();

        user = new User();
        course = new Course();

        courseUser.setCourse(course);
        courseUser.setUser(user);
    }

    @Test
    public void setGetTest() {
        User expectedUser = new User();
        Course expectedCourse = new Course();

        assertEquals(expectedUser, user);
        assertEquals(expectedCourse, course);
    }
}
