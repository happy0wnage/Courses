package ua.nure.petrov.Course.db.entity.constants;

/**
 * Created by Владислав on 28.07.2015.
 */
public class Fields {

    //    Course
    public static final String COURSE_ID_COURSE = "idCourse";
    public static final String COURSE_ID_USER_LECTURER = "idUserLecturer";
    public static final String COURSE_ID_THEME = "idTheme";
    public static final String COURSE_NAME = "Name";
    public static final String COURSE_DESCRIPTION = "Description";
    public static final String COURSE_START_DATE = "Start_date";
    public static final String COURSE_END_DATE = "End_date";

    public static final String COURSE_COUNT = "Count";

    //    News
    public static final String NEWS_ID_NEWS = "idNews";
    public static final String NEWS_ID_COURSE = "idCourse";
    public static final String NEWS_ID_USER = "idUser";
    public static final String NEWS_TITLE = "Title";
    public static final String NEWS_BODY = "Body";
    public static final String NEWS_POST_DATE = "Post_date";
    public static final String NEWS_PICTURE = "Picture";

    //    Theme
    public static final String THEME_ID_THEME = "idTheme";
    public static final String THEME_NAME = "Name";
    public static final String THEME_DESCRIPTION = "Description";

    //    Journal
    public static final String JOURNAL_ID_JOURNAL = "idJournal";
    public static final String JOURNAL_ID_COURSE = "idCourse";

    //    Mark
    public static final String MARKS_ID_MARKS = "idMarks";
    public static final String MARKS_ID_USER = "idUser";
    public static final String MARKS_ID_JOURNAL = "idJournal";
    public static final String MARKS_MARK = "Mark";
    public static final String MARKS_DAY = "Day";

    //    CourseUser
    public static final String COURSEUSER_ID_COURSE = "idCourse";
    public static final String COURSEUSER_ID_USER = "idUser";

    //    User
    public static final String USER_ID_USER = "idUser";
    public static final String USER_FIRST_NAME = "First_name";
    public static final String USER_SECOND_NAME = "Second_name";
    public static final String USER_MIDDLE_NAME = "Middle_name";
    public static final String USER_PASSWORD = "Password";
    public static final String USER_LOGIN = "Login";
    public static final String USER_SALT = "Salt";
    public static final String USER_EMAIL = "Email";
    public static final String USER_PHONE = "Phone";
    public static final String USER_PHOTO = "Photo";
    public static final String USER_STUDENT_CARD_NUMBER = "Student_card_number";
    public static final String USER_STATUS_BLOCKED = "Status_blocked";

    //    RoleUser
    public static final String ROLEUSER_ID_ROLE = "idRole";
    public static final String ROLEUSER_ID_USER = "idUser";

    //    Role
    public static final String ROLE_ID_ROLE = "idRole";
    public static final String ROLE_NAME = "Name";

    //    PrivateMessage
    public static final String PM_ID_PRIVATE_MESSAGE = "idPrivateMessage";
    public static final String PM_ID_USER = "idUser";
    public static final String PM_ID_TO_USER = "idToUser";
    public static final String PM_BODY = "Body";
    public static final String PM_POST_DATE = "Post_date";

}

