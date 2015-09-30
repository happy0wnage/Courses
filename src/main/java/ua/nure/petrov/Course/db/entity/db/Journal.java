package ua.nure.petrov.Course.db.entity.db;

/**
 * Created by Владислав on 28.07.2015.
 */
public class Journal extends Entity {

    private Course course;

    public Journal(int id) {
        super(id);
    }

    public Journal() {
        super(1);
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "course=" + course +
                '}';
    }
}
