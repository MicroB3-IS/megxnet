package net.megx.security.auth.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.SecurityException;
import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.User;
import net.megx.security.auth.services.UserService;
import net.megx.security.auth.web.ExternalLoginHandler;

public class ExternalLoginHandlerImpl extends BaseAuthenticationHandler implements
		ExternalLoginHandler {

	
	private UserService userService;
	
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ExternalLoginHandlerImpl(SecurityContext securityContext) {
		super(securityContext);
	}
	
	@Override
	public Authentication createAuthentication(HttpServletRequest request)
			throws SecurityException, ServletException, IOException {
		String provider = (String) request.getAttribute("provider");
		String logname = (String) request.getAttribute("logname");
		String firstName = (String)request.getAttribute("firstName");
		String lastName = (String)request.getAttribute("lastName");
		String email = (String)request.getAttribute("email");
		Authentication authentication = null;
		if(provider == null)
			throw new ServletException("External provider was not specified!");
		if(logname == null){
			if(email == null){
				log.error("The user login name (username) nor the email was retrieved from the external provider. Refusing to create authentication!");
				return null; // refusing to create authentication...
			}
			logname = email;
		}else{
			logname = getLogname(logname, provider);
		}
		User user = null;
		try {
			log.debug("Looking for user: " + logname);
			user = userService.getUserByUserId(logname);
			if(user == null){
				log.debug("This user was not registered. Registering now...");
				user = getNewUser(logname, email, firstName, lastName, provider);
				userService.addUser(user);
				log.debug("Successfully added: " + user);
			}
		} catch (Exception e) {
			log.error("Failed to create authentication: ",e);
			throw new ServletException(e);
		}
		authentication = new AuthenticationImpl(user); 
		log.debug("Successfully created Authentication: " + authentication);
		return authentication;
	}
	
	private User getNewUser(String logname, String email, String firstName, String lastName, String provider){
		User user = new User();
		user.setLogin(logname);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		if(email == null)
			email = "na@na.na"; // FIXME: this is a bit odd, but not all open id providers return an email...
		user.setEmail(email);
		
		user.setExternal(true);
		user.setProvider(provider);
		user.setPassword("");
		user.setJoinedOn(new Date());
		Role role = new Role();
		role.setLabel("user");
		
		List<Role> roles = new ArrayList<Role>(1);
		roles.add(role);
		user.setRoles(roles);
		
		return user;
	}
	
	protected String getLogname(String userId, String provider){
		return String.format("%s.%s", provider, userId);
	}
	
}
