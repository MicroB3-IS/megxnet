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
import javax.ws.rs.PathParam;

import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.WebResource;
import net.megx.security.auth.services.WebResourcesService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;

@Path("/filter/resources")
public class ResourcesManager extends BaseRestService{
	
	private static final int PAGE_COUNT = 20; // FIXME: make this dynamic
	
	private WebResourcesService resourcesService;
	
	private Log log = LogFactory.getLog(getClass());
	private Gson gson = new Gson();
	
	
	
	public ResourcesManager(WebResourcesService resourcesService) {
		super();
		this.resourcesService = resourcesService;
	}


	@GET
	public String getAll(){
		try{
			return  gson.toJson(resourcesService.getAll(0, PAGE_COUNT));
		}catch(Exception e){
			return toJSON(handleException(e));
		}
	}
	
	@GET
	@Path("{start}:{page}")
	public String getAllPaginated(@PathParam("start") int start, @PathParam("page") int page){
		try {
			return toJSON(resourcesService.getAll(start, page));
		} catch (Exception e) {
			return toJSON(handleException(e));
		}
	}
	
	
	@PUT
	public String updateResource(
			@FormParam("originalUrlPattern") String originalUrlPattern,
			@FormParam("urlPattern") String urlPattern,
			@FormParam("httpMethods") String httpMethods,
			@FormParam("roles") String roles
			){
		try{
			String [] methods = httpMethods.split(",");
			String [] rolesArr = roles.split(",");
			List<WebResource> existing = resourcesService.getWebResources(urlPattern);
			if(existing != null && existing.size() > 0){
				throw new Exception("Web resource with the same URL pattern already exist."); 
			}
			resourcesService.updateWebResource(originalUrlPattern, urlPattern, Arrays.asList(methods), Arrays.asList(rolesArr));
			return toJSON(new Result<String>("OK"));
		}catch(Exception e){
			return toJSON(handleException(e));
		}
	}
	
	@POST
	public String addResource(
			@FormParam("urlPattern") String urlPattern,
			@FormParam("httpMethods") String httpMethods,
			@FormParam("roles") String roles
			){
		try{
			List<Role> rolesList = new LinkedList<Role>();
			String [] rolesArr = roles.split(",");
			String [] methodsArr = httpMethods.split(",");
			
			List<WebResource> existing = resourcesService.getWebResources(urlPattern);
			if(existing != null && existing.size() > 0){
				throw new Exception("Web resource with the same URL pattern already exist."); 
			}
			
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
			return toJSON(new Result<String>("OK"));
		}catch (Exception e) {
			return toJSON(handleException(e));
		}
	}
	
	@DELETE
	public String removeResource(@FormParam("urlPattern") String urlPattern){
		try{
			resourcesService.removeWebResourceByPattern(urlPattern);
			return toJSON(new Result<String>("OK"));
		}catch (Exception e) {
			return toJSON(handleException(e));
		}
	}
}
