package ua.nure.petrov.Course.web.servlet.remove;

import ua.nure.petrov.Course.db.dao.CourseUserDao;
import ua.nure.petrov.Course.db.entity.constants.*;
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

@WebServlet(name = "unsubscribeCourse", urlPatterns = "/unsubscribeCourse")
public class UnsubscribeCourseServlet extends HttpServlet {

    public static final String NAME = "name";
    public static final String WARNING_MESSAGE = "server.course.leave";
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

        int idCourse = Integer.parseInt(request.getParameter(Fields.COURSE_ID_COURSE));

        courseUserDao.deleteCourseUserByIdCourseAndIdUser(user.getId(), idCourse);

        response.sendRedirect(request.getHeader(RequestConstants.REFERER));
        session.setAttribute(MessagesConstants.WARNING_MSG, WARNING_MESSAGE);
    }
}