package ua.nure.petrov.Course.web.servlet.add;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.RoleUserDao;
import ua.nure.petrov.Course.db.dao.UserDao;
import ua.nure.petrov.Course.db.entity.constants.MessagesConstants;
import ua.nure.petrov.Course.db.entity.constants.RequestConstants;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;
import ua.nure.petrov.Course.db.entity.constants.SessionEntities;
import ua.nure.petrov.Course.db.entity.db.PasswordHash;
import ua.nure.petrov.Course.db.entity.db.Role;
import ua.nure.petrov.Course.db.entity.db.RoleUser;
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
import java.util.List;

/**
 * Created by Владислав on 31.07.2015.
 */

@WebServlet(name = "login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(LoginServlet.class);

    private static final String EMPTY_LOGIN_OR_PASSWORD = "server.login.empty_login_pass";
    private static final String USER_BLOCKED = "server.login.blocked";
    private static final String VIEW_LOGIN = "login";
    private static final String VIEW_PASSWORD = "password";
    private static final String INCORRECT_LOGIN_PASSWORD = "server.login.incorrect_login_pass";

    private UserDao userDao;
    private RoleUserDao roleUserDao;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setUserDao(servletContext);
        setRoleUserDao(servletContext);
    }

    private void setUserDao(ServletContext servletContext) {
        userDao = (UserDao) servletContext.getAttribute(ServletContextEntityNames.USER_DAO);
        if (userDao == null) {
            throw new IllegalArgumentException("User DAO not initialized");
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

        String login = request.getParameter(VIEW_LOGIN);
        String password = request.getParameter(VIEW_PASSWORD);

        if (!EntityValidator.validateLoginPassword(login, password)) {
            response.sendRedirect(request.getHeader(RequestConstants.REFERER));
            session.setAttribute(MessagesConstants.LOGIN_ERROR_MSG, EMPTY_LOGIN_OR_PASSWORD);
            LOG.debug(EMPTY_LOGIN_OR_PASSWORD);
            return;
        }

        User user;

        try {
            user = userDao.getUserByLogin(login);
            String hash = PasswordHash.sha256(user.getSalt() + password);
            if (user.getPassword().equals(hash)) {
                if (user.isStatusBlocked()) {
                    response.sendRedirect(request.getHeader(RequestConstants.REFERER));
                    session.setAttribute(MessagesConstants.LOGIN_ERROR_MSG, USER_BLOCKED);
                    LOG.debug(USER_BLOCKED);
                    return;
                }

                List<RoleUser> roleUsers = roleUserDao.getRoleUserByIdUser(user.getId());
                Role role = roleUsers.get(0).getRole();

                session.setAttribute(SessionEntities.USER, user);
                session.setAttribute(SessionEntities.USER_ROLE, roleUsers);
                session.setAttribute(SessionEntities.ROLE, role);
            } else {
                session.setAttribute(MessagesConstants.LOGIN_ERROR_MSG, INCORRECT_LOGIN_PASSWORD);
                LOG.debug(INCORRECT_LOGIN_PASSWORD);
            }
        } catch (DBLayerException e) {
            session.setAttribute(MessagesConstants.LOGIN_ERROR_MSG, INCORRECT_LOGIN_PASSWORD);
            LOG.debug(e.getLocalizedMessage());
        }

        response.sendRedirect(request.getHeader(RequestConstants.REFERER));
    }
}
