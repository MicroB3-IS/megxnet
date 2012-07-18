package net.megx.security.auth.services.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.megx.security.auth.model.WebResource;

public interface WebResourcesMapper {
	public List<WebResource> matchWebResources(
			@Param("url") String url, 
			@Param("method") String method) throws Exception;
	public void insertWebResource(WebResource resource) throws Exception;
	public void deleteWebResource(WebResource resource) throws Exception;
	public List<WebResource> getAll(
			@Param("from")int from, 
			@Param("count") int count) throws Exception;
	public void deleteByUrlPattern(String pattern) throws Exception;
	public void insertSingleWebResource(
			@Param("urlPattern") String urlPattern,
			@Param("httpMethod") String httpMethod,
			@Param("role") String role
			) throws Exception;
}
