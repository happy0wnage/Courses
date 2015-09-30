package ua.nure.petrov.Course.web.servlet.add;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.PrivateMessageDao;
import ua.nure.petrov.Course.db.entity.constants.*;
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

/**
 * Created by Владислав on 06.08.2015.
 */

@WebServlet(name = "writeMessage", urlPatterns = "/writeMessage")
public class WriteMessageServlet extends HttpServlet {

    public static final String ID_TO_USER = "idToUser";
    public static final String MESSAGE = "message";
    private Logger LOG = Logger.getLogger(WriteMessageServlet.class);

    private PrivateMessageDao privateMessageDao;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setPrivateMessageDao(servletContext);
    }

    private void setPrivateMessageDao(ServletContext servletContext) {
        privateMessageDao = (PrivateMessageDao) servletContext.getAttribute(ServletContextEntityNames.PRIVATE_MESSAGE_DAO);
        if (privateMessageDao == null) {
            throw new IllegalArgumentException("PrivateMessageDao DAO not initialized");
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
        String message = request.getParameter(MESSAGE);

        try {
            int idToUser = Integer.parseInt(request.getParameter(ID_TO_USER));
            PrivateMessage privateMessage = new PrivateMessage();
            privateMessage.setUser(user);
            privateMessage.setToUser(new User(idToUser));
            privateMessage.setBody(message);
            privateMessage.setPostDate(Util.getDateTime());

            privateMessageDao.insertPrivateMessage(privateMessage);

        } catch (IllegalArgumentException e) {
            LOG.error(e.getMessage());
        }
        response.sendRedirect(request.getHeader(RequestConstants.REFERER));
    }
}