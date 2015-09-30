package ua.nure.petrov.Course.web.servlet.edit;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.RoleDao;
import ua.nure.petrov.Course.db.entity.constants.RequestConstants;
import ua.nure.petrov.Course.db.entity.constants.ServletContextEntityNames;
import ua.nure.petrov.Course.db.entity.constants.SessionEntities;
import ua.nure.petrov.Course.db.entity.db.RoleUser;

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
 * Created by Владислав on 11.08.2015.
 */
@WebServlet(name = "changeRole", urlPatterns = "/changeRole")
public class ChangeRoleServlet extends HttpServlet {

    private static final String ID_ROLE = "idRole";
    private Logger LOG = Logger.getLogger(ChangeRoleServlet.class);
    private RoleDao roleDao;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setRoleDao(servletContext);
    }

    private void setRoleDao(ServletContext servletContext) {
        roleDao = (RoleDao) servletContext.getAttribute(ServletContextEntityNames.ROLE_DAO);
        if (roleDao == null) {
            throw new IllegalArgumentException("Role DAO not initialized");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            int idRole = Integer.parseInt(request.getParameter(ID_ROLE));
            HttpSession session = request.getSession();
            List<RoleUser> userRoles = (List<RoleUser>) session.getAttribute(SessionEntities.USER_ROLE);

            for (RoleUser userRole : userRoles) {
                if (userRole.getRole().getId() == idRole) {
                    session.setAttribute(SessionEntities.ROLE, roleDao.getRoleById(idRole));
                }
            }
        } catch (IllegalArgumentException e) {
            LOG.debug(e.getMessage());
        }

        response.sendRedirect(request.getHeader(RequestConstants.REFERER));
    }
}