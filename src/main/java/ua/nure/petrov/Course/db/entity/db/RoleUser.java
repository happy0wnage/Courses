package ua.nure.petrov.Course.db.entity.db;

/**
 * Created by Владислав on 28.07.2015.
 */
public class RoleUser {

    private Role role;

    private User user;

    public RoleUser(Role role, User user) {
        this.role = role;
        this.user = user;
    }

    public RoleUser() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "RoleUser{" +
                "role=" + role +
                ", user=" + user +
                '}';
    }
}
