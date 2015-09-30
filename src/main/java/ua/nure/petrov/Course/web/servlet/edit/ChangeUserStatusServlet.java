package ua.nure.petrov.Course.web.servlet.edit;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.UserDao;
import ua.nure.petrov.Course.db.entity.constants.RequestConstants;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;
import ua.nure.petrov.Course.db.entity.db.User;
import ua.nure.petrov.Course.db.entity.exception.DBLayerException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Владислав on 11.08.2015.
 */

@WebServlet(name = "changeUserStatus", urlPatterns = "/changeUserStatus")
public class ChangeUserStatusServlet extends HttpServlet {

    public static final String ID_USER = "idUser";
    private Logger LOG = Logger.getLogger(ChangeUserStatusServlet.class);

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

        int idUser = Integer.parseInt(request.getParameter(ID_USER));
        User user = userDao.getUserById(idUser);

        if(user.isStatusBlocked()) {
            user.setStatusBlocked(false);
        } else {
            user.setStatusBlocked(true);
        }

        try {
            userDao.updateUser(user);
        } catch (DBLayerException e) {
            LOG.debug(e.getMessage());
        }

        response.sendRedirect(request.getHeader(RequestConstants.REFERER));

    }
}