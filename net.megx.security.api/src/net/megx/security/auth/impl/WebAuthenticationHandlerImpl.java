package net.megx.security.auth.impl;

import javax.servlet.http.HttpServletRequest;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.User;
import net.megx.security.auth.services.UserService;
import net.megx.security.auth.web.WebContextUtils;
import net.megx.security.auth.web.WebLoginHandler;
import net.megx.security.auth.web.WebUtils;

public class WebAuthenticationHandlerImpl extends BaseAuthenticationHandler implements WebLoginHandler{

	
	private String usernameField = "j_username";
	private String passwordField = "j_password";
	private String loginEndpointUrl = "/j_security_check";
	
	private UserService userService;
	
	public WebAuthenticationHandlerImpl(SecurityContext securityContext) {
		super(securityContext);
	}

	@Override
	public Authentication createAuthentication(HttpServletRequest request) {
		SecurityContext context = WebContextUtils.getSecurityContext(request);
		if(context == null){
			context = WebContextUtils.newSecurityContext(request);
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
		return WebUtils.getRequestPath(request, false);
	}
	
	public void setUserService(UserService userService){
		this.userService = userService;
	}
}
