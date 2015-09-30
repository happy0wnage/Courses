package ua.nure.petrov.Course.web.servlet.browse;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.CourseDao;
import ua.nure.petrov.Course.db.dao.NewsDao;
import ua.nure.petrov.Course.db.entity.constants.MessagesConstants;
import ua.nure.petrov.Course.db.entity.constants.Path;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;
import ua.nure.petrov.Course.db.entity.constants.SessionEntities;
import ua.nure.petrov.Course.db.entity.db.Course;
import ua.nure.petrov.Course.db.entity.db.News;
import ua.nure.petrov.Course.db.entity.db.Role;
import ua.nure.petrov.Course.db.entity.db.User;
import ua.nure.petrov.Course.db.entity.sorter.NewsOrder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Created by Владислав on 02.08.2015.
 */

@WebServlet(name = "newsPage", urlPatterns = "/newsPage")
public class NewsPageServlet extends HttpServlet {

    private static final String ID_COURSE = "idCourse";
    private static final String NO_NEWS = "main.news.no_news";

    private NewsDao newsDao;
    private CourseDao courseDao;

    private Logger LOG = Logger.getLogger(NewsPageServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setNewsDao(servletContext);
        setCourseDao(servletContext);
    }

    private void setNewsDao(ServletContext servletContext) {
        newsDao = (NewsDao) servletContext.getAttribute(ServletContextEntityNames.NEWS_DAO);
        if (newsDao == null) {
            throw new IllegalArgumentException("News DAO not initialized");
        }
    }

    private void setCourseDao(ServletContext servletContext) {
        courseDao = (CourseDao) servletContext.getAttribute(ServletContextEntityNames.COURSE_DAO);
        if (courseDao == null) {
            throw new IllegalArgumentException("Course DAO not initialized");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute(SessionEntities.USER);
        Role role = (Role) session.getAttribute(SessionEntities.ROLE);

        Map<Course, List<News>> newsCourse = new HashMap<>();
        List<Course> courses = getCourseForRole(role.getName(), user);

        List<News> news = null;
        try {
            int idCourse = Integer.parseInt(request.getParameter(ID_COURSE));
            news = newsDao.getNewsByIdCourse(idCourse);
            Collections.sort(news, NewsOrder.SORT_BY_POST_DATE);

            Course course = courseDao.getCourseById(idCourse);
            newsCourse.put(course, news);
        } catch (NumberFormatException e) {
            for (Course course : courses) {
                news = newsDao.getNewsByIdCourse(course.getId());
                Collections.sort(news, NewsOrder.SORT_BY_POST_DATE);
                newsCourse.put(course, news);
            }
        }

        if(news.size() == 0) {
           session.setAttribute(MessagesConstants.ALERT_MSG, NO_NEWS);
        }

        request.setAttribute(SessionEntities.NEWS_COURSE, newsCourse);
        request.setAttribute(SessionEntities.COURSE, courses);
        request.getRequestDispatcher(Path.NEWS_PAGE).
                forward(request, response);
    }

    private List<Course> getCourseForRole(String role, User user) {
        switch (role) {
            case Role.LECTURER:
                return courseDao.getCoursesByIdUserLecturer(user.getId());
            case Role.ADMIN:
                return courseDao.getAllCourses();
            default:
                return courseDao.getCoursesByIdUser(user.getId());
        }
    }
}