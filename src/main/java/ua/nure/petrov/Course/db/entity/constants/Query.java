package ua.nure.petrov.Course.db.entity.constants;

/**
 * Created by Владислав on 28.07.2015.
 */
public class Query {

    public static final String GET_ALL_COURSES = "SELECT * FROM Course;";
    public static final String GET_COURSE_BY_ID = "SELECT * FROM Course WHERE idCourse = ?;";
    public static final String GET_COURSES_BY_ID_USER_LECTURER = "SELECT * FROM Course WHERE idUserLecturer = ?;";
    public static final String GET_COURSES_BY_ID_USER = "SELECT * FROM course WHERE idCourse IN (SELECT idCourse FROM courseuser WHERE idUser = ?);";
    public static final String GET_COURSES_BY_ID_THEME = "SELECT * FROM course WHERE idTheme = ?;";
    public static final String GET_COURSES_WITHOUT_ID_USER = "SELECT * FROM course WHERE idCourse NOT IN (SELECT idCourse FROM courseuser WHERE idUser = ?);";
    public static final String INSERT_COURSES = "INSERT INTO Course (idUserLecturer, idTheme, Name, Description, Start_date, End_date) VALUES (?,?,?,?,?,?);";
    public static final String UPDATE_COURSE = "UPDATE Course SET idUserLecturer = ?, idTheme = ?, Name = ?, Description = ?, Start_date = ?, End_date = ? WHERE idCourse = ?;";
    public static final String DELETE_COURSE_BY_ID = "DELETE FROM Course WHERE idCourse = ?;";

    public static final String GET_USER_COUNT_BY_ID_COURSE = "SELECT COUNT(idUser) Count FROM courseuser WHERE idCourse = ?;";

    public static final String GET_NEWS_BY_ID_COURSE = "SELECT * FROM News WHERE idCourse = ?;";
    public static final String GET_ALL_NEWS = "SELECT * FROM News;";
    public static final String GET_NEWS_BY_ID_AUTHOR = "SELECT * FROM News WHERE idUser = ?;";
    public static final String GET_NEWS_BY_ID = "SELECT * FROM News WHERE idNews = ?;";
    public static final String GET_NEWS_BY_ID_USER = "SELECT * FROM News WHERE idCourse IN (SELECT idCourse FROM courseuser WHERE idUser = ?);";
    public static final String DELETE_NEWS_BY_ID = "DELETE FROM NEWS WHERE idNews = ?;";
    public static final String INSERT_NEWS = "INSERT INTO News (idCourse, idUser, Title, Body, Post_date, Picture) VALUES (?,?,?,?,?,?);";
    public static final String UPDATE_NEWS = "UPDATE NEWS SET idCourse = ?, idUser = ?, Title = ?, Body = ?, Post_date = ?, Picture = ? WHERE idNews = ?";

    public static final String GET_THEME_BY_ID_COURSE = "SELECT * FROM THEME WHERE idCourse = ?;";
    public static final String GET_ALL_THEMES = "SELECT * FROM THEME;";
    public static final String GET_THEME_BY_ID = "SELECT * FROM THEME WHERE idTheme = ?;";
    public static final String DELETE_THEME_BY_ID = "DELETE From theme WHERE idTheme = ?;";
    public static final String INSERT_THEME = "INSERT INTO Theme (Name, Description) VALUES (?,?);";

    public static final String INSERT_JOURNAL = "INSERT INTO Journal (idCourse) VALUES (?);";
    public static final String DELETE_JOURNAL = "DELETE FROM Journal WHERE idJournal = ?;";
    public static final String GET_JOURNAL_BY_ID_COURSE = "SELECT * FROM Journal WHERE idCourse = ?;";
    public static final String GET_JOURNAL_BY_ID = "SELECT * FROM Journal WHERE idJournal = ?;";
    public static final String GET_JOURNALS_BY_ID_USER = "SELECT * FROM Journal WHERE idCourse IN (SELECT idCourse FROM CourseUser WHERE idUser = ?);";
    public static final String GET_ALL_JOURNALS = "SELECT * FROM Journal;";
    public static final String GET_JOURNALS_BY_ID_USER_LECTURER = "SELECT * FROM Journal WHERE idCourse IN (SELECT idCourse FROM Course WHERE idUserLecturer = ?);";


    public static final String GET_MARKS_BY_ID_USER = "SELECT * FROM Marks WHERE idUser = ?;";
    public static final String GET_MARKS_BY_ID_USE_AND_JOURNAL =  "SELECT * FROM Marks WHERE idUser = ? AND idJournal = ?;";
    public static final String GET_ALL_MARKS_BY_ID_USER_LECTURER = "SELECT * FROM Marks WHERE idJournal IN (SELECT idJournal FROM Journal WHERE idCourse IN (SELECT idCourse FROM Course WHERE idUserLecturer = ?));";
    public static final String GET_ALL_MARKS = "SELECT * FROM Marks;";
    public static final String GET_MARKS_BY_ID_JOURNAL = "SELECT * FROM Marks WHERE idJournal = ?;";
    public static final String GET_MARK_BY_ID = "select * from marks WHERE idMarks = ?;";
    public static final String INSERT_MARK = "INSERT INTO Marks (idUser, idJournal, Mark, Day) VALUES (?,?,?,?);";
    public static final String UPDATE_MARK = "UPDATE Marks SET idUser = ?, idJournal = ?, Mark = ?, Day = ? WHERE idMarks = ?;";
    public static final String DELETE_MARK = "DELETE FROM Marks WHERE idMarks = ?;";

