package net.megx.pubmap.rest;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.megx.megdb.pubmap.PubMapService;
import net.megx.model.pubmap.Article;
import net.megx.ws.core.BaseRestService;

@Path("v1/pubmap/v1.0.0")
public class PubmapAPI extends BaseRestService {

	private PubMapService service;

	public PubmapAPI(PubMapService service) {
		this.service = service;
	}

	@Path("article")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String storeArticle(@FormParam("pmid") int pmid,
			@FormParam("lon") Double lon,
			@FormParam("lat") Double lat,
			@FormParam("articleXml")final String articleXml,
			@FormParam("megxBarJson") String megxBarJson,
			@Context HttpServletRequest request) {
		
			if (lon == null && lat == null) {
				return toJSON(new Result<String>(true, "Longitude and latitude not provided",
						"bad-request"));
			}
		Article article = new Article();
		String savedArticle = null;
        Date created = Calendar.getInstance().getTime();
        String articleCreator;
        if (request.getUserPrincipal() != null) {
            articleCreator = request.getUserPrincipal().getName();
        } else {
        	articleCreator = "anonymous";
        }
        article.setPmid(pmid);
        article.setCreated(created);
        article.setLat(lat);
        article.setLon(lon);
        article.setArticleXML(articleXml);
        article.setUserName(articleCreator);
        article.setMegxBarJSON(megxBarJson);
        
        try{
        	savedArticle = service.storeArticles(article);
        } catch (Exception e) {
            log.error("Could not save article", e);
            return toJSON(handleException(e));
        }
        
		return savedArticle;

	}
}
