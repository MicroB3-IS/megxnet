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
	public static Role ROLE_USER = new Role();
	public static Role ROLE_ADMIN = new Role();
	public static Role ROLE_CMS_ADMIN = new Role();
	public static List<Role> ROLES = new ArrayList<Role>();
	
	static{
		ROLE_USER.setLabel("user");
		ROLE_ADMIN.setLabel("admin");
		ROLE_CMS_ADMIN.setLabel("cmsAdmin");
		ROLES.add(ROLE_USER);
		ROLES.add(ROLE_ADMIN);
		ROLES.add(ROLE_CMS_ADMIN);
	}
	
	private WebResource defaultWebResource;
	
	private WebResource createWebResource(){
		WebResource webResource = new WebResource();
		webResource.setHttpMethod(HTTP_METHOD);
		webResource.setUrlPattern(URL_PATTERN);
		webResource.setRoles(ROLES);
		return webResource;
	}
	
	@Before
	public void setup() throws Exception{
		webResourcesService = buildService(DBWebResourcesService.class);
		defaultWebResource = createWebResource();
		webResourcesService.addWebResourceMapping(defaultWebResource);
	}
	
	@Test
	public void testMatch() throws Exception{
		List<WebResource> retrievedResources = webResourcesService.match(URL_PATTERN, HTTP_METHOD);
		Assert.assertTrue("Web resource is not found", retrievedResources.contains(defaultWebResource));
	}
	
	@After
	public void tearDown() throws Exception{
		webResourcesService.removeWebResource(defaultWebResource);
	}
}
