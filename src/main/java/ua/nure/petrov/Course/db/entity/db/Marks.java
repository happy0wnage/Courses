package ua.nure.petrov.Course.db.entity.db;

import java.sql.Date;

/**
 * Created by Владислав on 29.07.2015.
 */
public class Marks extends Entity {

    private User user;

    private Journal journal;

    private int mark;

    private Date day;

    public Marks(int id) {
        super(id);
    }

    public Marks() {
        super(1);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public boolean validateMark(int mark) {
        if (mark >= 1 && mark <= 100) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Marks{" +
                "user=" + user +
                ", journal=" + journal +
                ", mark=" + mark +
                ", day=" + day +
                '}';
    }
}
