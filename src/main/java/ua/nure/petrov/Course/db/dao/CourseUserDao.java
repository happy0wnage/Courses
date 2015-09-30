package ua.nure.petrov.Course.db.dao;

import ua.nure.petrov.Course.db.entity.db.CourseUser;

import java.util.List;

/**
 * Created by Владислав on 29.07.2015.
 */
public interface CourseUserDao {

    void insertCourseUser(CourseUser cu);

    void deleteCourseUserByIdUser(int idUser);

    void deleteCourseUserByIdCourse(int idCourse);

    void deleteCourseUserByIdCourseAndIdUser(int idUser, int idCourse);

    List<CourseUser> getCourseUserByIdUser(int idUser);

    List<CourseUser> getAllCourseUsers();

    List<CourseUser> getCourseUserByIdCourse(int idCourse);

}
