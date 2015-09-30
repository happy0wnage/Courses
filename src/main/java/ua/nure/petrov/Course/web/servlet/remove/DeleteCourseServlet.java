package ua.nure.petrov.Course.web.servlet.remove;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.CourseUserDao;
import ua.nure.petrov.Course.db.entity.constants.MessagesConstants;
import ua.nure.petrov.Course.db.entity.constants.Path;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
* Created by Владислав on 05.08.2015.
*/

@WebServlet(name = "deleteCourse", urlPatterns = "/deleteCourse")
public class DeleteCourseServlet extends HttpServlet {

    private static final String ID_COURSE = "idCourse";
    private CourseUserDao courseUserDao;

    private Logger LOG = Logger.getLogger(DeleteCourseServlet.class);

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
        try {
            int idCourse = Integer.parseInt(request.getParameter(ID_COURSE));

            courseUserDao.deleteCourseUserByIdCourse(idCourse);
        } catch (NumberFormatException e) {
            LOG.debug(MessagesConstants.OTHER_ERROR, e);
        }
        request.getRequestDispatcher(Path.HOME_SERVLET).forward(request, response);

    }
}