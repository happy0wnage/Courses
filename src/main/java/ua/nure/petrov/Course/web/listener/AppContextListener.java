package ua.nure.petrov.Course.web.listener;

import ua.nure.petrov.Course.db.dao.*;
import ua.nure.petrov.Course.db.dao.impl.*;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Владислав on 30.07.2015.
 */
public class AppContextListener implements ServletContextListener {
    private static final String RUSSIAN = "ru";
    private static final String ENGLISH = "en";
    private CourseDao courseDao = new CourseDaoImpl();
    private CourseUserDao courseUserDao = new CourseUserDaoImpl();
    private JournalDao journalDao = new JournalDaoImpl();
    private MarksDao marksDao = new MarksDaoImpl();
    private NewsDao newsDao = new NewsDaoImpl();
    private PrivateMessageDao privateMessageDao = new PrivateMessageDaoImpl();
    private RoleDao roleDao = new RoleDaoImpl();
    private RoleUserDao roleUserDao = new RoleUserDaoImpl();
    private ThemeDao themeDao = new ThemeDaoImpl();
    private UserDao userDao = new UserDaoImpl();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        putDAOObject(servletContext);
        putSupportedLocales(servletContext);
    }

    private void putDAOObject(ServletContext servletContext) {
        servletContext.setAttribute(ServletContextEntityNames.COURSE_DAO, courseDao);
        servletContext.setAttribute(ServletContextEntityNames.COURSE_USER_DAO, courseUserDao);
        servletContext.setAttribute(ServletContextEntityNames.JOURNAL_DAO, journalDao);
        servletContext.setAttribute(ServletContextEntityNames.MARKS_DAO, marksDao);
        servletContext.setAttribute(ServletContextEntityNames.NEWS_DAO, newsDao);
        servletContext.setAttribute(ServletContextEntityNames.PRIVATE_MESSAGE_DAO, privateMessageDao);
        servletContext.setAttribute(ServletContextEntityNames.ROLE_DAO, roleDao);
        servletContext.setAttribute(ServletContextEntityNames.ROLE_USER_DAO, roleUserDao);
        servletContext.setAttribute(ServletContextEntityNames.THEME_DAO, themeDao);
        servletContext.setAttribute(ServletContextEntityNames.USER_DAO, userDao);

    }

    private void putSupportedLocales(ServletContext servletContext) {
        List<String> supportedLocales = Arrays.asList(RUSSIAN, ENGLISH);
        servletContext.setAttribute(ServletContextEntityNames.LOCALES, supportedLocales);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
