package net.megx.security.auth.services.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.megx.security.auth.WebResource;

public interface WebResourcesMapper {
	public List<WebResource> matchWebResources(
			@Param("url") String url, 
			@Param("method") String method) throws Exception;
	public void insertWebResource(WebResource resource) throws Exception;
	public void deleteWebResource(WebResource resource) throws Exception;
}
