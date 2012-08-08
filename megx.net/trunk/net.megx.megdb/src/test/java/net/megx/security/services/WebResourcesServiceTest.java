package net.megx.security.services;

import java.util.ArrayList;
import java.util.List;

import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.WebResource;
import net.megx.security.auth.services.WebResourcesService;
import net.megx.security.auth.services.db.DBWebResourcesService;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WebResourcesServiceTest extends DBServiceTest{
	
	private WebResourcesService webResourcesService;
	
	public static String URL_PATTERN = "oauth/authorize/TEST*";
	public static String HTTP_METHOD = "GET";
	public static List<Role> ROLES = new ArrayList<Role>(1);
	
	public static String SECOND_URL_PATTERN = "/admin/TEST*";
	public static String SECOND_HTTP_METHOD = "ALL";
	public static List<Role> SECOND_ROLES = new ArrayList<Role>(1);
	
	public static String THIRD_URL_PATTERN = "/ws/filter/TEST/*";
	public static String THIRD_HTTP_METHOD = "PUT";
	public static List<Role> THIRD_ROLES = new ArrayList<Role>(1);
	
	public static Role ROLE_USER = new Role();
	public static Role ROLE_ADMIN = new Role();
	public static Role ROLE_CMS_ADMIN = new Role();
	
	static{
		ROLE_USER.setLabel("user");
		ROLE_ADMIN.setLabel("admin");
		ROLE_CMS_ADMIN.setLabel("cmsAdmin");
		
		ROLES.add(ROLE_USER);
		ROLES.add(ROLE_ADMIN);
		ROLES.add(ROLE_CMS_ADMIN);
		
		SECOND_ROLES.add(ROLE_USER);
		SECOND_ROLES.add(ROLE_CMS_ADMIN);
		
		THIRD_ROLES.add(ROLE_ADMIN);
		THIRD_ROLES.add(ROLE_CMS_ADMIN);
	}
	
	private WebResource defaultWebResource;
	
	private WebResource createWebResource(String httpMethod, String urlPattern, List<Role> roles){
		WebResource webResource = new WebResource();
		webResource.setHttpMethod(httpMethod);
		webResource.setUrlPattern(urlPattern);
		webResource.setRoles(roles);
		return webResource;
	}
	
	@Before
	public void setup() throws Exception{
		webResourcesService = buildService(DBWebResourcesService.class);
		defaultWebResource = createWebResource(HTTP_METHOD, URL_PATTERN, ROLES);
		webResourcesService.addWebResourceMapping(defaultWebResource);
	}
	
	@Test
	public void testMatch() throws Exception{
		List<WebResource> retrievedResources = webResourcesService.match(URL_PATTERN, HTTP_METHOD);
		Assert.assertTrue("Web resource is not found", retrievedResources.contains(defaultWebResource));
	}
	
	@Test
	public void testRemoveWebResource() throws Exception{
		webResourcesService.removeWebResource(defaultWebResource);
		List<WebResource> retrievedResources = webResourcesService.match(URL_PATTERN, HTTP_METHOD);
		Assert.assertTrue("Web resource is not removed", !(retrievedResources.contains(defaultWebResource)));
		webResourcesService.addWebResourceMapping(defaultWebResource);
	}
	
	@Test
	public void testRemoveWebResourceByPattern() throws Exception{
		webResourcesService.removeWebResourceByPattern(URL_PATTERN);
		List<WebResource> retrievedResources = webResourcesService.match(URL_PATTERN, HTTP_METHOD);
		Assert.assertTrue("Web resource is not removed", !(retrievedResources.contains(defaultWebResource)));
		webResourcesService.addWebResourceMapping(defaultWebResource);
	}
	
	@Test
	public void testAddWebResourceMapping() throws Exception{
		webResourcesService.removeWebResource(defaultWebResource);
		webResourcesService.addWebResourceMapping(defaultWebResource);
		List<WebResource> retrievedResources = webResourcesService.match(URL_PATTERN, HTTP_METHOD);
		Assert.assertTrue("Web resource is not added", retrievedResources.contains(defaultWebResource));
	}
	
	@Test
	public void testAddWebResourceMappings() throws Exception{
		WebResource secondWebResource = createWebResource(SECOND_HTTP_METHOD, SECOND_URL_PATTERN, SECOND_ROLES);
		WebResource thirdWebResource  = createWebResource(THIRD_HTTP_METHOD, THIRD_URL_PATTERN, THIRD_ROLES);
		List<WebResource> webResToBeAdded = new ArrayList<WebResource>(1);
		webResToBeAdded.add(defaultWebResource);
		webResToBeAdded.add(secondWebResource);
		webResToBeAdded.add(thirdWebResource);
		
		webResourcesService.addWebResourceMappings(webResToBeAdded);
		List<WebResource> retrievedResources = webResourcesService.getAll(1, 3);
		Assert.assertTrue("Resources are not added", retrievedResources.containsAll(webResToBeAdded));
		
		webResourcesService.removeWebResource(secondWebResource);
		webResourcesService.removeWebResource(thirdWebResource);
		
	}
	
	@After
	public void tearDown() throws Exception{
		webResourcesService.removeWebResource(defaultWebResource);
	}
}
