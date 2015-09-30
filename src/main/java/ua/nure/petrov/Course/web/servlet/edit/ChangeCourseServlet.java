package ua.nure.petrov.Course.web.servlet.edit;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.CourseDao;
import ua.nure.petrov.Course.db.entity.constants.MessagesConstants;
import ua.nure.petrov.Course.db.entity.constants.RequestConstants;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;
import ua.nure.petrov.Course.db.entity.db.Course;
import ua.nure.petrov.Course.db.entity.db.Theme;
import ua.nure.petrov.Course.db.entity.db.User;
import ua.nure.petrov.Course.db.entity.exception.DBLayerException;
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
 * Created by Владислав on 14.08.2015.
 */

@WebServlet(name = "changeCourse", urlPatterns = "/changeCourse")
public class ChangeCourseServlet extends HttpServlet {

    private static final String ID_COURSE = "idCourse";
    private static final String ID_LECTURER = "idLecturer";
    private static final String ID_THEME = "idTheme";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String NOT_ALL_FIELDS_ARE_FILLED = "server.course.add";
    public static final String INCORRECT_DATE_VALUE = "server.course.invalid_date";
    private static final String DURATION = "duration";

    private CourseDao courseDao;
    private Logger LOG = Logger.getLogger(ChangeCourseServlet.class);

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
        Course course = fillCourseFromRequest(request);

        if(!EntityValidator.validateCourse(course)) {
            session.setAttribute(MessagesConstants.ERROR_MSG, INCORRECT_DATE_VALUE);
            response.sendRedirect(request.getHeader(RequestConstants.REFERER));
            LOG.error(INCORRECT_DATE_VALUE);
            return;
        }

        try {
            courseDao.updateCourse(course);
        } catch (DBLayerException e) {
            LOG.debug(e.getMessage());
        }

        response.sendRedirect(request.getHeader(RequestConstants.REFERER));
    }

    private Course fillCourseFromRequest(HttpServletRequest request) {

        int idCourse = Integer.parseInt(request.getParameter(ID_COURSE));
        Course course = courseDao.getCourseById(idCourse);

        int idLecturer = Integer.parseInt(request.getParameter(ID_LECTURER));
        int idTheme = Integer.parseInt(request.getParameter(ID_THEME));

        String duration = request.getParameter(DURATION);

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
        course.setName(request.getParameter(NAME));
        course.setDescription(request.getParameter(DESCRIPTION));
        course.setStartDate(java.sql.Date.valueOf(startDate));
        course.setEndDate(java.sql.Date.valueOf(endDate));
        return course;
    }
}