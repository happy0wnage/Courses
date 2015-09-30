package ua.nure.petrov.Course.web.servlet.browse;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.CourseDao;
import ua.nure.petrov.Course.db.dao.RoleDao;
import ua.nure.petrov.Course.db.dao.RoleUserDao;
import ua.nure.petrov.Course.db.dao.UserDao;
import ua.nure.petrov.Course.db.entity.constants.Path;
import ua.nure.petrov.Course.db.entity.constants.RequestConstants;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;
import ua.nure.petrov.Course.db.entity.constants.SessionEntities;
import ua.nure.petrov.Course.db.entity.db.Course;
import ua.nure.petrov.Course.db.entity.db.Role;
import ua.nure.petrov.Course.db.entity.db.RoleUser;
import ua.nure.petrov.Course.db.entity.db.User;

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
 * Created by Владислав on 03.08.2015.
 */

@WebServlet(name = "profile", urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {

    private static final String ID_USER = "idUser";
    private static final String USER_PROFILE = "userProfile";
    private static final String ROLE_PROFILE = "roleProfile";
    private static final String USER_ROLE_PROFILE = "userRoleProfile";
    private UserDao userDao;
    private CourseDao courseDao;
    private RoleDao roleDao;
    private RoleUserDao roleUserDao;

    private Logger LOG = Logger.getLogger(ProfileServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setUserDao(servletContext);
        setRoleDao(servletContext);
        setCourseDao(servletContext);
        setRoleUserDao(servletContext);
    }


    private void setUserDao(ServletContext servletContext) {
        userDao = (UserDao) servletContext.getAttribute(ServletContextEntityNames.USER_DAO);
        if (userDao == null) {
            throw new IllegalArgumentException("User DAO not initialized");
        }
    }

    private void setCourseDao(ServletContext servletContext) {
        courseDao = (CourseDao) servletContext.getAttribute(ServletContextEntityNames.COURSE_DAO);
        if (courseDao == null) {
            throw new IllegalArgumentException("Course DAO not initialized");
        }
    }

    private void setRoleDao(ServletContext servletContext) {
        roleDao = (RoleDao) servletContext.getAttribute(ServletContextEntityNames.ROLE_DAO);
        if (roleDao == null) {
            throw new IllegalArgumentException("Role DAO not initialized");
        }
    }

    private void setRoleUserDao(ServletContext servletContext) {
        roleUserDao = (RoleUserDao) servletContext.getAttribute(ServletContextEntityNames.ROLE_USER_DAO);
        if (roleUserDao == null) {
            throw new IllegalArgumentException("RoleUser DAO not initialized");
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
        User sessionUser = (User) session.getAttribute(SessionEntities.USER);

        int idUser = Integer.parseInt(request.getParameter(ID_USER));
        User user = userDao.getUserById(idUser);
        List<RoleUser> roleUsers = roleUserDao.getRoleUserByIdUser(idUser);

        for (RoleUser roleUser : roleUsers) {
            if (roleUser.getRole().getName().equals(Role.LECTURER)) {
                List<Course> courseList = courseDao.getCoursesByIdUserLecturer(user.getId());
                request.setAttribute(RequestConstants.COURSES, courseList);
            }
        }

        if (user.getId() == sessionUser.getId()) {
            request.getRequestDispatcher(Path.MY_PROFILE_PAGE).forward(request, response);
            return;
        }

        request.setAttribute(USER_PROFILE, user);
        request.setAttribute(USER_ROLE_PROFILE, roleUsers);

        request.getRequestDispatcher(Path.PROFILE_PAGE).forward(request, response);
    }
}