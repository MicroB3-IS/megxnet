package net.megx.pubmap.rest;

import java.util.ArrayList;
import java.util.List;

import javax.management.ServiceNotFoundException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.megx.megdb.pubmap.PubMapService;
import net.megx.model.Article;
import net.megx.pubmap.rest.json.ArticleDTO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

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
		try {
			List<Article> articles = getDBService().getAllArticles();
			List<ArticleDTO> articleDTOs = new ArrayList<ArticleDTO>();
			for(Article a : articles) {
				articleDTOs.add(ArticleDTO.fromArticle(a));
			}
			return gson.toJson(articleDTOs);
		} catch (Exception e) {
			log.error(e);
			return errorJSON(e);
		}
	}

	private String errorJSON(Exception e) {
		JSONObject err = new JSONObject();
		try {
			err.put("status", "ERROR");
			err.put("message", e.getMessage());
			return err.toString();
		} catch (JSONException e1) {
			log.error(e);
		}
		return null;
	}
}
