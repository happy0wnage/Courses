package ua.nure.petrov.Course.web.servlet.browse;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.*;
import ua.nure.petrov.Course.db.entity.constants.Path;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;
import ua.nure.petrov.Course.db.entity.constants.SessionEntities;
import ua.nure.petrov.Course.db.entity.db.*;
import ua.nure.petrov.Course.db.entity.exception.DBLayerException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Владислав on 01.08.2015.
 */

@WebServlet(name = "coursesPage", urlPatterns = "/coursesPage")
public class CoursePageServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(CoursePageServlet.class);

    private CourseDao courseDao;
    private ThemeDao themeDao;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setCourseDao(servletContext);
        setThemeDao(servletContext);
    }

    private void setCourseDao(ServletContext servletContext) {
        courseDao = (CourseDao) servletContext.getAttribute(ServletContextEntityNames.COURSE_DAO);
        if (courseDao == null) {
            throw new IllegalArgumentException("Course DAO not initialized");
        }
    }

    private void setThemeDao(ServletContext servletContext) {
        themeDao = (ThemeDao) servletContext.getAttribute(ServletContextEntityNames.THEME_DAO);
        if (themeDao == null) {
            throw new IllegalArgumentException("Theme DAO not initialized");
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
        Role role = (Role) session.getAttribute(SessionEntities.ROLE);

        List<Course> courses = null;
        List<Theme> themes = null;
        try {
            courses = getCoursesForUserRole(user.getId(), role.getName());
            themes = themeDao.getAllThemes();
        } catch (DBLayerException e) {
            LOG.error(e.getMessage(), e);
        }

        request.setAttribute(SessionEntities.COURSE, courses);
        request.setAttribute(SessionEntities.THEME, themes);
        request.getRequestDispatcher(Path.COURSES_PAGE).forward(request, response);
    }

    private List<Course> getCoursesForUserRole(int userId, String roleName) {
        switch (roleName) {
            case Role.ADMIN:
                return courseDao.getAllCourses();
            case Role.LECTURER:
                return courseDao.getCoursesByIdUserLecturer(userId);
            default:
                return courseDao.getCoursesByIdUser(userId);
        }
    }
}