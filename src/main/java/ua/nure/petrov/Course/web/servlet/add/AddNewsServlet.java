package ua.nure.petrov.Course.web.servlet.add;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.NewsDao;
import ua.nure.petrov.Course.db.entity.constants.*;
import ua.nure.petrov.Course.db.entity.db.Course;
import ua.nure.petrov.Course.db.entity.db.News;
import ua.nure.petrov.Course.db.entity.db.User;
import ua.nure.petrov.Course.db.entity.validator.EntityValidator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Владислав on 02.08.2015.
 */

@WebServlet(name = "addNews", urlPatterns = "/addNews")
@MultipartConfig(maxFileSize = 16177215)
public class AddNewsServlet extends HttpServlet {

    private static final String TITLE = "title";
    private static final String BODY = "body";
    private static final String ID_COURSE = "idCourse";
    private static final String TITLE_ERROR = "main.news.title_error";
    private static final String BODY_ERROR = "main.news.body_error";
    private static final String INCORRECT_FILE_TYPE = "main.error_file_type";
    private static final String NOT_ALL_FIELDS = "server.course.add";

    private NewsDao newsDao;

    private Logger LOG = Logger.getLogger(AddNewsServlet.class);


    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setNewsDao(servletContext);
    }

    private void setNewsDao(ServletContext servletContext) {
        newsDao = (NewsDao) servletContext.getAttribute(ServletContextEntityNames.NEWS_DAO);
        if (newsDao == null) {
            throw new IllegalArgumentException("News DAO not initialized");
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
        News news = new News(1);

        int idCourse = 0;
        try {
            idCourse = Integer.parseInt(request.getParameter(ID_COURSE));
        } catch (NumberFormatException e) {
            LOG.debug(e.getMessage());
        }
        String title = request.getParameter(TITLE);
        String body = request.getParameter(BODY);

        if (!title.isEmpty()) {
            news.setTitle(title);
        } else {
            session.setAttribute(MessagesConstants.ERROR_MSG, TITLE_ERROR);
            response.sendRedirect(request.getHeader(RequestConstants.REFERER));
            LOG.error(MessagesConstants.LOG_ERROR);
            return;
        }

        if (!body.isEmpty()) {
            news.setBody(body);
        } else {
            session.setAttribute(MessagesConstants.ERROR_MSG, BODY_ERROR);
            response.sendRedirect(request.getHeader(RequestConstants.REFERER));
            LOG.error(MessagesConstants.LOG_ERROR);
            return;
        }

        Part filePart = request.getPart(SessionEntities.PHOTO);
        if (filePart.getSize() != 0) {
            if (filePart.getContentType().contains(RequestConstants.CONTENT_TYPE_JPEG)) {
                String picPath;
                picPath = Path.NEWS_PICTURES + File.separator + Util.createNewPath();
                filePart.write(picPath);
                news.setPicture(picPath);
            } else {
                session.setAttribute(MessagesConstants.ERROR_MSG, INCORRECT_FILE_TYPE);
                response.sendRedirect(request.getHeader(RequestConstants.REFERER));
                LOG.error(INCORRECT_FILE_TYPE);
                return;
            }
        }

        news.setUser(user);
        news.setCourse(new Course(idCourse));
        news.setTitle(title);
        news.setBody(body);
        news.setPostDate(Util.getDateTime());

        if (!EntityValidator.validateNews(news)) {
            session.setAttribute(MessagesConstants.ERROR_MSG, NOT_ALL_FIELDS);
            response.sendRedirect(request.getHeader(RequestConstants.REFERER));
            LOG.error(NOT_ALL_FIELDS);
            return;
        }

        newsDao.insertNews(news);
        response.sendRedirect(request.getHeader(RequestConstants.REFERER));
    }
}