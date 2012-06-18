package net.megx.pubmap.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.ServiceNotFoundException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.megx.megdb.pubmap.PubMapService;
import net.megx.model.Article;
import net.megx.pubmap.rest.json.ArticleDTO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
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
	@Produces(MediaType.APPLICATION_JSON)
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
	
	
	
	//For debug in browser, pretty print the json ...
	//TODO: remove this method!
	@GET
	@Path("getAllArticlesHtml")
	@Produces("text/html")
	public String getAllArticlesHtml() throws ServiceNotFoundException, JSONException {
		String s = getAllArticles();
		if(s.trim().startsWith("[")) {
			s = new JSONArray(s).toString(4); 
		} else {
			s = new JSONObject(s).toString(4);
		}
		return "<pre>" + s + "</pre>";
	}
	
	@POST
	@Path("insertArticle")
	@Produces("text/html")
	public String insertArticle(@FormParam("article") String articleJSONString) throws ServiceNotFoundException,
			JSONException, IOException {
		ArticleDTO dto = gson.fromJson(articleJSONString, ArticleDTO.class);
		Article a = dto.toArticle();
		getDBService().insertArticle(a);
		return "OK";
	}

	private String errorJSON(Exception e) {
		JSONObject err = new JSONObject();
		try {
			err.put("status", "ERROR");
			err.put("message", e.getMessage());
			JSONArray stackTrace = new JSONArray();
			for(StackTraceElement ste : e.getStackTrace()) {
				stackTrace.put(ste.toString());
			}
			err.put("stackTrace", stackTrace);
			return err.toString();
		} catch (JSONException e1) {
			log.error(e);
		}
		return null;
	}
}
