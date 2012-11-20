package net.megx.security.auth.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.User;
import net.megx.security.auth.services.UserService;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.*;

public class WebAuthenticationHandlerImplTest {
	
	private WebAuthenticationHandlerImpl webAH;
	private HttpServletRequest request;
	private HttpSession session;
	private SecurityContext securityContext = new SecurityContextContainer();
	private UserService userService;
	private User user;
	
	private static String SECURITY_CONTEXT_SESSION_ATTR = "net.megx.security.SECURITY_CONTEXT";
	private static String REQUEST_PATH = "/j_security_check";
	
	private String USERNAME = "j_username";
	private String PASSWORD = "j_password";
	public static String DESCRIPTION = "Description for the new user";
	public static String FIRST_NAME = "Test";
	public static String LAST_NAME = "User";
	public static String INITIALS = "TU";
	public static String EMAIL = "testUser@testsite.com";
	public static boolean DISABLED = false;
	public static boolean EXTERNAL = false;
	public static Date JOINED_ON = new Date();
	public static Date LAST_LOGIN = new Date();
	public static String PROVIDER = "external-provider.com";
	public static Role ROLE_USER = new Role();
	public static Role ROLE_ADMIN = new Role();
	public static Role ROLE_CMS_ADMIN = new Role();
	
	static{
		ROLE_USER.setLabel("user");
		ROLE_ADMIN.setLabel("admin");
		ROLE_CMS_ADMIN.setLabel("cmsAdmin");
	}
	
	private User createUser(){
		User user = new User();
		user.setDescription(DESCRIPTION);
		user.setDisabled(DISABLED);
		user.setEmail(EMAIL);
		user.setExternal(EXTERNAL);
		user.setFirstName(FIRST_NAME);
		user.setInitials(INITIALS);
		user.setLastName(LAST_NAME);
		user.setJoinedOn(JOINED_ON);
		user.setLastlogin(LAST_LOGIN);
		user.setLastName(LAST_NAME);
		user.setLogin(USERNAME);
		user.setPassword(PASSWORD);
		user.setProvider(PROVIDER);
		List<Role> roles = new ArrayList<Role>(1);
		roles.add(ROLE_USER);
		user.setRoles(roles);
		return user;
	}
	
	@Before
	public void setup() throws Exception{
		request = EasyMock.createMock(HttpServletRequest.class);
		session = EasyMock.createMock(HttpSession.class);
		userService = EasyMock.createMock(UserService.class);
		webAH = new WebAuthenticationHandlerImpl(securityContext);
		webAH.setUserService(userService);
		user = createUser();
	}
	
	@Test
	public void testCreateAuthentication() throws Exception{
		expect(request.getSession()).andReturn(session);
		//expect(request.getRequestURI().substring(request.getContextPath().length())).andReturn(REQUEST_PATH);
		expect(request.getRequestURI()).andReturn(REQUEST_PATH);
		expect(request.getRequestURI()).andReturn(REQUEST_PATH);
		expect(request.getContextPath()).andReturn("");
		expect(request.getContextPath()).andReturn("");
		expect(request.getParameter(USERNAME)).andReturn(USERNAME);
		expect(request.getParameter(PASSWORD)).andReturn(PASSWORD);
		expect(userService.getUser(USERNAME.trim(), PASSWORD.trim())).andReturn(user);
		replay(request);
		replay(userService);
		
		expect(session.getAttribute(SECURITY_CONTEXT_SESSION_ATTR)).andReturn(securityContext);
		expect(session.getId()).andReturn("sessionID");
		replay(session);
		
		Authentication retrievedAuthentication = webAH.createAuthentication(request);
		Assert.assertNotNull("Authentication is null", retrievedAuthentication);
	}
}
