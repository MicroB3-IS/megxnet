package net.megx.security.services;

import java.security.Permission;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.User;
import net.megx.security.auth.model.UserVerification;
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
	public static Role ROLE_CMS_ADMIN = new Role();
	
	public static String NEW_USERNAME = "newTestUser";
	public static String NEW_DESCRIPTION = "New description for the existing user";
	public static String NEW_FIRST_NAME = "newTest";
	public static String NEW_LAST_NAME = "newUser";
	public static String NEW_INITIALS = "newTU";
	public static String NEW_EMAIL = "newTestUser@testsite.com";
	public static boolean NEW_DISABLED = false;
	public static boolean NEW_EXTERNAL = false;
	public static Date NEW_JOINED_ON = new Date();
	public static Date NEW_LAST_LOGIN = new Date();
	public static String NEW_PASSWORD = "newPassword";
	public static String NEW_PROVIDER = "newExternal-provider.com";
	
	static{
		ROLE_USER.setLabel("user");
		ROLE_ADMIN.setLabel("admin");
		ROLE_CMS_ADMIN.setLabel("cmsAdmin");
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
	public void testAddPendingUser() throws Exception{
		userService.removeUser(defaultUser.getLogin());
		userService.addPendingUser(defaultUser);
		User pendingUser = userService.getUserByUserId(USERNAME);
		Assert.assertEquals("Login does not match.", USERNAME, pendingUser.getLogin());
		Assert.assertEquals("Description does not match.", DESCRIPTION, pendingUser.getDescription());
		Assert.assertEquals("Email does not match.", EMAIL, pendingUser.getEmail());
		Assert.assertEquals("First name does not match.", FIRST_NAME, pendingUser.getFirstName());
		Assert.assertEquals("Initials does not match.", INITIALS, pendingUser.getInitials());
		Assert.assertEquals("Joined date does not match.", JOINED_ON, pendingUser.getJoinedOn());
		Assert.assertEquals("Last login date does not match.", LAST_LOGIN, pendingUser.getLastlogin());
		Assert.assertEquals("Last name does not match.", LAST_NAME, pendingUser.getLastName());
		Assert.assertEquals("Disabled does not match", true, pendingUser.isDisabled());
		//TODO: The pending user does not have roles after calling commitPending, needs to be fixed.
		//Assert.assertNotNull("Roles must not be null.", defaultUser.getRoles());
		//Assert.assertEquals("User must have exatcly one role: ", 1, defaultUser.getRoles().size());
	}
	
	@Test
	public void testCommitPending() throws Exception{
		userService.removeUser(defaultUser.getLogin());
		UserVerification verification = userService.addPendingUser(defaultUser);
		defaultUser.setDisabled(false);
		userService.commitPending(defaultUser, verification.getVerificationValue(), 10000);
		
		User committedUser = userService.getUserByUserId(USERNAME);
		Assert.assertEquals("Login does not match.", USERNAME, committedUser.getLogin());
		Assert.assertEquals("Description does not match.", DESCRIPTION, committedUser.getDescription());
		Assert.assertEquals("Email does not match.", EMAIL, committedUser.getEmail());
		Assert.assertEquals("First name does not match.", FIRST_NAME, committedUser.getFirstName());
		Assert.assertEquals("Initials does not match.", INITIALS, committedUser.getInitials());
		Assert.assertEquals("Joined date does not match.", JOINED_ON, committedUser.getJoinedOn());
		Assert.assertEquals("Last login date does not match.", LAST_LOGIN, committedUser.getLastlogin());
		Assert.assertEquals("Last name does not match.", LAST_NAME, committedUser.getLastName());
		Assert.assertEquals("Disabled does not match", false, committedUser.isDisabled());
		//TODO: The pending user does not have roles after calling commitPending, needs to be fixed.
		//Assert.assertNotNull("Roles must not be null.", committedUser.getRoles());
		//Assert.assertEquals("User must have exatcly one role: ", 1, committedUser.getRoles().size());
	}

	@Test
	public void testGetVerification() throws Exception{
		userService.removeUser(defaultUser.getLogin());
		UserVerification verification = userService.addPendingUser(defaultUser);
		Assert.assertNotNull("Verification string does not exist", userService.getVerification(verification.getVerificationValue(), 10000));
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
		List<Role> availableRoles = userService.getAvailableRoles(0,0,true).getResults();
		Assert.assertTrue("USER role is not available.", availableRoles.contains(ROLE_USER));
		Assert.assertTrue("ADMIN role is not available.", availableRoles.contains(ROLE_ADMIN));
		Assert.assertTrue("CMS_ADMIN role is not available.", availableRoles.contains(ROLE_CMS_ADMIN));
	}
	
	@Test
	public void testUpdateUser() throws Exception{
		User user = userService.getUserByUserId(USERNAME);
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
		userService.updateUser(user);
		
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
	
	@Test
	public void testRemoveUser() throws Exception{
		userService.removeUser(USERNAME);
		User user = userService.getUserByUserId(USERNAME);
		Assert.assertNull(user);
		
		//Add the user again so it can be succesfully removed in the teardown method
		userService.addUser(defaultUser);
	}
	
	@Test
	public void testCreateRole() throws Exception{
		Role role = new Role();
		role.setDescription(DESCRIPTION);
		role.setLabel("chonAdmin");
		userService.createRole(role);
		
		List<Role> availableRoles = userService.getAvailableRoles(0,0,true).getResults();
		Assert.assertTrue("Role is not present in the list of available roles", availableRoles.contains(role));
		
		userService.removeRole(role.getLabel());
	}
	
	@Test
	public void testRemoveRole() throws Exception{
		Role role = new Role();
		role.setDescription(DESCRIPTION);
		role.setLabel("chonAdmin");
		userService.createRole(role);
		
		List<Role> availableRoles = userService.getAvailableRoles(0,0,true).getResults();
		Assert.assertTrue("Role is not present in the list of available roles", availableRoles.contains(role));
		
		userService.removeRole(role.getLabel());
		Assert.assertTrue("Role is present in the list of available roles after removal", userService.getAvailableRoles(0,0,true).getResults().contains(role) == false);
	}
	
	@After
	public void tearDown() throws Exception{
		// remove the default user
		userService.removeUser(defaultUser.getLogin());
	}
}
