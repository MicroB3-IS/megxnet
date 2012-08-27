package net.megx.security.api.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.SecurityException;
import net.megx.security.auth.impl.ExternalLoginHandlerImpl;
import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.User;
import net.megx.security.auth.services.UserService;
import net.megx.security.auth.web.ExternalLoginHandler;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.*;

public class ExternalLoginHandlerTest {
	
	// mocks
	private HttpServletRequest request;
	private UserService userService;
	private HttpSession session;
	
	// real handler
	private ExternalLoginHandler handler;
	
	private static String EXISTING_USER = "existing";
	private static String NON_EXISTING_USER = "nonexisting";
	
	public static String DESCRIPTION = "Description fro the new user";
	public static String FIRST_NAME = "Test";
	public static String LAST_NAME = "User";
	public static String INITIALS = "TU";
	public static String EMAIL = "testUser@testsite.com";
	public static boolean DISABLED = false;
	public static boolean EXTERNAL = false;
	public static Date JOINED_ON = new Date();
	public static Date LAST_LOGIN = new Date();
	public static String PASSWORD = "password";
	public static String PROVIDER = "external-provider.com";
	public static Role ROLE_USER = new Role();
	public static Role ROLE_ADMIN = new Role();
	
	static{
		ROLE_USER.setLabel("user");
		ROLE_ADMIN.setLabel("admin");
	}
	
	
	@Before
	public void setup() throws Exception{
		request = EasyMock.createMock(HttpServletRequest.class);
		session = EasyMock.createMock(HttpSession.class);
		
		expect(request.getSession()).andReturn(session);
		
		handler = new ExternalLoginHandlerImpl(null);
		userService = EasyMock.createMock(UserService.class);
		
		expect(userService.getUserByUserId(EXISTING_USER + "." + PROVIDER)).andReturn(createUser());
		expect(userService.getUserByUserId(NON_EXISTING_USER + "." + PROVIDER)).andReturn(null);
		User nonExistingUser = createUser();
		nonExistingUser.setLogin(NON_EXISTING_USER + "." + PROVIDER);
		expect(userService.addUser(nonExistingUser)).andReturn(nonExistingUser);
		
		((ExternalLoginHandlerImpl)handler).setUserService(userService);
		
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
		user.setLogin(EXISTING_USER);
		user.setPassword(PASSWORD);
		user.setProvider(PROVIDER);
		List<Role> roles = new ArrayList<Role>(1);		
		roles.add(ROLE_USER);
		user.setRoles(roles);
		return user;
	}
	
	
	@Test
	public void testCreateAuthentication() throws SecurityException, ServletException, IOException{
		String provider = PROVIDER;
		String logname = EXISTING_USER;
		String firstName = FIRST_NAME;
		String lastName = LAST_NAME;
		String email = EMAIL;
		
		expect(request.getAttribute("provider")).andReturn(provider);
		expect(request.getAttribute("logname")).andReturn(logname);
		expect(request.getAttribute("firstName")).andReturn(firstName);
		expect(request.getAttribute("lastName")).andReturn(lastName);
		expect(request.getAttribute("email")).andReturn(email);
		
		
		replay(request);
		replay(userService);
		replay(session);
		
		Authentication authentication = handler.createAuthentication(request);
		Assert.assertNotNull("Authentication must not be null", authentication);
	}
	
	@Test
	public void testCreateAuthenticationNonExistingUser() throws SecurityException, ServletException, IOException{
		String provider = PROVIDER;
		String logname = NON_EXISTING_USER;
		String firstName = FIRST_NAME;
		String lastName = LAST_NAME;
		String email = EMAIL;
		
		expect(request.getAttribute("provider")).andReturn(provider);
		expect(request.getAttribute("logname")).andReturn(logname);
		expect(request.getAttribute("firstName")).andReturn(firstName);
		expect(request.getAttribute("lastName")).andReturn(lastName);
		expect(request.getAttribute("email")).andReturn(email);
		
		
		replay(request);
		replay(userService);
		replay(session);
		
		Authentication authentication = handler.createAuthentication(request);
		Assert.assertNotNull("Authentication must not be null", authentication);
	}
	
	public static void main(String[] args) {
		HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
		expect(request.getParameter("test")).andReturn("Value 1");
		replay(request);
		System.out.println(request.getParameter("test"));
	}
}
