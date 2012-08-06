package net.megx.security.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.User;
import net.megx.security.auth.services.UserService;
import net.megx.security.auth.services.db.DBUserService;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserServiceTest extends DBServiceTest{

	private UserService userService;
	
	
	public static String USERNAME = "testUser";
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
	
	
	private User createNewUser(){
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
	
	private User defaultUser;
	
	
	@Before
	public void setup() throws Exception{
		userService = buildService(DBUserService.class);
		
		// add the default user
		defaultUser = createNewUser();
		userService.addUser(defaultUser);
		
		
	}
	
	
	@Test
	public void testAddUser() throws Exception{
		userService.removeUser(USERNAME);
		userService.addUser(defaultUser);
		User user = userService.getUserByUserId(USERNAME);
		Assert.assertNotNull("User was not successfully added.",user);
	}
	
	@Test
	public void testGetUserByUsername() throws Exception{
		User user = userService.getUserByUserId(USERNAME);
		Assert.assertEquals("Login does not match.", USERNAME, user.getLogin());
		Assert.assertEquals("Description does not match.", DESCRIPTION, user.getDescription());
		Assert.assertEquals("Email does not match.", EMAIL, user.getEmail());
		Assert.assertEquals("First name does not match.", FIRST_NAME, user.getFirstName());
		Assert.assertEquals("Initials does not match.", INITIALS, user.getInitials());
		Assert.assertEquals("Joined date does not match.", JOINED_ON, user.getJoinedOn());
		Assert.assertEquals("Last login date does not match.", LAST_LOGIN, user.getLastlogin());
		Assert.assertEquals("Last name does not match.", LAST_NAME, user.getLastName());
		
		Assert.assertEquals("Disabled does not match", DISABLED, user.isDisabled());
		
		Assert.assertNotNull("Roles must not be null.", user.getRoles());
		Assert.assertEquals("User must have exatcly one role: ", 1, user.getRoles().size());
		
		
	}
	
	@After
	public void tearDown() throws Exception{
		// remove the default user
		userService.removeUser(defaultUser.getLogin());
	}
}
