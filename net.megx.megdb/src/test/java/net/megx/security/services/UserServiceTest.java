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
	
	public static String NEW_USERNAME = "New testUser";
	public static String NEW_DESCRIPTION = "New Description for the new user";
	public static String NEW_FIRST_NAME = "New Test";
	public static String NEW_LAST_NAME = "New User";
	public static String NEW_INITIALS = "New TU";
	public static String NEW_EMAIL = "newTestUser@testsite.com";
	public static boolean NEW_DISABLED = true;
	public static boolean NEW_EXTERNAL = true;
	public static Date NEW_JOINED_ON = new Date();
	public static Date NEW_LAST_LOGIN = new Date();
	public static String NEW_PASSWORD = "newPassword";
	public static String NEW_PROVIDER = "newExternal-provider.com";
	public static Role NEW_ROLE_USER = new Role();
	public static Role NEW_ROLE_ADMIN = new Role();
	
	static{
		ROLE_USER.setLabel("user");
		ROLE_ADMIN.setLabel("admin");
		NEW_ROLE_USER.setLabel("newUser");
		NEW_ROLE_ADMIN.setLabel("newAdmin");
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
	
	private User updateExistingUser(User user){
		user.setDescription(NEW_DESCRIPTION);
		user.setDisabled(NEW_DISABLED);
		user.setEmail(NEW_EMAIL);
		user.setExternal(NEW_EXTERNAL);
		user.setFirstName(NEW_FIRST_NAME);
		user.setInitials(NEW_INITIALS);
		user.setLastName(NEW_LAST_NAME);
		user.setJoinedOn(NEW_JOINED_ON);
		user.setLastlogin(NEW_LAST_LOGIN);
		user.setLastName(NEW_LAST_NAME);
		//user.setLogin(NEW_USERNAME);
		user.setPassword(NEW_PASSWORD);
		user.setProvider(NEW_PROVIDER);
		//user.getRoles().get(0).setLabel(NEW_ROLE_USER.getLabel());
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
	
	@Test
	public void testGetUser() throws Exception{
		User user = userService.getUser(USERNAME, PASSWORD);
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
	
	@Test
	public void testGetAvailableRoles() throws Exception{
		User user = userService.getUserByUserId(USERNAME);
		Assert.assertNotNull("Roles must not be null.", user.getRoles());
		Assert.assertEquals("Roles do not match", ROLE_USER.getLabel(), user.getRoles().get(0).getLabel());
	}
	
	@Test
	public void testUpdateUser() throws Exception{
		User user = userService.getUserByUserId(USERNAME);
		userService.updateUser(updateExistingUser(user));
		User updatedUser = userService.getUserByUserId(USERNAME);
		
		Assert.assertEquals("Login does not match.", USERNAME, updatedUser.getLogin());
		Assert.assertEquals("Description does not match.", NEW_DESCRIPTION, updatedUser.getDescription());
		Assert.assertEquals("Email does not match.", NEW_EMAIL, updatedUser.getEmail());
		Assert.assertEquals("First name does not match.", NEW_FIRST_NAME, updatedUser.getFirstName());
		Assert.assertEquals("Initials does not match.", NEW_INITIALS, updatedUser.getInitials());
		Assert.assertEquals("Joined date does not match.", NEW_JOINED_ON, updatedUser.getJoinedOn());
		Assert.assertEquals("Last login date does not match.", NEW_LAST_LOGIN, updatedUser.getLastlogin());
		Assert.assertEquals("Last name does not match.", NEW_LAST_NAME, updatedUser.getLastName());
		
		Assert.assertEquals("Disabled does not match", NEW_DISABLED, updatedUser.isDisabled());
		
		Assert.assertNotNull("Roles must not be null.", updatedUser.getRoles());
		Assert.assertEquals("User must have exatcly one role: ", 1, updatedUser.getRoles().size());
	}
	
	@After
	public void tearDown() throws Exception{
		// remove the default user
		userService.removeUser(defaultUser.getLogin());
	}
}
