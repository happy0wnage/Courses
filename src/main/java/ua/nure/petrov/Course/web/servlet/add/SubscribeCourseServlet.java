package ua.nure.petrov.Course.web.servlet.add;

import ua.nure.petrov.Course.db.dao.CourseUserDao;
import ua.nure.petrov.Course.db.entity.constants.*;
import ua.nure.petrov.Course.db.entity.db.Course;
import ua.nure.petrov.Course.db.entity.db.CourseUser;
import ua.nure.petrov.Course.db.entity.db.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Владислав on 04.08.2015.
 */

@WebServlet(name = "subscribeCourse", urlPatterns = "/subscribeCourse")
public class SubscribeCourseServlet extends HttpServlet {

    private static final String ALERT_MSG = "server.course.success";
    private static final String NOT_LOGINED_ERR_MSG = "server.course.subscribe";
    private static final String NAME = "name";

    private CourseUserDao courseUserDao;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setCourseUserDao(servletContext);
    }

    private void setCourseUserDao(ServletContext servletContext) {
        courseUserDao = (CourseUserDao) servletContext.getAttribute(ServletContextEntityNames.COURSE_USER_DAO);
        if (courseUserDao == null) {
            throw new IllegalArgumentException("Course User DAO not initialized");
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
        User user = (User) session.getAttribute(SessionEntities.USER);
        if (user == null) {
            session.setAttribute(MessagesConstants.ERROR_MSG, NOT_LOGINED_ERR_MSG);
            response.sendRedirect(request.getHeader(RequestConstants.REFERER));
            return;
        }

        int idCourse = Integer.parseInt(request.getParameter(Fields.COURSE_ID_COURSE));
        String courseName = request.getParameter(NAME);

        CourseUser courseUser = new CourseUser();
        courseUser.setUser(user);
        courseUser.setCourse(new Course(idCourse));

        courseUserDao.insertCourseUser(courseUser);

        session.setAttribute(MessagesConstants.ALERT_MSG, ALERT_MSG);
        response.sendRedirect(request.getHeader(RequestConstants.REFERER));
    }
}