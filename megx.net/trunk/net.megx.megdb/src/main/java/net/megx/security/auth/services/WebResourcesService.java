package net.megx.security.auth.services;

import java.util.List;

import net.megx.security.auth.model.WebResource;

public interface WebResourcesService {
	public List<WebResource> match(String uri, String method) throws Exception;
	//public List<WebResource> getWebResources(Role role) throws Exception;
	
	public void addWebResourceMapping(WebResource resource) throws Exception;
	public void addWebResourceMappings(List<WebResource> resources) throws Exception;
	public void updateWebResource(WebResource resource) throws Exception;
	public void updateWebResource(String urlPattern, List<String> methods, List<String> roles) throws Exception;
	public void removeWebResource(WebResource resource) throws Exception;
	public void removeWebResourceByPattern(String urlPattern) throws Exception;
	
	public List<WebResource> getAll(int from, int count) throws Exception;
	
}
