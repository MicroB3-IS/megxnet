package net.megx.security.auth.services.db;

import java.util.List;

import net.megx.megdb.BaseMegdbService;
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
	public List<WebResource> getAll(int from, int count) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
