package net.megx.security.filter.ui;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.megx.security.auth.model.WebResource;
import net.megx.security.auth.services.WebResourcesService;

@Path("/filter/resources")
public class ResourcesManager {
	
	private static final int PAGE_COUNT = 20; // FIXME: make this dynamic
	
	private WebResourcesService resourcesService;
	
	private Log log = LogFactory.getLog(getClass());
	
	@GET
	List<WebResource> getAll(){
		try {
			return  resourcesService.getAll(0, PAGE_COUNT);
		} catch (Exception e) {
			log.error("Failed to retrieve web resource mappings: ", e);
		}
		return null;
	}
	
	
}
