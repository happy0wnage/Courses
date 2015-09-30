package ua.nure.petrov.Course.web.servlet.util;

import ua.nure.petrov.Course.db.dao.NewsDao;
import ua.nure.petrov.Course.db.dao.UserDao;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;
import ua.nure.petrov.Course.db.entity.constants.SessionEntities;
import ua.nure.petrov.Course.db.entity.db.News;
import ua.nure.petrov.Course.db.entity.db.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by Владислав on 02.08.2015.
 */

@WebServlet(name = "imageGeneratorServlet", urlPatterns = "/imageGeneratorServlet")
public class ImageGeneratorServlet extends HttpServlet {

    private static final String SOURCE = "source";
    private static final String ID = "id";
    private static final String RESPONSE_TYPE = "image/jpeg";
    private NewsDao newsDao;
    private UserDao userDao;


    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setNewsDao(servletContext);
        setUserDao(servletContext);
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
            throw new IllegalArgumentException("user DAO not initialized");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType(RESPONSE_TYPE);

        String source = request.getParameter(SOURCE);
        int id = Integer.parseInt(request.getParameter(ID));

        File image;
        FileInputStream stream = null;

        if (source.equals(SessionEntities.USER)) {
            User user = userDao.getUserById(id);
            image = new File(user.getPhoto());
            stream = new FileInputStream(image);
            writeStream(stream, response.getOutputStream());
        } else if (source.equals(SessionEntities.NEWS)) {
            News news = newsDao.getNewsById(id);
            image = new File(news.getPicture());
            stream = new FileInputStream(image);
            writeStream(stream, response.getOutputStream());
        }

    }

    private void writeStream(InputStream inputStream,
                                   OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) > -1) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }
}