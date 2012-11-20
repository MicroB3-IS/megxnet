package net.megx.security.auth.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.SecurityException;
import net.megx.security.auth.model.User;
import net.megx.security.auth.services.UserService;
import net.megx.security.auth.web.GeneralAuthenticationHandler;

public class GeneralAuthenticationHandlerImpl extends BaseAuthenticationHandler implements GeneralAuthenticationHandler{

	private UserService userService;
	
	
	public GeneralAuthenticationHandlerImpl(SecurityContext securityContext, UserService userService) {
		super(securityContext);
		this.userService = userService;
	}

	@Override
	public Authentication createAuthentication(HttpServletRequest request)
			throws SecurityException, ServletException, IOException {
		Object username = request.getAttribute(USERNAME_KEY);
		Object password = request.getAttribute(PASSWORD_KEY);
		if(username instanceof String && password instanceof String){
			try {
				User user = userService.getUser((String)username, (String) password);
				if(user != null)
					return new AuthenticationImpl(user);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		return null;
	}

}
