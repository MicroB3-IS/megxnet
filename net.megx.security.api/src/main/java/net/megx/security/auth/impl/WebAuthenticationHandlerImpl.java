package net.megx.security.auth.impl;

import java.net.URISyntaxException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.InvalidCredentialsException;
import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.SecurityException;
import net.megx.security.auth.WebLogoutException;
import net.megx.security.auth.model.User;
import net.megx.security.auth.services.UserService;
import net.megx.security.auth.web.WebContextUtils;
import net.megx.security.auth.web.WebLoginHandler;
import net.megx.security.auth.web.WebUtils;

public class WebAuthenticationHandlerImpl extends BaseAuthenticationHandler
        implements WebLoginHandler {

    private String usernameField = "j_username";
    private String passwordField = "j_password";
    private String loginEndpointUrl = "/j_security_check";
    private String logoutEndpointUrl = "/admin/logout\\.do";

    private UserService userService;

    public WebAuthenticationHandlerImpl(SecurityContext securityContext) {
        super(securityContext);
    }

    @Override
    public Authentication createAuthentication(HttpServletRequest request)
            throws SecurityException, ServletException {

        SecurityContext context = WebContextUtils.getSecurityContext(request);

        if (context == null) {
            context = WebContextUtils.newSecurityContext(request);
            WebContextUtils.replaceSecurityContext(context, request, true);
        }

        if (log.isDebugEnabled()) {
            log.debug(String.format("Got request from requestURL %s",
                    request.getParameter("redirectURL")));
        }

        Authentication authentication = context.getAuthentication();
        try {
            if (getRequestPath(request).equals(loginEndpointUrl)) { // try
                                                                    // default
                                                                    // login...
                if (authentication == null) {
                    String username = request.getParameter(usernameField);
                    String password = request.getParameter(passwordField);
                    log.debug("username=" + username);
                    log.debug("password=" + password);

                    if (username != null && password != null) {
                        log.debug("username=" + username + "=");
                        try {
                            User user = userService.getUser(username.trim(),
                                    password.trim());

                            if (user == null) {
                                throw new InvalidCredentialsException(
                                        "Invalid username or password.");
                            }
                            if (user.isExternal()) {
                                throw new InvalidCredentialsException(
                                        "Login with external account.");
                            }
                            Date lastLogin = user.getLastlogin();
                            // TODO: check for more robust and secure solution
                            // e.g. by not storing password in User objetc at
                            // all
                            user.setPassword(null);
                            user.setLastlogin(new Date());
                            if (lastLogin == null) {
                                lastLogin = user.getLastlogin();
                            }
                            userService.updateUser(user);
                            authentication = new AuthenticationImpl(user);
                            request.getSession().setAttribute("userLastLogin",
                                    lastLogin);
                            // context.storeLastRequestedURL(getSiteHomeUrl(request));
                            checkRedirectUrl(request, context);
                        } catch (SecurityException e) {
                            throw e;
                        } catch (Exception e) {
                            throw new ServletException(e);
                        }
                    }
                } else {
                    WebContextUtils.newSecurityContext(request)
                            .storeLastRequestedURL(getSiteHomeUrl(request));
                }
            }
        } catch (URISyntaxException e) {
            throw new ServletException(e);
        }
        try {
            if (getRequestPath(request).matches(logoutEndpointUrl)) {
                WebContextUtils.clearSecurityContext(request);
                request.getSession().invalidate();
                // WebContextUtils.newSecurityContext(request).storeLastRequestedURL(getSiteHomeUrl(request));
                // return null;
                throw new WebLogoutException();
            }
        } catch (URISyntaxException e) {
            throw new ServletException(e);
        }

        return authentication;
    }

    protected String getRequestPath(HttpServletRequest request)
            throws URISyntaxException {
        return WebUtils.getRequestPath(request, false);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    protected void checkRedirectUrl(HttpServletRequest request,
            SecurityContext context) {

        String savedRequestPath = context.getLastRequestedURL();

        if (log.isDebugEnabled()) {
            log.debug(String.format(
                    "I have stored %s and could use from requestURL %s",
                    savedRequestPath, request.getParameter("redirectURL")));
        }

        // in these casees just enable redirect to home page
        if (savedRequestPath == null
                || savedRequestPath.matches(loginEndpointUrl)
                || savedRequestPath.matches(logoutEndpointUrl)
                || savedRequestPath.compareTo("") == 0) {
            // context.storeLastRequestedURL(null);
            context.storeLastRequestedURL(getSiteHomeUrl(request));
            return;
        }
        // otherwise just need to keep it as is
    }

  

    protected String getSiteHomeUrl(HttpServletRequest request) {
        return "/"; // empty, directly under root
    }
}
