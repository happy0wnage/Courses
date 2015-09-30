
package ua.nure.petrov.Course.web.filter;

import org.xml.sax.SAXException;
import ua.nure.petrov.Course.db.entity.constants.Path;
import ua.nure.petrov.Course.db.entity.constants.SessionEntities;
import ua.nure.petrov.Course.db.entity.db.Role;
import ua.nure.petrov.Course.db.entity.exception.ApplicationInitializationException;
import ua.nure.petrov.Course.db.parser.security.SecurityDOMParser;
import ua.nure.petrov.Course.db.parser.security.URI;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Set;

/*
* Created by Владислав on 01.08.2015.
*/


@WebFilter("/*")
public class SecurityFilter extends BaseHttpFilter {

    private static final String COMMON = "common";
    private static final String RESOURCES = "resources";
    private static final String ACCESS_DENIED_MSG = "This uri is not allowed for you.";
    private SecurityDOMParser domParser;

    private Set<URI> adminURIs;
    private Set<URI> studentURIs;
    private Set<URI> lecturerURIs;
    private Set<URI> commonURIs;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        domParser = SecurityDOMParser.getInstance();
        try {
            adminURIs = domParser.parseRole(Role.ADMIN);
            studentURIs = domParser.parseRole(Role.STUDENT);
            lecturerURIs = domParser.parseRole(Role.LECTURER);
            commonURIs = domParser.parseRole(COMMON);
        } catch (ParserConfigurationException | SAXException e) {
            throw new ApplicationInitializationException("Parsing roles not done", e);
        }
        catch (IOException e) {
            throw new ApplicationInitializationException("Security file not found.", e);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        if (accessGranted(httpServletRequest)) {
            filterChain.doFilter(httpServletRequest, servletResponse);
        } else {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.sendRedirect(Path.HOME_SERVLET);
        }
    }

    private boolean accessGranted(HttpServletRequest request) {
        HttpSession userSession = request.getSession();
        String requestURI = request.getServletPath();
        if (isResources(requestURI)) {
            return true;
        }
        Role usersCurrentRole = (Role) userSession.getAttribute(SessionEntities.ROLE);
        URI requestedURI = new URI(requestURI);
        if (usersCurrentRole == null) {
            return commonURIs.contains(requestedURI);
        }
        return isAccessAllowedToRole(usersCurrentRole.getName(), requestedURI);
    }

    private boolean isResources(String URI) {
        return URI.contains(RESOURCES);
    }

    private boolean isAccessAllowedToRole(String roleName, URI requestedURI) {
        switch (roleName) {
            case Role.ADMIN:
                return adminURIs.contains(requestedURI);
            case Role.LECTURER:
                return lecturerURIs.contains(requestedURI);
            case Role.STUDENT:
                return studentURIs.contains(requestedURI);
            default:
                return false;
        }
    }
}
