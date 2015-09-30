package ua.nure.petrov.Course.web.servlet.edit;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.UserDao;
import ua.nure.petrov.Course.db.entity.constants.*;
import ua.nure.petrov.Course.db.entity.db.PasswordHash;
import ua.nure.petrov.Course.db.entity.db.User;
import ua.nure.petrov.Course.db.entity.exception.DBLayerException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Владислав on 14.08.2015.
 */

@WebServlet(name = "changePassword", urlPatterns = "/changePassword")
public class ChangePasswordServlet extends HttpServlet {

    private static final String OLD_PASSWORD = "oldPassword";
    private static final String NEW_PASSWORD = "newPassword";
    private static final String NEW_PASSWORD_REPEAT = "newPasswordRepeat";
    public static final String OLD_PWD_ERROR = "Old password entered incorrectly";
    public static final String NEW_PWD_ERROR = "Passwords do not match";
    private UserDao userDao;
    private Logger LOG = Logger.getLogger(ChangePasswordServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setUserDao(servletContext);
    }

    private void setUserDao(ServletContext servletContext) {
        userDao = (UserDao) servletContext.getAttribute(ServletContextEntityNames.USER_DAO);
        if (userDao == null) {
            throw new IllegalArgumentException("User DAO not initialized");
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

        String oldPassword = request.getParameter(OLD_PASSWORD);
        oldPassword = PasswordHash.sha256(user.getSalt() + oldPassword);

        if(!user.getPassword().equals(oldPassword)) {
            session.setAttribute(MessagesConstants.ERROR_MSG, OLD_PWD_ERROR);
            LOG.debug(OLD_PWD_ERROR);
            response.sendRedirect(request.getHeader(RequestConstants.REFERER));
            return;
        }

        String newPassword = request.getParameter(NEW_PASSWORD);
        String newPasswordRepeat = request.getParameter(NEW_PASSWORD_REPEAT);

        if(newPassword.equals(newPasswordRepeat)) {
            user.setPassword(PasswordHash.sha256(user.getSalt() + newPassword));

            try {
                userDao.updateUser(user);
            } catch (DBLayerException e) {
                LOG.debug(e.getMessage());
            }

        } else {
            session.setAttribute(MessagesConstants.ERROR_MSG, NEW_PWD_ERROR);
            LOG.debug(NEW_PWD_ERROR);
            response.sendRedirect(request.getHeader(RequestConstants.REFERER));
            return;
        }

        response.sendRedirect(Path.LOGOUT_SERVLET);


    }
}