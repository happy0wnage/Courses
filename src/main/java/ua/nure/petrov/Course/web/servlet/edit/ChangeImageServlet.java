package ua.nure.petrov.Course.web.servlet.edit;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.NewsDao;
import ua.nure.petrov.Course.db.dao.UserDao;
import ua.nure.petrov.Course.db.entity.constants.*;
import ua.nure.petrov.Course.db.entity.db.News;
import ua.nure.petrov.Course.db.entity.db.User;
import ua.nure.petrov.Course.db.entity.exception.DBLayerException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Владислав on 11.08.2015.
 */

@WebServlet(name = "changeImage", urlPatterns = "/changeImage")
@MultipartConfig(maxFileSize = 16177215)
public class ChangeImageServlet extends HttpServlet {

    private Logger LOG = Logger.getLogger(ChangeImageServlet.class);

    private static final String SOURCE = "source";
    private static final String ID = "id";

    private static final String INCORRECT_FILE_TYPE = "main.error_file_type";

    private UserDao userDao;
    private NewsDao newsDao;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setUserDao(servletContext);
        setNewsDao(servletContext);
    }

    private void setNewsDao(ServletContext servletContext) {
        newsDao = (NewsDao) servletContext.getAttribute(ServletContextEntityNames.NEWS_DAO);
        if (newsDao == null) {
            throw new IllegalArgumentException("News DAO not initialized");
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

        String source = request.getParameter(SOURCE);
        HttpSession session = request.getSession();

        int id = Integer.parseInt(request.getParameter(ID));
        String picPath;

        Part filePart = request.getPart(SessionEntities.PHOTO);

        if (source.equals(SessionEntities.USER)) {
            User user = userDao.getUserById(id);

            if (filePart.getSize() != 0) {
                if (filePart.getContentType().contains(RequestConstants.CONTENT_TYPE_JPEG)) {
                    picPath = Path.NEWS_PICTURES + File.separator + Util.createNewPath();
                    filePart.write(picPath);
                    user.setPhoto(picPath);
                } else {
                    session.setAttribute(MessagesConstants.LOGIN_ERROR_MSG, INCORRECT_FILE_TYPE);
                    response.sendRedirect(request.getHeader(RequestConstants.REFERER));
                    LOG.error(INCORRECT_FILE_TYPE);
                    return;
                }
            }

            try {
                userDao.updateUser(user);
            } catch (DBLayerException e) {
                LOG.debug(e);
            }

        } else if (source.equals(SessionEntities.NEWS)) {
            News news = newsDao.getNewsById(id);

            if (filePart.getSize() != 0) {
                if (filePart.getContentType().contains(RequestConstants.CONTENT_TYPE_JPEG)) {
                    picPath = Path.NEWS_PICTURES + File.separator + Util.createNewPath();
                    filePart.write(picPath);
                    news.setPicture(picPath);
                } else {
                    session.setAttribute(MessagesConstants.LOGIN_ERROR_MSG, INCORRECT_FILE_TYPE);
                    response.sendRedirect(request.getHeader(RequestConstants.REFERER));
                    LOG.error(INCORRECT_FILE_TYPE);
                    return;
                }
            }

            try {
                newsDao.updateNews(news);
            } catch (DBLayerException e) {
                LOG.debug(e);
            }
        }

        response.sendRedirect(request.getHeader(RequestConstants.REFERER));
    }
}
