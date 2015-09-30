package ua.nure.petrov.Course.web.servlet.remove;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.MarksDao;
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
 * Created by Владислав on 12.08.2015.
 */

@WebServlet(name = "deleteMark", urlPatterns = "/deleteMark")
public class DeleteMarkServlet extends HttpServlet {

    public static final String ID_MARK = "idMark";
    private Logger LOG = Logger.getLogger(DeleteMarkServlet.class);

    private MarksDao marksDao;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setMarksDao(servletContext);
    }

    private void setMarksDao(ServletContext servletContext) {
        marksDao = (MarksDao) servletContext.getAttribute(ServletContextEntityNames.MARKS_DAO);
        if (marksDao == null) {
            throw new IllegalArgumentException("Marks DAO not initialized");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idMark = Integer.parseInt(request.getParameter(ID_MARK));

            try {
                marksDao.deleteMarkById(idMark);
            } catch (DBLayerException e) {
                LOG.debug(e.getLocalizedMessage());
            }

        } catch (IllegalArgumentException e) {
            LOG.debug(e.getLocalizedMessage());
        }

        response.sendRedirect(request.getHeader(RequestConstants.REFERER));

    }
}