package ua.nure.petrov.Course.web.servlet.edit;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.entity.constants.RequestConstants;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;
import ua.nure.petrov.Course.db.entity.constants.SessionEntities;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Владислав on 12.08.2015.
 */

@WebServlet(name = "changeLanguageServlet", urlPatterns = "/changeLang")
public class ChangeLangServlet extends HttpServlet {

    private static final String LANGUAGE = "lang";
    private Logger LOG = Logger.getLogger(ChangeLangServlet.class);

    private List<String> supportedLocales;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        supportedLocales = (List<String>) servletContext.getAttribute(ServletContextEntityNames.LOCALES);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lang = request.getParameter(LANGUAGE);
        if (lang != null) {
            if (supportedLocales.contains(lang)) {
                HttpSession userSession = request.getSession();
                userSession.setAttribute(SessionEntities.USER_LANG, lang);
            }
        }
        response.sendRedirect(request.getHeader(RequestConstants.REFERER));
    }
}