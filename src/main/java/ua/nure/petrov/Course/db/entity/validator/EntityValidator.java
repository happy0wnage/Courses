package ua.nure.petrov.Course.db.entity.validator;

import ua.nure.petrov.Course.db.entity.constants.Util;
import ua.nure.petrov.Course.db.entity.db.Course;
import ua.nure.petrov.Course.db.entity.db.Marks;
import ua.nure.petrov.Course.db.entity.db.News;
import ua.nure.petrov.Course.db.entity.db.User;

import java.sql.Date;


public class EntityValidator {

    public static boolean validateUser(User user) {

        if (user.getFirstName().isEmpty() || user.getSecondName().isEmpty() ||
                user.getLogin().isEmpty() || user.getEmail().isEmpty() || user.getPhone().isEmpty() || user.getPassword().isEmpty()) {
            return false;
        }

        if (!validatePhone(user.getPhone())) {
            return false;
        }

        if (!validateName(user.getFirstName())) {
            return false;
        }

        if (!validateName(user.getSecondName())) {
            return false;
        }

        if (user.getMiddleName() != null) {
            if (!validateName(user.getMiddleName())) {
                return false;
            }
        }

        if (!validateEmail(user.getEmail())) {
            return false;
        }

        if (!validateLogin(user.getLogin())) {
            return false;
        }

        if (user.getStudentCardNumber() != null) {
            if (!validateCardNumber(user.getStudentCardNumber())) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateProfile(User user) {

        if (user.getLogin().isEmpty() || user.getEmail().isEmpty() || user.getPhone().isEmpty()) {
            return false;
        }

        if (!validatePhone(user.getPhone())) {
            return false;
        }

        if (!validateEmail(user.getEmail())) {
            return false;
        }

        if (!validateLogin(user.getLogin())) {
            return false;
        }

        if (user.getStudentCardNumber() != null) {
            if (!validateCardNumber(user.getStudentCardNumber())) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateNews(News news) {
        return !(news.getTitle().isEmpty() || news.getBody().isEmpty());
    }

    public static boolean validateCourse(Course course) {

        if (course.getName().isEmpty() || course.getDescription().isEmpty() || course.getStartDate() == null || course.getEndDate() == null) {
            return false;
        }

        if (course.getStartDate().after(Util.getYesterdaysDate())) {
            if (!isDateLater(course.getEndDate(), course.getStartDate())) {
                return false;
            }
        } else {
            return false;
        }

        if(course.getDuration() > 120) {
            return false;
        }

        return true;
    }

    public static boolean validateMarks(Marks marks) {
        return !(marks.getUser() == null || marks.getJournal() == null || marks.getMark() == 0 || marks.getDay() == null);

    }

    private static boolean validateName(String name) {
        return name.matches("^[A-ZА-Я][a-zа-яA-ZА-Я-]{2,30}$");
    }

    private static boolean validateEmail(String email) {
        return email.matches("^\\w.+@\\w+[.][a-z]+$");
    }

    private static boolean validateLogin(String login) {
        return login.matches("^[a-zA-Z0-9_-]{3,16}$");
    }

    private static boolean validatePassord(String password) {
        return password.matches("^\\w{3,14}$");
    }

    private static boolean validatePhone(String phone) {
        return phone.matches("^(\\+38)?[0](50|63|66|67|68|91|92|93|94|95|96|97|98|99)\\d{7}$");
    }

    private static boolean validateCardNumber(String card) {
        return card.matches("^[А-ЯA-Z]{2}\\d{8}$");
    }

    private static boolean isDateLater(Date date, Date compDate) {
        return date.after(compDate);
    }

    private EntityValidator() {
    }

    public static boolean validateLoginPassword(String login, String password) {
        if (login.isEmpty()) {
            return false;
        }
        return !password.isEmpty();
    }

}