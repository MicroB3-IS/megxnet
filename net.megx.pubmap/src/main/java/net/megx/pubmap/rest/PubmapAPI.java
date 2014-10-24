package net.megx.pubmap.rest;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.megdb.pubmap.PubMapService;
import net.megx.model.pubmap.Article;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.Result;

@Path("v1/pubmap/v1.0.0")
public class PubmapAPI extends BaseRestService {

	private PubMapService service;

	public PubmapAPI(PubMapService service) {
		this.service = service;
	}

	@Path("article")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String storeArticle(@FormParam("article") String articleJson,
			@Context HttpServletRequest request) {
		try {
			if (articleJson == null) {
				return toJSON(new Result<String>(true, "article not provided",
						"bad-request"));
			}
			Article article = gson.fromJson(articleJson, Article.class);
			String articleCreator;
			String status;

			article.setArticleXML(article.getArticleXML()
					.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
					.replaceAll("&nbsp;", " ").replaceAll("&amp;", "&"));
			article.setCreated(Calendar.getInstance().getTime());

			if (request.getUserPrincipal() != null) {
				articleCreator = request.getUserPrincipal().getName();
			} else {
				articleCreator = "anonymous";
			}
			article.setUserName(articleCreator);

			if (service.articleExists(article.getPmid())) {
				status = "Bookmark already exists on server.";
			} else {
				service.storeArticle(article);
				status = "Bookmark successfully stored to server.";
			}

			return toJSON(status);
		} catch (DBGeneralFailureException e) {
			log.error("Could not store article:" + e);
			return toJSON(handleException(e));
		} catch (Exception e) {
			log.error("Server error:" + e);
			return toJSON(handleException(e));
		}
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllArticles(@Context HttpServletRequest request) {
		try {
			return toJSON(new Result<List<Article>>(service.getAllArticles()));
		} catch (DBGeneralFailureException e) {
			log.error("Could not retrieve all Articles");
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			log.error("No Articles exists");
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		} catch (Exception e) {
			log.error("Server error:" + e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

}
