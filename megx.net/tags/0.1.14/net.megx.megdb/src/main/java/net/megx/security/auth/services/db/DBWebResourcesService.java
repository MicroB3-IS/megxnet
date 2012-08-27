package net.megx.security.auth.services.db;

import java.util.List;

import net.megx.megdb.BaseMegdbService;
import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.WebResource;
import net.megx.security.auth.services.WebResourcesService;

public class DBWebResourcesService extends BaseMegdbService implements WebResourcesService{

	@Override
	public List<WebResource> match(final String uri, final String method) throws Exception {
		return doInSession(new DBTask<WebResourcesMapper,List<WebResource>>() {
			@Override
			public List<WebResource> execute(WebResourcesMapper mapper)
					throws Exception {
				return mapper.matchWebResources(uri, method);
			}
		}, WebResourcesMapper.class);
	}

	@Override
	public void addWebResourceMapping(final WebResource resource) throws Exception {
		doInTransaction(new DBTask<WebResourcesMapper,Object>() {
			@Override
			public Object execute(WebResourcesMapper mapper) throws Exception {
				mapper.insertWebResource(resource);
				return null;
			}
		}, WebResourcesMapper.class);
	}

	@Override
	public void updateWebResource(final WebResource resource) throws Exception {
		doInTransaction(new DBTask<WebResourcesMapper,Object>() {
			@Override
			public Object execute(WebResourcesMapper mapper) throws Exception {
				mapper.deleteWebResource(resource);
				mapper.insertWebResource(resource);
				return null;
			}
		}, WebResourcesMapper.class);
	}

	@Override
	public void removeWebResource(final WebResource resource) throws Exception {
		doInTransaction(new DBTask<WebResourcesMapper,Object>() {
			@Override
			public Object execute(WebResourcesMapper mapper) throws Exception {
				mapper.deleteWebResource(resource);
				return null;
			}
		}, WebResourcesMapper.class);
	}

	@Override
	public List<WebResource> getAll(final int from, final int count) throws Exception {
		return doInSession(new DBTask<WebResourcesMapper, List<WebResource>>() {

			@Override
			public List<WebResource> execute(WebResourcesMapper mapper)
					throws Exception {
				return mapper.getAll(from, count);
			}
			
		}, WebResourcesMapper.class);
	}

	@Override
	public void removeWebResourceByPattern(final String urlPattern) throws Exception {
		doInTransaction(new DBTask<WebResourcesMapper, Object>() {

			@Override
			public Object execute(WebResourcesMapper mapper) throws Exception {
				mapper.deleteByUrlPattern(urlPattern);
				return null;
			}
			
		}, WebResourcesMapper.class);
	}

	@Override
	public void updateWebResource(final String originalUrlPattern, final String urlPattern, final List<String> methods,
			final List<String> roles) throws Exception {
		doInTransaction(new DBTask<WebResourcesMapper, Object>() {

			@Override
			public Object execute(WebResourcesMapper mapper) throws Exception {
				mapper.deleteByUrlPattern(originalUrlPattern);
				
				for(String method: methods){
					for(String role: roles){
						mapper.insertSingleWebResource(urlPattern, method, role);
					}
				}
				return null;
			}
			
		}, WebResourcesMapper.class);
	}

	@Override
	public void addWebResourceMappings(final List<WebResource> resources)
			throws Exception {
			doInTransaction(new DBTask<WebResourcesMapper, Object>() {
	
				@Override
				public Object execute(WebResourcesMapper mapper) throws Exception {
					for(WebResource resource: resources){
						
						for(Role role: resource.getRoles()){
							mapper.insertSingleWebResource(resource.getUrlPattern(), resource.getHttpMethod(), role.getLabel());
						}
						//mapper.insertWebResource(resource);
					}
					return null;
				}
				
			}, WebResourcesMapper.class);
	}

	@Override
	public List<WebResource> getWebResources(final String pattern) throws Exception {
		return doInSession(new BaseMegdbService.DBTask<WebResourcesMapper, List<WebResource>>() {

			@Override
			public List<WebResource> execute(WebResourcesMapper mapper)
					throws Exception {
				return mapper.getByPattern(pattern);
			}
			
		}, WebResourcesMapper.class);
	}

}
