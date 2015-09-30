package ua.nure.petrov.Course.web.servlet.add;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.CourseDao;
import ua.nure.petrov.Course.db.entity.constants.MessagesConstants;
import ua.nure.petrov.Course.db.entity.constants.RequestConstants;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;
import ua.nure.petrov.Course.db.entity.db.Course;
import ua.nure.petrov.Course.db.entity.db.Theme;
import ua.nure.petrov.Course.db.entity.db.User;
import ua.nure.petrov.Course.db.entity.validator.EntityValidator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Владислав on 05.08.2015.
 */

@WebServlet(name = "addCourse", urlPatterns = "/addCourse")
public class AddCourseServlet extends HttpServlet {

    public static final String ID_USER = "inputLecturer";
    public static final String ID_THEME = "inputTheme";
    public static final String INPUT_NAME = "inputName";
    public static final String INPUT_DESCRIPTION = "inputDescription";
    public static final String START_DATE = "inputStartDate";
    public static final String END_DATE = "inputEndDate";
    public static final String NOT_ALL_FIELDS_ARE_FILLED = "server.course.add";
    public static final String INCORRECT_DATE_VALUE = "server.course.invalid_date";

    private Logger LOG = Logger.getLogger(AddCourseServlet.class);

    private CourseDao courseDao;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setCourseDao(servletContext);
    }

    private void setCourseDao(ServletContext servletContext) {
        courseDao = (CourseDao) servletContext.getAttribute(ServletContextEntityNames.COURSE_DAO);
        if (courseDao == null) {
            throw new IllegalArgumentException("Course DAO not initialized");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Course course = fillCourseFromRequest(request, session);

        if(!EntityValidator.validateCourse(course)) {
            session.setAttribute(MessagesConstants.ERROR_MSG, INCORRECT_DATE_VALUE);
            response.sendRedirect(request.getHeader(RequestConstants.REFERER));
            LOG.error(NOT_ALL_FIELDS_ARE_FILLED);
            return;
        }

        courseDao.insertCourse(course);

        response.sendRedirect(request.getHeader(RequestConstants.REFERER));
    }

    private Course fillCourseFromRequest(HttpServletRequest request, HttpSession session) {
        Course course = new Course();


        int idLecturer = Integer.parseInt(request.getParameter(ID_USER));
        int idTheme = Integer.parseInt(request.getParameter(ID_THEME));


        String duration = request.getParameter("duration");

        String pattern = "([0-3][0-9]\\.[0-2][0-9]\\.[0-9]{4})\\s-\\s([0-2][0-9]\\.[0-2][0-9]\\.[0-9]{4})";
        Pattern r = Pattern.compile(pattern);

        String startDate = null;
        String endDate = null;

        Matcher m = r.matcher(duration);
        while (m.find()) {
            startDate = m.group(1);
            endDate = m.group(2);
        }

        final String OLD_FORMAT = "dd.MM.yyyy";
        final String NEW_FORMAT = "yyyy-MM-dd";

        Date start = null;
        Date end = null;

        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        try {
            start = sdf.parse(startDate);
            end = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        sdf.applyPattern(NEW_FORMAT);

        startDate = sdf.format(start);
        endDate = sdf.format(end);

        course.setUserLecturer(new User(idLecturer));
        course.setTheme(new Theme(idTheme));
        course.setName(request.getParameter(INPUT_NAME));
        course.setDescription(request.getParameter(INPUT_DESCRIPTION));
        course.setStartDate(java.sql.Date.valueOf(startDate));
        course.setEndDate(java.sql.Date.valueOf(endDate));
        return course;
    }

}