package ua.nure.petrov.Course.db.entity.validator;

import org.junit.Before;
import org.junit.Test;
import ua.nure.petrov.Course.db.entity.constants.Util;
import ua.nure.petrov.Course.db.entity.db.*;

import java.sql.Date;

import static org.junit.Assert.assertTrue;


public class TestEntityValidator {

    User user;
    News news;
    Course course;
    Marks marks;

    @Before
    public void before() {
        user = new User();
        news = new News();
        course = new Course();
        marks = new Marks();
    }

    @Test
    public void testValidateUser() {
        user.setFirstName("Name");
        user.setSecondName("Sname");
        user.setMiddleName("Mname");
        user.setLogin("login");
        user.setEmail("email@email.com");
        user.setPhone("+380931111111");
        user.setStudentCardNumber("XA87987987");
        user.setPassword("A80972541524a");

        assertTrue(EntityValidator.validateUser(user));
    }

    @Test
    public void testValidateProfile() {
        user.setLogin("login");
        user.setEmail("email@email.com");
        user.setPhone("+380931111111");
        user.setStudentCardNumber("XA87987987");

        assertTrue(EntityValidator.validateProfile(user));
    }

    @Test
    public void testValidateNews() {
        news.setTitle("title");
        news.setBody("body");

        assertTrue(EntityValidator.validateNews(news));
    }

    @Test(expected = NullPointerException.class)
    public void testValidateNewsIfTitleIsNull() {
        news.setTitle(null);
        news.setBody("body");

        EntityValidator.validateNews(news);
    }

    @Test
    public void testValidateNewsIfTitleIsEmpty() {
        news.setTitle("");
        news.setBody("body");

        assertTrue(!EntityValidator.validateNews(news));
    }


    @Test
    public void testValidateCourse() {
        course.setName("name");
        course.setDescription("description");
        course.setStartDate(Date.valueOf("2015-08-20"));
        course.setEndDate(Date.valueOf("2015-10-20"));

        assertTrue(EntityValidator.validateCourse(course));
    }

    @Test
    public void testValidateMarks() {
        User expectedUser = new User();
        Journal expectedJournal = new Journal();
        int expectedMark = 100;
        Date expectedDay = Util.getDate();

        marks.setUser(expectedUser);
        marks.setJournal(expectedJournal);
        marks.setMark(expectedMark);
        marks.setDay(expectedDay);

        assertTrue(EntityValidator.validateMarks(marks));
    }

    @Test
    public void testValidateLoginPassword() {
        String expectedLogin = "login";
        String expectedPassword = "password";

        assertTrue(EntityValidator.validateLoginPassword(expectedLogin, expectedPassword));
    }

}