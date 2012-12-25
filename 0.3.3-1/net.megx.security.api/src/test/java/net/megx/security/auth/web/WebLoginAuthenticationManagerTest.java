package net.megx.security.auth.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.impl.AuthenticationImpl;
import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.User;
import net.megx.security.auth.model.WebResource;
import net.megx.security.auth.web.impl.WebLoginAuthenticationManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WebLoginAuthenticationManagerTest {
	
	private WebLoginAuthenticationManager wlaManager = new WebLoginAuthenticationManager();
	private Authentication auth;
	private WebResource firstWebResource, secondWebResource, thirdWebResource;
	private List<WebResource> resources;
	
	private User user;
	
	public static String USERNAME = "testUserForTokenTest";
	public static String DESCRIPTION = "Description for the new user";
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
	
	public static String URL_PATTERN = "oauth/authorize/TEST*";
	public static String HTTP_METHOD = "GET";
	public static List<Role> ROLES = new ArrayList<Role>(1);
	
	public static String SECOND_URL_PATTERN = "/admin/TEST*";
	public static String SECOND_HTTP_METHOD = "ALL";
	public static List<Role> SECOND_ROLES = new ArrayList<Role>(1);
	
	public static String THIRD_URL_PATTERN = "/ws/filter/TEST/*";
	public static String THIRD_HTTP_METHOD = "PUT";
	public static List<Role> THIRD_ROLES = new ArrayList<Role>(1);
	
	static{
		ROLE_USER.setLabel("user");
		ROLE_ADMIN.setLabel("admin");
		ROLE_CMS_ADMIN.setLabel("cmsAdmin");
		
		ROLES.add(ROLE_USER);
		ROLES.add(ROLE_ADMIN);
		ROLES.add(ROLE_CMS_ADMIN);
		
		SECOND_ROLES.add(ROLE_CMS_ADMIN);
		
		THIRD_ROLES.add(ROLE_ADMIN);
		THIRD_ROLES.add(ROLE_CMS_ADMIN);
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
	
	private WebResource createWebResource(String httpMethod, String urlPattern, List<Role> roles){
		WebResource webResource = new WebResource();
		webResource.setHttpMethod(httpMethod);
		webResource.setUrlPattern(urlPattern);
		webResource.setRoles(roles);
		return webResource;
	}
	
	@Before
	public void setup() throws Exception{
		user = createUser();
		auth = new AuthenticationImpl(user);
		resources = new ArrayList<WebResource>();		
	}
	
	@Test
	public void testCheckAuthentication_Authenticate() throws Exception{
		try{
			firstWebResource = createWebResource(HTTP_METHOD, URL_PATTERN, ROLES);
			resources.add(firstWebResource);
			wlaManager.checkAuthentication(auth, resources);
			Assert.assertTrue("Successfull authentication",true);
		}
		catch(Exception e){
			Assert.assertTrue("Unsuccessfull authentication", false);
		}
	}
	
	@Test 
	public void testCheckAuthentication_DONT_Authenticate() throws Exception{
		try{
			secondWebResource = createWebResource(SECOND_HTTP_METHOD, SECOND_URL_PATTERN, SECOND_ROLES);
			thirdWebResource = createWebResource(THIRD_HTTP_METHOD, THIRD_URL_PATTERN, THIRD_ROLES);
			resources.add(secondWebResource);
			resources.add(thirdWebResource);
			wlaManager.checkAuthentication(auth, resources);
			Assert.assertTrue("User should not be authenticated",false);
		}
		catch(Exception e){
			Assert.assertTrue("Unsuccessfull authentication", true);
		}
	}
}
