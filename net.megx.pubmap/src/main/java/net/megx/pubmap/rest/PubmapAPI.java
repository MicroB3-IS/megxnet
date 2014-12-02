package net.megx.pubmap.rest;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.megdb.pubmap.PubMapService;
import net.megx.model.pubmap.Article;
import net.megx.model.pubmap.Ocean;
import net.megx.pubmap.geonames.GeonamesService;
import net.megx.pubmap.geonames.model.Place;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.Result;

@Path("v1/pubmap/v1.0.0")
public class PubmapAPI extends BaseRestService {

	@Context
	HttpServletRequest request;

	private PubMapService service;
	private GeonamesService geonamesService;

	public PubmapAPI(PubMapService service, GeonamesService geonamesService) {
		this.service = service;
		this.geonamesService = geonamesService;

	}

	@Path("article")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String storeArticle(@FormParam("article") String articleJson) {
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
			log.error("Could not store article in db: " + e);
			return toJSON(handleException(e));
		} catch (Exception e) {
			log.error("Server error while saving article: " + e);
			return toJSON(handleException(e));
		}
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllArticles() {
		try {
			return toJSON(new Result<List<Article>>(service.getAllArticles()));
		} catch (DBGeneralFailureException e) {
			log.error("Could not retrieve all Articles from db");
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			log.error("No Articles exists");
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		} catch (Exception e) {
			log.error("Server error while getting all articles: " + e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Path("placename")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String findNearby(@QueryParam("lat") String lat,
			@QueryParam("lon") String lon) {

		Place place = new Place();

		try {

			if (lat == null) {
				throw new Exception("Latitude parameter not provided.");

			} else if (lon == null) {
				throw new Exception("Longitude parameter not provided.");
			}

			place = geonamesService.getPlaceName(lat, lon);
			return toJSON(new Result<Place>(place));

		} catch (Exception e) {
			log.error("Server error: ", e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Path("coordinates")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String findCoordinates(@QueryParam("q") String placeName,
			@QueryParam("worldRegion") String worldRegion) {

		Place place = new Place();
		Ocean ocean = new Ocean();

		try {

			if (worldRegion == null) {
				throw new Exception("WorldRegion parameter not provided.");

			}

			if (service.isOcean(worldRegion)) {

				ocean = service.getOceanByName(worldRegion);
				place.setWorldRegion(ocean.getOceanName());
				place.setPlaceName(placeName);
				place.setLat(ocean.getLat().toString());
				place.setLon(ocean.getLon().toString());

			} else {

				if (placeName == null) {
					throw new Exception("PlaceName parameter not provided.");

				} else {
					place = geonamesService.getCoordinates(placeName,
							worldRegion);
				}
			}

			return toJSON(new Result<Place>(place));

		} catch (DBGeneralFailureException e) {
			log.error("Could not retrieve Ocean from db");
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			log.error("No Ocean exists");
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		} catch (Exception e) {
			log.error("Server error: ", e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
}
