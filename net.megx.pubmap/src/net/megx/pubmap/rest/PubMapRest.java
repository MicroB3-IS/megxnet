package net.megx.pubmap.rest;

import java.util.List;

import javax.management.ServiceNotFoundException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import net.megx.megdb.pubmap.PubMapArticle;
import net.megx.megdb.pubmap.PubMapService;

import com.google.gson.Gson;

@Path("/pubmap")
public class PubMapRest {
	private static final Log log = LogFactory.getLog(PubMapRest.class);
	
	private BundleContext bundleContext;
	
	private Gson gson = new Gson();	

	public PubMapRest(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}
	
	private PubMapService getDBService() throws ServiceNotFoundException {
		ServiceReference ref = bundleContext.getServiceReference(PubMapService.class.getName());
		if(ref == null) {
			throw new ServiceNotFoundException(PubMapService.class.getName());
		}
		return (PubMapService) bundleContext.getService(ref);
	}


	@GET
	@Path("getAllArticles")
	@Produces("application/json")
	public String getAllArticles() throws ServiceNotFoundException {
		log.debug("Called pubmap/getAllArticles");
		// http://localhost:8080/megx.net-web/services/pubmap/getAllArticles
		List<PubMapArticle> articles = getDBService().getAllArticles();
		return gson.toJson(articles);
	}
}
