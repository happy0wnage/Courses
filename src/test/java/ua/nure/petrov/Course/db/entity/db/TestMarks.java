package ua.nure.petrov.Course.db.entity.db;

import org.junit.Before;
import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by Владислав on 29.07.2015.
 */
public class TestMarks {

    Marks marks;
    Journal journal;
    User user;

    @Before
    public void before() {
        journal = new Journal();
        marks = new Marks();
        user = new User();

        marks.setUser(user);
        marks.setJournal(journal);
        marks.setMark(100);
        marks.setDay(Date.valueOf("2015-08-07"));
    }

    @Test
    public void setGetTest() {

        Journal expectedJournal = new Journal();
        User expectedUser = new User();
        int expectedMark = 100;
        Date expectedDay = Date.valueOf("2015-08-07");

        assertEquals(expectedJournal, marks.getJournal());
        assertEquals(expectedUser, marks.getUser());
        assertEquals(expectedMark, marks.getMark());
        assertEquals(expectedDay, marks.getDay());
    }
}
