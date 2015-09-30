package ua.nure.petrov.Course.db.entity.db;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Владислав on 28.07.2015.
 */
public class TestJournal {

    Journal journal;
    Course course;

    @Before
    public void before() {
        journal = new Journal();
        course = new Course();
        journal.setCourse(course);
    }

    @Test
    public void setGetTest() {
        Course expectedCourse = new Course();
        assertEquals(expectedCourse, journal.getCourse());
    }
}
