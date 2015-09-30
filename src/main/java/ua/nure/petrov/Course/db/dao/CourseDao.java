package ua.nure.petrov.Course.db.dao;

import ua.nure.petrov.Course.db.entity.db.Course;

import java.util.List;

/**
 * Created by Владислав on 28.07.2015.
 */
public interface CourseDao {

    List<Course> getAllCourses();

    List<Course> getCoursesByIdUser(int idUser);

    List<Course> getCoursesByIdTheme(int idTheme);

    List<Course> getCoursesWithoutIdUser(int idUser);

    List<Course> getCoursesByIdUserLecturer(int idUserLecturer);

    int getUserCountByIdCourse(int idCourse);

    void insertCourse(Course course);

    void updateCourse(Course course);

    void deleteCourseById(int id);

    Course getCourseById(int id);


}
