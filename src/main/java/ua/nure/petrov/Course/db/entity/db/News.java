package ua.nure.petrov.Course.db.entity.db;

import java.sql.Timestamp;

/**
 * Created by Владислав on 28.07.2015.
 */
public class News extends Entity {

    private Course course;

    private User user;

    private String title;

    private String body;

    private Timestamp postDate;

    private String picture;

    public News(int id) {
        super(id);
    }

    public News() {
        super(1);
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getPostDate() {
        return postDate;
    }

    public void setPostDate(Timestamp postDate) {
        this.postDate = postDate;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "News{" +
                "course=" + course +
                ", user=" + user +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", postDate=" + postDate +
                ", picture=" + picture +
                '}';
    }
}
