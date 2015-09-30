package ua.nure.petrov.Course.web.servlet.add;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.MarksDao;
import ua.nure.petrov.Course.db.entity.constants.MessagesConstants;
import ua.nure.petrov.Course.db.entity.constants.RequestConstants;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;
import ua.nure.petrov.Course.db.entity.constants.Util;
import ua.nure.petrov.Course.db.entity.db.Journal;
import ua.nure.petrov.Course.db.entity.db.Marks;
import ua.nure.petrov.Course.db.entity.db.User;
import ua.nure.petrov.Course.db.entity.validator.EntityValidator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by Владислав on 09.08.2015.
 */

@WebServlet(name = "addMark", urlPatterns = "/addMark")
public class AddMarkServlet extends HttpServlet {

    private static final String ID_USER = "idUser";
    private static final String ID_JOURNAL = "idJournal";
    private static final String MARK = "mark";
    private static final String DAY = "inputDate";
    private static final String FAILED_TO_ADD_MARK = "server.mark.add";
    private Logger LOG = Logger.getLogger(AddMarkServlet.class);

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

        HttpSession session = request.getSession();

        int idUser;
        int idJournal;
        int mark;
        String day;

        try {
            idUser = Integer.parseInt(request.getParameter(ID_USER));
            idJournal = Integer.parseInt(request.getParameter(ID_JOURNAL));
            mark = Integer.parseInt(request.getParameter(MARK));
            day = request.getParameter(DAY);
            day = Util.changeFormatDate(day);

            if (!Util.isDateValid(day)) {
                session.setAttribute(MessagesConstants.ERROR_MSG, FAILED_TO_ADD_MARK);
                LOG.debug(FAILED_TO_ADD_MARK);
                response.sendRedirect(request.getHeader(RequestConstants.REFERER));
                return;
            }

        } catch (IllegalArgumentException e) {
            session.setAttribute(MessagesConstants.ERROR_MSG, FAILED_TO_ADD_MARK);
            LOG.debug(FAILED_TO_ADD_MARK);
            response.sendRedirect(request.getHeader(RequestConstants.REFERER));
            return;
        }

        Marks marks = new Marks();
        marks.setUser(new User(idUser));
        marks.setJournal(new Journal(idJournal));
        marks.setMark(mark);
        marks.setDay(Date.valueOf(day));

        if (EntityValidator.validateMarks(marks)) {
            marksDao.insertMark(marks);
        }

        response.sendRedirect(request.getHeader(RequestConstants.REFERER));

    }
}