package net.megx.security.auth.impl;

import javax.servlet.http.HttpServletRequest;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.User;
import net.megx.security.auth.services.UserService;
import net.megx.security.auth.web.WebAuthenticationHandler;
import net.megx.security.auth.web.WebContextUtils;

public class WebAuthenticationHandlerImpl extends BaseAuthenticationHandler implements WebAuthenticationHandler{

	
	private String usernameField = "j_username";
	private String passwordField = "j_password";
	private String loginEndpointUrl = "j_security_check";
	
	private UserService userService;
	
	public WebAuthenticationHandlerImpl(SecurityContext securityContext) {
		super(securityContext);
	}

	@Override
	public Authentication createAuthentication(HttpServletRequest request) {
		SecurityContext context = getSecurityContext();
		if(context == null){
			context = new SecurityContextContainer();
			WebContextUtils.replaceSecurityContext(context, request, true);
		}
		Authentication authentication = context.getAuthentication();
		if(authentication == null){
			if(getRequestPath(request).equals(loginEndpointUrl)){ // try default login...
				String username = request.getParameter(usernameField);
				String password = request.getParameter(passwordField);
				if(username != null && password != null){
					try {
						User user = userService.getUser(username.trim(), password.trim());
						authentication = new AuthenticationImpl(user);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return authentication;
	}

	
	protected String getRequestPath(HttpServletRequest request){
		String requestURL = request.getRequestURL().toString();
		return "";
	}
	
	
}
