package ua.nure.petrov.Course.db.entity.db;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Владислав on 28.07.2015.
 */
public class Course extends Entity{

    private User userLecturer;

    private Theme theme;

    private String name;

    private String description;

    private Date startDate;

    private Date endDate;

    private int duration;

    private double left;

    private int usersCount;

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public double getLeft() {
        long duration  = new java.util.Date().getTime() - startDate.getTime();
        long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
        return diffInDays*100/getDuration();
    }

    public int getDuration() {
        long duration  = endDate.getTime() - startDate.getTime();
        long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
        return (int) diffInDays;
    }

    public User getUserLecturer() {
        return userLecturer;
    }

    public void setUserLecturer(User userLecturer) {
        this.userLecturer = userLecturer;
    }

    public Course(int id) {
        super(id);
    }

    public Course() {
        super(1);
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endtDate) {
        this.endDate = endtDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Course{" +
                "userLecturer=" + userLecturer +
                ", theme=" + theme +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