    public static final String INSERT_COURSEUSER = "INSERT INTO CourseUser (idCourse, idUser) VALUES (?,?);";
    public static final String DELETE_COURSEUSER_BY_ID_COURSE = "DELETE FROM CourseUser WHERE idCourse = ?;";
    public static final String DELETE_COURSEUSER_BY_ID_USER = "DELETE FROM CourseUser WHERE idUser = ?;";
    public static final String DELETE_COURSEUSER_BY_ID_COURSE_AND_ID_USER = "DELETE FROM CourseUser WHERE idCourse = ? AND idUser = ?;";
    public static final String GET_COURSEUSER_BY_ID_USER = "SELECT * FROM CourseUser WHERE idUser = ?;";
    public static final String GET_ALL_COURSEUSERS = "SELECT * FROM CourseUser";
    public static final String GET_COURSEUSER_BY_ID_COURSE = "SELECT * FROM CourseUser WHERE idCourse = ?";

    public static final String GET_USER_BY_ID = "SELECT * FROM user WHERE idUser = ?;";
    public static final String GET_USER_BY_LOGIN = "SELECT * FROM user WHERE Login LIKE ?;";
    public static final String GET_USER_BY_CARD = "SELECT * FROM user WHERE Student_card_number LIKE ?;";
    public static final String GET_USER_BY_ID_COURSE = "SELECT * FROM user WHERE idUser IN (SELECT idUser FROM courseuser WHERE idCourse = ?);";
    public static final String DELETE_USER_BY_ID = "DELETE FROM User WHERE idUser = ?;";
    public static final String UPDATE_USER = "UPDATE User SET First_name = ?, Second_name = ?, Middle_name = ?, Login = ?, Password = ?,Salt = ?, Email = ?, Phone = ?, Photo = ?, Student_card_number = ?, Status_blocked = ? WHERE idUser = ?;";
    public static final String INSERT_USER = "INSERT INTO User (First_name, Second_name, Middle_name, Login, Password, Salt, Email, Phone, Photo, Student_card_number, Status_blocked) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    public static final String GET_USER_BY_LOGIN_PASSWORD = "SELECT * FROM user where Login LIKE ? AND Password LIKE ?;";
    public static final String GET_USER_BY_LOGIN_EMAIL_CARD_PHONE = "SELECT * FROM user where Login = ? OR email = ? OR Student_card_number = ? OR Phone = ?;";

    public static final String GET_ALL_ROLES = "SELECT * FROM Role;";
    public static final String GET_ROLE_BY_ID = "SELECT * FROM Role WHERE idRole = ?;";
    public static final String GET_ROLE_BY_NAME = "SELECT * FROM Role WHERE name = ?;";

    public static final String INSERT_ROLEUSER = "INSERT INTO RoleUser (idRole, idUser) VALUES (?,?);";
    public static final String DELETE_ROLEUSER_BY_ID_USER = "DELETE FROM RoleUser WHERE idUser = ?;";
    public static final String GET_ROLEUSER_BY_ID_USER = "SELECT * FROM RoleUser WHERE idUser = ?;";
    public static final String GET_ROLEUSER_BY_ID_ROLE = "SELECT * FROM RoleUser WHERE idRole = ?;";
    public static final String GET_ALL_ROLE_USERS = "SELECT * FROM RoleUser;";

    public static final String INSERT_PRIVATE_MESSAGE = "INSERT INTO PrivateMessage (idUser, idToUser, Body, Post_date) VALUES (?,?,?,?);";
    public static final String DELETE_PRIVATE_MESSAGE_BY_ID = "DELETE FROM PrivateMessage WHERE idPrivateMessage = ?;";
    public static final String GET_PRIVATE_MESSAGE_BY_ID_USER = "SELECT * FROM PrivateMessage WHERE idUser = ?;";
    public static final String GET_PRIVATE_MESSAGE_BY_ID_TO_USER = "SELECT * FROM PrivateMessage WHERE idToUser = ?;";
    public static final String GET_PRIVATE_MESSAGE_BY_ID_USERS = "SELECT * FROM PrivateMessage WHERE idUser = ? AND idToUser = ?;";
    public static final String GET_PRIVATE_MESSAGE_BY_ID_USERS_OR = "SELECT * FROM PrivateMessage WHERE idUser = ? OR idToUser = ?;";
    public static final String GET_CONVERSATION_BY_ID_USER = "SELECT * FROM privatemessage WHERE (idUser = ? AND idToUser = ?) || (idToUser = ? AND idUser = ?) ORDER BY Post_date;";

    private Query(){}
}
