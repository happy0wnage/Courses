package ua.nure.petrov.Course.web.servlet.add;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.ThemeDao;
import ua.nure.petrov.Course.db.entity.constants.MessagesConstants;
import ua.nure.petrov.Course.db.entity.constants.RequestConstants;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;
import ua.nure.petrov.Course.db.entity.db.Theme;
import ua.nure.petrov.Course.db.entity.exception.DBLayerException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Владислав on 06.08.2015.
 */

@WebServlet(name = "addTheme", urlPatterns = "/addTheme")
public class AddThemeServlet extends HttpServlet {

    private static final String THEME_NAME = "themeName";
    private static final String THEME_DESCRIPTION = "themeDescription";
    private static final String ERROR_ADDING_THEME = "server.theme.add";
    private Logger LOG = Logger.getLogger(AddThemeServlet.class);

    private ThemeDao themeDao;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setThemeDao(servletContext);
    }

    private void setThemeDao(ServletContext servletContext) {
        themeDao = (ThemeDao) servletContext.getAttribute(ServletContextEntityNames.THEME_DAO);
        if (themeDao == null) {
            throw new IllegalArgumentException("Theme DAO not initialized");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter(THEME_NAME);
        String description = request.getParameter(THEME_DESCRIPTION);

        Theme theme = new Theme();
        theme.setName(name);
        theme.setDescription(description);

        try {
            themeDao.insertTheme(theme);
        } catch (DBLayerException e) {
            HttpSession session = request.getSession();
            session.setAttribute(MessagesConstants.ERROR_MSG, ERROR_ADDING_THEME);
            LOG.debug(ERROR_ADDING_THEME);
            response.sendRedirect(request.getHeader(RequestConstants.REFERER));
            return;
        }

        response.sendRedirect(request.getHeader(RequestConstants.REFERER));
    }
}