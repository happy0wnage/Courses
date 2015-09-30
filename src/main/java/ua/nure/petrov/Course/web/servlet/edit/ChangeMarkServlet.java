package ua.nure.petrov.Course.web.servlet.edit;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.MarksDao;
import ua.nure.petrov.Course.db.entity.constants.RequestConstants;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;
import ua.nure.petrov.Course.db.entity.db.Marks;
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

@WebServlet(name = "changeMark", urlPatterns = "/changeMark")
public class ChangeMarkServlet extends HttpServlet {

    private static final String ID_MARK = "idMark";
    private static final String MARK = "mark";
    private static final String JSON = "application/json";

    private Logger LOG = Logger.getLogger(ChangeMarkServlet.class);

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

//        JSONObject responseObject = new JSONObject();

        try {
            int idMark = Integer.parseInt(request.getParameter(ID_MARK));
            int mark = Integer.parseInt(request.getParameter(MARK));

            Marks marks = marksDao.getMarkById(idMark);
            marks.setMark(mark);

            try {
                marksDao.updateMark(marks);
            } catch (DBLayerException e) {
//                responseObject.put(MessagesConstants.ERROR_MSG, e.getMessage()); TODO JSON put
                LOG.debug(e.getLocalizedMessage());
            }

        } catch (IllegalArgumentException e) {
//            responseObject.put(MessagesConstants.ERROR_MSG, e.getLocalizedMessage()); TODO JSON put
            LOG.debug(e.getLocalizedMessage());

        }

//        response.setContentType(JSON);
//        PrintWriter outWriter = response.getWriter();
//        outWriter.print(responseObject);

        response.sendRedirect(request.getHeader(RequestConstants.REFERER));

    }
}