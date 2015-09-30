package ua.nure.petrov.Course.web.servlet.browse;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.CourseDao;
import ua.nure.petrov.Course.db.dao.RoleDao;
import ua.nure.petrov.Course.db.dao.RoleUserDao;
import ua.nure.petrov.Course.db.dao.ThemeDao;
import ua.nure.petrov.Course.db.entity.constants.Path;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;
import ua.nure.petrov.Course.db.entity.constants.SessionEntities;
import ua.nure.petrov.Course.db.entity.db.*;
import ua.nure.petrov.Course.db.entity.exception.DBLayerException;
import ua.nure.petrov.Course.db.entity.sorter.CourseOrder;

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
 * Created by Владислав on 30.07.2015.
 */
@WebServlet(name = "index", urlPatterns = "/index.html")
public class HomePageServlet extends HttpServlet {

    private static final String SORTED_VALUE = "sortedValue";
    private static final String SORTED_VALUE_BY_DATE = "date";
    private static final String SORTED_VALUE_BY_STUD_COUNT = "count";
    private static final String SORTED_VALUE_BY_DURATION = "duration";
    private static final String SORTED_VALUE_BY_NAME = "name";

    private static final String ID_THEME = "idTheme";
    private static final String ID_LECTURER = "idLecturer";
    private static final String NO_COURSES_AVAILABLE = "main.no_courses_available";
    private static final String NO_COURSE_MESSAGE = "noCourseMessage";

    private CourseDao courseDao;
    private ThemeDao themeDao;
    private RoleUserDao roleUserDao;
    private RoleDao roleDao;

    private static Logger LOG = Logger.getLogger(HomePageServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setCourseDao(servletContext);
        setRoleUserDao(servletContext);
        setThemeDao(servletContext);
        setRoleDao(servletContext);
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

    private void setRoleUserDao(ServletContext servletContext) {
        roleUserDao = (RoleUserDao) servletContext.getAttribute(ServletContextEntityNames.ROLE_USER_DAO);
        if (roleUserDao == null) {
            throw new IllegalArgumentException("Role user DAO not initialized");
        }
    }

    private void setRoleDao(ServletContext servletContext) {
        roleDao = (RoleDao) servletContext.getAttribute(ServletContextEntityNames.ROLE_DAO);
        if (roleDao == null) {
            throw new IllegalArgumentException("Role DAO not initialized");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Course> courses;
        HttpSession session = request.getSession();

        //Get courses without parameters
        try {
            User user = (User) session.getAttribute(SessionEntities.USER);
            courses = courseDao.getCoursesWithoutIdUser(user.getId());
        } catch (NullPointerException e) {
            courses = courseDao.getAllCourses();
            LOG.error(e);
        }

        if (courses.size() == 0) {
            session.setAttribute(NO_COURSE_MESSAGE, NO_COURSES_AVAILABLE);
        }

        Collections.sort(courses, CourseOrder.SORT_BY_NEAREST_DATE);

        for (Course course : courses) {
            course.setUsersCount(courseDao.getUserCountByIdCourse(course.getId()));
        }

        //Get availableLecturers
        Set<User> availableLecturers = getAvailableLecturers(courses);

        //Get availableThemes
        Set<Theme> availableThemes = getAvailableThemes(courses);

        //Get courses with parameters: theme or lecturer
        List<Course> newCourse = getCourseByThemeORLecturer(request, courses);

        //Sorting courses by categories
        sortCourses(courses, request, session);

        List<Theme> themes = null;
        List<RoleUser> roleUsers = null;
        Role lecturer = roleDao.getRoleByName(Role.LECTURER);
        try {
            themes = themeDao.getAllThemes();
            roleUsers = roleUserDao.getRoleUserByIdRole(lecturer.getId());
        } catch (DBLayerException e) {
            LOG.error(e.getMessage(), e);
        }

        if (newCourse.size() == 0) {
            request.setAttribute(SessionEntities.COURSE, courses);
        } else {
            request.setAttribute(SessionEntities.COURSE, newCourse);
        }

        request.setAttribute(SessionEntities.ROLE_USER, roleUsers);
        request.setAttribute(SessionEntities.THEME, themes);

        request.setAttribute(SessionEntities.AVAILABLE_THEMES, availableThemes);
        request.setAttribute(SessionEntities.AVAILABLE_LECTURERS, availableLecturers);

        request.getRequestDispatcher(Path.HOME_PAGE).forward(request, response);
    }

    private static Set<User> getAvailableLecturers(List<Course> courses) {
        Set<User> availableLecturers = new HashSet<>();
        for (Course course : courses) {
            availableLecturers.add(course.getUserLecturer());
        }
        return availableLecturers;
    }

    private static Set<Theme> getAvailableThemes(List<Course> courses) {
        Set<Theme> availableThemes = new HashSet<>();
        for (Course course : courses) {
            availableThemes.add(course.getTheme());
        }
        return availableThemes;
    }

    private static List<Course> getCourseByThemeORLecturer(HttpServletRequest request, List<Course> courses) {
        List<Course> newCourse = new ArrayList<>();

        try {
            int idTheme = Integer.parseInt(request.getParameter(ID_THEME));
            for (Course course : courses) {
                if (course.getTheme().getId() == idTheme) {
                    newCourse.add(course);
                }
            }
        } catch (IllegalArgumentException e) {
            LOG.debug(e.getMessage());
        }

        try {
            int idLecturer = Integer.parseInt(request.getParameter(ID_LECTURER));
            for (Course course : courses) {

                if (course.getUserLecturer().getId() == idLecturer) {
                    newCourse.add(course);
                }
            }
        } catch (IllegalArgumentException e) {
            LOG.debug(e.getMessage());
        }

        return newCourse;
    }

    private static void sortCourses(List<Course> courses, HttpServletRequest request, HttpSession session) {
        try {
            String sortedValue = request.getParameter(SORTED_VALUE);

            if (sortedValue != null) {
                switch (sortedValue) {
                    case SORTED_VALUE_BY_DATE:
                        Collections.sort(courses, CourseOrder.SORT_BY_START_DATE);
                        break;
                    case SORTED_VALUE_BY_DURATION:
                        Collections.sort(courses, CourseOrder.SORT_BY_DURATION);
                        break;
                    case SORTED_VALUE_BY_NAME:
                        Collections.sort(courses, CourseOrder.SORT_BY_NAME);
                        break;
                    case SORTED_VALUE_BY_STUD_COUNT:
                        Collections.sort(courses, CourseOrder.SORT_BY_USER_COUNT);
                        break;
                    default:
                        break;
                }
            }
        } catch (IllegalArgumentException e) {
            LOG.debug(e.getMessage());
        }

    }
}
