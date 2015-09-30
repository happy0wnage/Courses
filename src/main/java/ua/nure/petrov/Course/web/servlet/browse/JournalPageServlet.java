package ua.nure.petrov.Course.web.servlet.browse;

import ua.nure.petrov.Course.db.dao.*;
import ua.nure.petrov.Course.db.entity.constants.Path;
import ua.nure.petrov.Course.db.entity.constants.RequestConstants;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;
import ua.nure.petrov.Course.db.entity.constants.SessionEntities;
import ua.nure.petrov.Course.db.entity.db.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Владислав on 02.08.2015.
 */

@WebServlet(name = "journalPage", urlPatterns = "/journalPage")
public class JournalPageServlet extends HttpServlet {

    private MarksDao marksDao;
    private JournalDao journalDao;
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setMarksDao(servletContext);
        setJournalDao(servletContext);
        setUserDao(servletContext);
    }

    private void setJournalDao(ServletContext servletContext) {
        journalDao = (JournalDao) servletContext.getAttribute(ServletContextEntityNames.JOURNAL_DAO);
        if (journalDao == null) {
            throw new IllegalArgumentException("Journal DAO not initialized");
        }
    }

    private void setUserDao(ServletContext servletContext) {
        userDao = (UserDao) servletContext.getAttribute(ServletContextEntityNames.USER_DAO);
        if (userDao == null) {
            throw new IllegalArgumentException("User DAO not initialized");
        }
    }

    private void setMarksDao(ServletContext servletContext) {
        marksDao = (MarksDao) servletContext.getAttribute(ServletContextEntityNames.MARKS_DAO);
        if (marksDao == null) {
            throw new IllegalArgumentException("Marks DAO not initialized");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionEntities.USER);
        Role role = (Role) session.getAttribute(SessionEntities.ROLE);

        List<Journal> journals = getJournalForRole(role.getName(), user);
        List<Marks> marks;
        List<User> users;

        Map<Journal, Map<User, List<Marks>>> allMarks = new HashMap<>();
        Map<User, List<Marks>> courseMarks;

        for (Journal journal : journals) {
            courseMarks = new HashMap<>();
            users = userDao.getUserByIdCourse(journal.getCourse().getId());

            for (User u : users) {
                marks = marksDao.getMarksByIdUserAndJournal(u.getId(), journal.getId());
                courseMarks.put(u, marks);
            }

            allMarks.put(journal, courseMarks);
        }

        request.setAttribute(RequestConstants.COURSE_MARKS, allMarks);
        request.getRequestDispatcher(Path.JOURNAL_PAGE).forward(request, response);
    }

    private List<Journal> getJournalForRole(String role, User user) {
        switch (role) {
            case Role.LECTURER:
                return  journalDao.getJournalsByIdLecturer(user.getId());
            case Role.ADMIN:
                return journalDao.getAllJournals();
            default:
                return journalDao.getJournalsByIdUser(user.getId());
        }
    }
}