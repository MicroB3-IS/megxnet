package net.megx.security.auth.services;

import java.util.List;

import net.megx.security.auth.Role;
import net.megx.security.auth.WebResource;

public interface WebResourcesService {
	public List<WebResource> match(String uri, String method) throws Exception;
	public List<WebResource> getWebResources(Role role) throws Exception;
	
	public void addWebResourceMapping(WebResource resource) throws Exception;
	public void updateWebResource(WebResource resource) throws Exception;
	public void removeWebResource(WebResource resource) throws Exception;
}
