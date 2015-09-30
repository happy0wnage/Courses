package ua.nure.petrov.Course.web.servlet.edit;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.UserDao;
import ua.nure.petrov.Course.db.entity.constants.MessagesConstants;
import ua.nure.petrov.Course.db.entity.constants.RequestConstants;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;
import ua.nure.petrov.Course.db.entity.constants.SessionEntities;
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

/**
 * Created by Владислав on 14.08.2015.
 */

@WebServlet(name = "changeProfile", urlPatterns = "/changeProfile")
public class ChangeProfileServlet extends HttpServlet {

    private static final String ID_USER = "idUser";
    private static final String LOGIN = "login";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String CARD = "card";
    private static final String INCORRECT_USER = "Invalid data to update ";
    private static final String INCORRECT_USER_KEY = "server.profile.update.email";
    private Logger LOG = Logger.getLogger(ChangeProfileServlet.class);

    private UserDao userDao;

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
        User user = fillUserFromRequest(request, session);

        if(!EntityValidator.validateProfile(user)) {
            session.setAttribute(MessagesConstants.ERROR_MSG, INCORRECT_USER_KEY);
            LOG.debug(INCORRECT_USER);
            response.sendRedirect(request.getHeader(RequestConstants.REFERER));
            return;
        }

        try {
            userDao.updateUser(user);
        } catch (DBLayerException e) {
            LOG.debug(e.getMessage());
        }

        session.setAttribute(SessionEntities.USER, user);
        response.sendRedirect(request.getHeader(RequestConstants.REFERER));
    }

    private User fillUserFromRequest(HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute(SessionEntities.USER);

        String login = request.getParameter(LOGIN);
        String email = request.getParameter(EMAIL);
        String phone = request.getParameter(PHONE);
        String studentCardNumber = request.getParameter(CARD);

        user.setLogin(login);
        user.setEmail(email);
        user.setPhone(phone);
        user.setStudentCardNumber(studentCardNumber);

        return user;
    }
}