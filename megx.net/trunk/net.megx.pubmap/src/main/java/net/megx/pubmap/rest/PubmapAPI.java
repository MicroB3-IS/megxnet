package net.megx.pubmap.rest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.megdb.pubmap.PubMapService;
import net.megx.model.pubmap.Article;
import net.megx.pubmap.external.beans.Geonames;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.Result;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

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

	@Path("placename")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public void findNearby(@QueryParam("lat") String lat,
			@QueryParam("lon") String lon, @Context HttpServletRequest request) {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		InputStream instream = null;
		URI uri = null;
		String country;
		String placeName;

		try {
			uri = new URIBuilder().setScheme("http")
					.setHost("api.geonames.org").setPath("/extendedFindNearby")
					.setParameter("lat", lat).setParameter("lng", lon)
					.setParameter("username", "megx").build();

			HttpGet httpget = new HttpGet(uri);
			response = httpclient.execute(httpget);
			int status = response.getStatusLine().getStatusCode();

			if (status >= 200 && status < 300) {

				HttpEntity entity = response.getEntity();

				if (entity != null) {
					
					instream = entity.getContent();
					JAXBContext context = JAXBContext.newInstance(Geonames.class);
					Unmarshaller um = context.createUnmarshaller();
					Geonames geonames = (Geonames) um.unmarshal(instream);

					if (geonames.getGeonamesLst() != null) {

						System.out.println("land");

					} else if (geonames.getOcean() != null) {

						System.out.println("ocean");
//						placeName = geonames.getOcean().getName();
						
					} else if (geonames.getAddress() != null) {

						System.out.println("US address");
					}
					EntityUtils.consume(entity);
				}
			} else {
				throw new ClientProtocolException(
						"Unexpected response status: " + status);
			}

		} catch (URISyntaxException e) {
			log.error("Wrong URI" + uri, e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (ClientProtocolException e) {
			log.error("HTTPReq:ClientProtocolException ", e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (JAXBException e) {
			log.error("JAXBException ", e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			log.error("HTTPReq:IOException ", e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (Throwable e) {
			log.error("HTTPReq:Exception ", e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				instream.close();
				response.close();
			} catch (IOException e) {
				log.error("HTTPReq:Exception closing response ", e);
			}
		}
	}
}
