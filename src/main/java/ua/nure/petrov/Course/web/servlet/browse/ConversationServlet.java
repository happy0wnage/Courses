package ua.nure.petrov.Course.web.servlet.browse;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.PrivateMessageDao;
import ua.nure.petrov.Course.db.dao.UserDao;
import ua.nure.petrov.Course.db.entity.constants.Path;
import ua.nure.petrov.Course.db.entity.constants.RequestConstants;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;
import ua.nure.petrov.Course.db.entity.constants.SessionEntities;
import ua.nure.petrov.Course.db.entity.db.PrivateMessage;
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
 * Created by Владислав on 06.08.2015.
 */

@WebServlet(name = "conversationServlet", urlPatterns = "/conversationServlet")
public class ConversationServlet extends HttpServlet {

    public static final String ID_TO_USER = "idToUser";
    private Logger LOG = Logger.getLogger(ConversationServlet.class);

    private PrivateMessageDao privateMessageDao;
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setPrivateMessageDao(servletContext);
        setUserDao(servletContext);
    }

    private void setPrivateMessageDao(ServletContext servletContext) {
        privateMessageDao = (PrivateMessageDao) servletContext.getAttribute(ServletContextEntityNames.PRIVATE_MESSAGE_DAO);
        if (privateMessageDao == null) {
            throw new IllegalArgumentException("PrivateMessageDao DAO not initialized");
        }
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
        User fstUser = (User) session.getAttribute(SessionEntities.USER);

        try {
            int idToUser = Integer.parseInt(request.getParameter(ID_TO_USER));
            User sndUser = userDao.getUserById(idToUser);
            List<PrivateMessage> privateMessages = privateMessageDao.getConversationByIdUsers(fstUser.getId(), sndUser.getId());

            request.setAttribute(RequestConstants.PRIVATE_MESSAGES, privateMessages);
        } catch (IllegalArgumentException e) {
            LOG.error(e.getMessage());
        }
        request.getRequestDispatcher(Path.CONVERSATION_PAGE).forward(request, response);

    }
}