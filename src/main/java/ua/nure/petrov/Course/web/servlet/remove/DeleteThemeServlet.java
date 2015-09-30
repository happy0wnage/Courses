package ua.nure.petrov.Course.web.servlet.remove;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.CourseDao;
import ua.nure.petrov.Course.db.dao.ThemeDao;
import ua.nure.petrov.Course.db.entity.constants.MessagesConstants;
import ua.nure.petrov.Course.db.entity.constants.RequestConstants;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;
import ua.nure.petrov.Course.db.entity.constants.SessionEntities;
import ua.nure.petrov.Course.db.entity.db.Course;
import ua.nure.petrov.Course.db.entity.db.Theme;
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
 * Created by Владислав on 13.08.2015.
 */

@WebServlet(name = "deleteTheme", urlPatterns = "/deleteTheme")
public class DeleteThemeServlet extends HttpServlet {

    private static final String ID_THEME = "idTheme";
    private Logger LOG = Logger.getLogger(DeleteThemeServlet.class);

    private ThemeDao themeDao;
    private CourseDao courseDao;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setThemeDao(servletContext);
        setCourseDao(servletContext);
    }

    private void setThemeDao(ServletContext servletContext) {
        themeDao = (ThemeDao) servletContext.getAttribute(ServletContextEntityNames.THEME_DAO);
        if (themeDao == null) {
            throw new IllegalArgumentException("Theme DAO not initialized");
        }
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

        int idTheme = Integer.parseInt(request.getParameter(ID_THEME));
        Theme theme = themeDao.getThemeById(idTheme);

        List<Course> courseList = courseDao.getCoursesByIdTheme(idTheme);
        request.setAttribute(SessionEntities.COURSES_TO_DELETE, courseList);
        request.setAttribute(SessionEntities.THEME_TO_DELETE, theme);

        if (courseList.size() == 0) {
            try {

                themeDao.deleteThemeById(idTheme);
                session.setAttribute(MessagesConstants.INFO_MSG, "You have successfully removed theme - \"" + theme.getName() + "\"");
            } catch (DBLayerException ex) {
                LOG.debug(ex.getLocalizedMessage());
            }
        }
        response.sendRedirect(request.getHeader(RequestConstants.REFERER));
    }
}