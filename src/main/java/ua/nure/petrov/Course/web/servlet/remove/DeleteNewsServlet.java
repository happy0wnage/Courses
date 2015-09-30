package ua.nure.petrov.Course.web.servlet.remove;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.NewsDao;
import ua.nure.petrov.Course.db.entity.constants.RequestConstants;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;
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

@WebServlet(name = "deleteNews", urlPatterns = "/deleteNews")
public class DeleteNewsServlet extends HttpServlet {

    private Logger LOG = Logger.getLogger(DeleteNewsServlet.class);

    private NewsDao newsDao;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setNewsDao(servletContext);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void setNewsDao(ServletContext servletContext) {
        newsDao = (NewsDao) servletContext.getAttribute(ServletContextEntityNames.NEWS_DAO);
        if (newsDao == null) {
            throw new IllegalArgumentException("News DAO not initialized");
        }
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idNews = Integer.parseInt(request.getParameter("idNews"));
            newsDao.deleteNewsById(idNews);
        } catch (DBLayerException e) {
            LOG.debug(e.getMessage());
        }

        response.sendRedirect(request.getHeader(RequestConstants.REFERER));
    }
}