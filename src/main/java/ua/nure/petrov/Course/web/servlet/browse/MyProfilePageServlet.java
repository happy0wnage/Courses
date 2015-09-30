package ua.nure.petrov.Course.web.servlet.browse;

import ua.nure.petrov.Course.db.dao.CourseDao;
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
import ua.nure.petrov.Course.db.entity.exception.ApplicationInitializationException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Владислав on 03.08.2015.
 */

@WebServlet(name = "profilePage", urlPatterns = "/profilePage")
public class MyProfilePageServlet extends HttpServlet {

    private UserDao userDao;
    private CourseDao courseDao;
    private RoleUserDao roleUserDao;


    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setUsersDao(servletContext);
        setCourseDao(servletContext);
        setRoleUserDao(servletContext);
    }

    private void setUsersDao(ServletContext servletContext) {
        userDao = (UserDao) servletContext.getAttribute(ServletContextEntityNames.USER_DAO);
        if (userDao == null) {
            throw new ApplicationInitializationException("User dao is incorrect");
        }
    }

    private void setCourseDao(ServletContext servletContext) {
        courseDao = (CourseDao) servletContext.getAttribute(ServletContextEntityNames.COURSE_DAO);
        if (courseDao == null) {
            throw new IllegalArgumentException("Course DAO not initialized");
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
        resetUserInSession(request);
        request.getRequestDispatcher(Path.MY_PROFILE_PAGE).forward(request, response);
    }

    private void resetUserInSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute(SessionEntities.USER);
        User newUser = userDao.getUserById(sessionUser.getId());

        Role role = (Role) session.getAttribute(SessionEntities.ROLE);

        if (role.getName().equals(Role.ADMIN)) {
            Map<User, List<Role>> roles = new HashMap<>();
            List<RoleUser> ru = roleUserDao.getAllRoleUsers();

            for (RoleUser roleUser : ru) {

                List<RoleUser> roleUsers = roleUserDao.getRoleUserByIdUser(roleUser.getUser().getId());
                List<Role> r = new ArrayList<>();

                for (RoleUser rolesUser : roleUsers) {
                    r.add(rolesUser.getRole());
                }
                roles.put(roleUser.getUser(), r);
            }
            request.setAttribute(RequestConstants.ROLE_USER, roles);
        }

        if (role.getName().equals(Role.LECTURER)) {
            List<Course> courseList = courseDao.getCoursesByIdUserLecturer(newUser.getId());
            request.setAttribute(RequestConstants.COURSES, courseList);
        }

        session.setAttribute(SessionEntities.USER, newUser);
    }
}