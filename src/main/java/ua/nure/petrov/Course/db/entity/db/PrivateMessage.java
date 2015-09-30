package ua.nure.petrov.Course.db.entity.db;

import java.sql.Timestamp;

/**
 * Created by Владислав on 28.07.2015.
 */
public class PrivateMessage extends Entity {

    private User user;

    private User toUser;

    private String body;

    private Timestamp postDate;

    public PrivateMessage(int id) {
        super(id);
    }
    public PrivateMessage() {
        super(1);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
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

    @Override
    public String toString() {
        return "PrivateMessage{" +
                "user=" + user +
                ", toUser=" + toUser +
                ", body='" + body + '\'' +
                ", postDate=" + postDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PrivateMessage that = (PrivateMessage) o;

        if (body != null ? !body.equals(that.body) : that.body != null) return false;
        if (postDate != null ? !postDate.equals(that.postDate) : that.postDate != null) return false;
        if (toUser != null ? !toUser.equals(that.toUser) : that.toUser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (toUser != null ? toUser.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (postDate != null ? postDate.hashCode() : 0);
        return result;
    }
}

