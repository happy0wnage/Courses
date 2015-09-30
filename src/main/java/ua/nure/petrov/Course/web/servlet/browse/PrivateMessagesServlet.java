package ua.nure.petrov.Course.web.servlet.browse;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.PrivateMessageDao;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Владислав on 07.08.2015.
 */

@WebServlet(name = "messagesPage", urlPatterns = "/messagesPage")
public class PrivateMessagesServlet extends HttpServlet {

    private Logger LOG = Logger.getLogger(PrivateMessagesServlet.class);

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

        List<PrivateMessage> privateMessagesForUsers = privateMessageDao.getPrivateMessagesByIdUsersOR(user.getId(), user.getId());


        Collections.reverse(privateMessagesForUsers);

        Map<User, PrivateMessage> messages = new HashMap<>();
        PrivateMessage lastPrivateMessage;

        for (PrivateMessage privateMessage : privateMessagesForUsers) {
            List<PrivateMessage> pm = privateMessageDao.getConversationByIdUsers(privateMessage.getToUser().getId(), privateMessage.getUser().getId());

            Collections.reverse(pm);
            lastPrivateMessage = pm.get(0);


            if (lastPrivateMessage.getToUser().getId() == user.getId()) {
                messages.put(privateMessage.getUser(), lastPrivateMessage);
            } else if (lastPrivateMessage.getUser().getId() == user.getId()) {
                messages.put(privateMessage.getToUser(), lastPrivateMessage);
            }
        }

        request.setAttribute(RequestConstants.PRIVATE_MESSAGES, messages);
        request.getRequestDispatcher(Path.MESSAGES_PAGE).forward(request, response);

    }
}