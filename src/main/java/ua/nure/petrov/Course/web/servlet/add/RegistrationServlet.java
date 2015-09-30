package ua.nure.petrov.Course.web.servlet.add;

import org.apache.log4j.Logger;
import ua.nure.petrov.Course.db.dao.RoleDao;
import ua.nure.petrov.Course.db.dao.RoleUserDao;
import ua.nure.petrov.Course.db.dao.UserDao;
import ua.nure.petrov.Course.db.entity.constants.*;
import ua.nure.petrov.Course.db.entity.db.PasswordHash;
import ua.nure.petrov.Course.db.entity.db.Role;
import ua.nure.petrov.Course.db.entity.db.RoleUser;
import ua.nure.petrov.Course.db.entity.db.User;
import ua.nure.petrov.Course.db.entity.exception.DBLayerException;
import ua.nure.petrov.Course.db.entity.exception.DataRepeatException;
import ua.nure.petrov.Course.db.entity.validator.EntityValidator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 01.08.2015.
 */
@WebServlet(name = "register", urlPatterns = "/register")
@MultipartConfig(maxFileSize = 16177215)
public class RegistrationServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(RegistrationServlet.class);

    private static final String FIRST_NAME = "firstName";
    private static final String SECOND_NAME = "secondName";
    private static final String MIDDLE_NAME = "middleName";
    private static final String USER_LOGIN = "login";
    private static final String USER_EMAIL = "email";
    private static final String USER_PHONE = "phone";
    private static final String USER_PASSWORD = "password";
    private static final String USER_CARD = "card";
    private static final String ADMIN = "admin";
    private static final String LECTURER = "lecturer";
    private static final String INCORRECT_USER = "registration.incorrect_user";
    private static final String INCORRECT_FILE_UPLOADED_KEY = "server.registration.incorrect_file";
    private static final String USER_IS_INVALID_KEY = "server.registration.user_invalid";


    private UserDao userDao;
    private RoleUserDao roleUserDao;
    private RoleDao roleDao;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        setUserDao(servletContext);
        setRoleUserDao(servletContext);
        setRoleDao(servletContext);
    }

    private void setUserDao(ServletContext servletContext) {
        userDao = (UserDao) servletContext.getAttribute(ServletContextEntityNames.USER_DAO);
        if (userDao == null) {
            throw new IllegalArgumentException("User DAO not initialized");
        }
    }

    private void setRoleUserDao(ServletContext servletContext) {
        roleUserDao = (RoleUserDao) servletContext.getAttribute(ServletContextEntityNames.ROLE_USER_DAO);
        if (roleUserDao == null) {
            throw new IllegalArgumentException("RoleUser DAO not initialized");
        }
    }

    private void setRoleDao(ServletContext servletContext) {
        roleDao = (RoleDao) servletContext.getAttribute(ServletContextEntityNames.ROLE_DAO);
        if (roleDao == null) {
            throw new IllegalArgumentException("RoleUser DAO not initialized");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();


        User user = fillUserFromRequest(request);

        Part filePart = request.getPart(SessionEntities.PHOTO);
        String picPath;

        if (filePart.getSize() != 0) {
            if (filePart.getContentType().contains(RequestConstants.CONTENT_TYPE_JPEG)) {
                picPath = Path.USER_PHOTOS + File.separator + Util.createNewPath();
                filePart.write(picPath);
                user.setPhoto(picPath);
            } else {
                session.setAttribute(MessagesConstants.ERROR_MSG, INCORRECT_FILE_UPLOADED_KEY);
                response.sendRedirect(request.getHeader(RequestConstants.REFERER));
                LOG.error(INCORRECT_FILE_UPLOADED_KEY);
                return;
            }
        }

        Role role;
        List<Role> roles = new ArrayList<>();
        Role sessionRole = null;

        try {
            sessionRole = (Role) session.getAttribute(SessionEntities.ROLE);
            String roleNameAdmin = request.getParameter(ADMIN);
            String roleNameLecturer = request.getParameter(LECTURER);

            if (sessionRole.getName().equals(Role.ADMIN)) {
                if (roleNameAdmin != null && roleNameLecturer != null) {
                    roles.add(roleDao.getRoleByName(roleNameLecturer));
                    roles.add(roleDao.getRoleByName(roleNameAdmin));
                } else {

                    if (roleNameAdmin != null) {
                        roles.add(roleDao.getRoleByName(roleNameAdmin));
                    } else if (roleNameLecturer != null) {
                        roles.add(roleDao.getRoleByName(roleNameLecturer));
                    } else {
                        roles.add(roleDao.getRoleById(roleDao.getRoleByName(Role.LECTURER).getId()));
                    }
                }
            }
        } catch (NullPointerException e) {
            roles.add(roleDao.getRoleByName(Role.STUDENT));
        }

        List<RoleUser> roleUsers = new ArrayList<>();
        for (Role userRole : roles) {
            roleUsers.add(new RoleUser(userRole, user));
        }

        role = roleUsers.get(0).getRole();

        if (!EntityValidator.validateUser(user)) {
            session.setAttribute(MessagesConstants.ERROR_MSG, USER_IS_INVALID_KEY);
            session.setAttribute(INCORRECT_USER, user);
            response.sendRedirect(Path.REGISTER_SERVLET);
            LOG.error(INCORRECT_USER);
            return;
        }

        try {
            userDao.checkForMatches(user);
        } catch (DataRepeatException e) {
            session.setAttribute(INCORRECT_USER, user);
            session.setAttribute(MessagesConstants.ERROR_MSG, e.getLocalizedMessage());
            response.sendRedirect(Path.REGISTER_SERVLET);
            LOG.error(e.getMessage());
            return;
        }

        try {
            userDao.insertRoleUser(user, roles);
        } catch (DBLayerException e) {
            session.setAttribute(INCORRECT_USER, user);
            response.sendRedirect(Path.REGISTER_SERVLET);
            session.setAttribute(MessagesConstants.ERROR_MSG, e.getLocalizedMessage());
            LOG.error(e.getMessage());
            return;
        }

        user = userDao.getUserByLogin(user.getLogin());

        if (sessionRole == null) {
            session.setAttribute(SessionEntities.USER, user);
            session.setAttribute(SessionEntities.USER_ROLE, roleUsers);
            session.setAttribute(SessionEntities.ROLE, role);
        }

        response.sendRedirect(Path.HOME_SERVLET);
    }

    private User fillUserFromRequest(HttpServletRequest request) throws IOException, ServletException {
        User user = new User();

        user.setSalt(PasswordHash.generateSalt());
        user.setPassword(PasswordHash.sha256(user.getSalt() + request.getParameter(USER_PASSWORD)));
        user.setFirstName(request.getParameter(FIRST_NAME));
        user.setSecondName(request.getParameter(SECOND_NAME));
        String middleName = request.getParameter(MIDDLE_NAME);
        if (!middleName.isEmpty()) {
            user.setMiddleName(request.getParameter(MIDDLE_NAME));
        }
        user.setLogin(request.getParameter(USER_LOGIN));
        user.setEmail(request.getParameter(USER_EMAIL));
        user.setPhone(request.getParameter(USER_PHONE));
        user.setStudentCardNumber(request.getParameter(USER_CARD));
        user.setStatusBlocked(false);
        return user;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(Path.REGISTRATION_PAGE).forward(request, response);
    }

}
