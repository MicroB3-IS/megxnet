package net.megx.security.filter.ui;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.WebResource;
import net.megx.security.auth.services.WebResourcesService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;

@Path("/filter/resources")
public class ResourcesManager {
	
	private static final int PAGE_COUNT = 20; // FIXME: make this dynamic
	
	private WebResourcesService resourcesService;
	
	private Log log = LogFactory.getLog(getClass());
	private Gson gson = new Gson();
	
	
	
	public ResourcesManager(WebResourcesService resourcesService) {
		super();
		this.resourcesService = resourcesService;
	}


	@GET
	public String getAll() throws Exception{
		return  gson.toJson(resourcesService.getAll(0, PAGE_COUNT));
	}
	
	@PUT
	public void updateResource(
			@FormParam("urlPattern") String urlPattern,
			@FormParam("httpMethods") String httpMethods,
			@FormParam("roles") String roles
			) throws Exception{
		String [] methods = httpMethods.split(",");
		String [] rolesArr = roles.split(",");
		resourcesService.updateWebResource(urlPattern, Arrays.asList(methods), Arrays.asList(rolesArr));
	}
	
	@POST
	public void addResource(
			@FormParam("urlPattern") String urlPattern,
			@FormParam("httpMethods") String httpMethods,
			@FormParam("roles") String roles
			) throws Exception{
		List<Role> rolesList = new LinkedList<Role>();
		String [] rolesArr = roles.split(",");
		String [] methodsArr = httpMethods.split(",");
		
		for(String roleStr: rolesArr){
			Role role = new Role();
			role.setLabel(roleStr);
			rolesList.add(role);
		}
		List<WebResource> resources = new LinkedList<WebResource>();
		
		for(String method: methodsArr){
			WebResource resource = new WebResource();
			resource.setHttpMethod(method);
			resource.setUrlPattern(urlPattern);
			resource.setRoles(rolesList);
			resources.add(resource);
		}
		resourcesService.addWebResourceMappings(resources);
	}
	
	@DELETE
	public void removeResource(@FormParam("urlPattern") String urlPattern) throws Exception{
		resourcesService.removeWebResourceByPattern(urlPattern);
	}
}
