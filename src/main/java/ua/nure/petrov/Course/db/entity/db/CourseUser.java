package ua.nure.petrov.Course.db.entity.db;

/**
 * Created by Владислав on 28.07.2015.
 */
public class CourseUser {

    private Course course;

    private User user;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CourseUser{" +
                "course=" + course +
                ", user=" + user +
                '}';
    }
}
