package net.megx.pubmap.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.ServiceNotFoundException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.megx.megdb.pubmap.PubMapService;
import net.megx.model.Article;
import net.megx.pubmap.mock.PubMapMockService;
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
	
	
	public static final String CFG_KEY_JSON_PRETTY_PRINT = "json_pretty_print";
	public static final String CFG_KEY_JSON_PRETTY_PRINT_INDENT = "json_pretty_print_indent";

	private boolean JSON_PRETTY_PRINT = false;
	private int JSON_PRETTY_PRINT_INDENT = 3;

	public PubMapRest(BundleContext bundleContext, JSONObject cfg) {
		this.bundleContext = bundleContext;
		JSON_PRETTY_PRINT = cfg.optBoolean(CFG_KEY_JSON_PRETTY_PRINT,
				JSON_PRETTY_PRINT);
		JSON_PRETTY_PRINT_INDENT = cfg.optInt(CFG_KEY_JSON_PRETTY_PRINT_INDENT,
				JSON_PRETTY_PRINT_INDENT);
	}

	private PubMapService getDBService() throws ServiceNotFoundException {
		ServiceReference ref = bundleContext
				.getServiceReference(PubMapService.class.getName());
		if (ref == null) {
			throw new ServiceNotFoundException(PubMapService.class.getName());
		}
		return (PubMapService) bundleContext.getService(ref);
	}

	@GET
	@Path("articles")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllArticles() throws Exception {
		log.debug("Called pubmap/getAllArticles");
		try {
			List<Article> articles = getDBService().getAllArticles();
			List<ArticleDTO> articleDTOs = new ArrayList<ArticleDTO>();
			for (Article a : articles) {
				articleDTOs.add(ArticleDTO.fromDAO(a));
			}
			return toJSONString(articleDTOs);
		} catch (Exception e) {
			log.error("Error in getAllArticles", e);
			return handleException(e);
		}
	}

	@GET
	@Path("article/{id_code}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getArticleById(@PathParam("id_code") String idCode,
			@QueryParam("id") String id) throws Exception {
		log.debug("Called pubmap/getArticleById");
		try {
			Article a = getDBService().selectArticleDetailsById(id, idCode);
			if (a == null) {
				throw new Exception("Article with id_code: " + idCode
						+ " and id: " + id + " not found in database.");
			}
			return toJSONString(ArticleDTO.fromDAO(a));
		} catch (Exception e) {
			log.error("Error in getAllArticles", e);
			return handleException(e);
		}
	}

	@POST
	@Path("article/add")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/html")
	public String insertArticleFromForm(
			@FormParam("article") String articleJSONString) throws Exception {
		try {
			
			//TEMP FIX FOR: https://colab.mpi-bremen.de/its/browse/MEGX-160
			articleJSONString = articleJSONString.replaceAll("Identifiers", "identifiers");
			
			ArticleDTO dto = gson.fromJson(articleJSONString, ArticleDTO.class);
			Article a = dto.toDAO();
			int code = getDBService().insertArticle(a);
			JSONObject resp = new JSONObject();
			resp.put("code", code);
			resp.put("status", "OK");
			return toJSONString(resp);
		} catch (Exception e) {
			log.error("Error in insertArticle=" + articleJSONString, e);
			return handleException(e);
		}
	}

	@POST
	@Path("/article/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertArticleFromJson(String jsonBody) {

		
		//try {
			//TEMP FIX FOR: https://colab.mpi-bremen.de/its/browse/MEGX-160 
			jsonBody = jsonBody.replaceAll("Identifiers", "identifiers");
			/**
			 * TODO here we could write the json to a file in a directory of jsons or the JCR repsository
			 */
			
			
			
			
			ArticleDTO dto = gson.fromJson(jsonBody, ArticleDTO.class);
			Article a = dto.toDAO();
			try {
				PubMapMockService.saveTofile(a.getDOI(), jsonBody);
			} catch (IOException e) {
				log.error("Error in insertArticle", e);
				throw new WebApplicationException(Response.serverError()
						.entity(prettyPrintJSONExceptionMessage(e)).build());
			}
			/*	
			getDBService().insertArticle(a);
			
		} catch (ServiceNotFoundException e) {
			log.error("Error in insertArticle", e);
			throw new WebApplicationException(Response.serverError()
					.entity(prettyPrintJSONExceptionMessage(e)).build());
		} catch (SQLException e) {
			log.error("Error in insertArticle", e);
			throw new WebApplicationException(Response.serverError()
					.entity(prettyPrintJSONExceptionMessage(e)).build());
		} catch (IllegalStateException e) {
			log.error("Error in insertArticle", e);
			throw new WebApplicationException(Response.serverError()
					.entity(prettyPrintJSONExceptionMessage(e)).build());
		} catch (JsonSyntaxException e) {
			log.error("Error in insertArticle=" + jsonBody, e);
			throw new WebApplicationException(Response.serverError()
					.entity(prettyPrintJSONExceptionMessage(e)).build());
		}*/
		return Response.ok().build();
	}

	private String handleException(Exception e) throws Exception {
		// for now just rethrow it
		if (true)
			throw e;

		JSONObject err = new JSONObject();
		try {
			err.put("status", "ERROR");
			err.put("message", e.getMessage());
			JSONArray stackTrace = new JSONArray();
			for (StackTraceElement ste : e.getStackTrace()) {
				stackTrace.put(ste.toString());
			}
			err.put("stackTrace", stackTrace);
			return toJSONString(err);
		} catch (JSONException e1) {
			log.error(e);
		}
		return null;
	}

	private String prettyPrintJSONExceptionMessage(Exception e) {
		JSONObject err = new JSONObject();
		try {
			err.put("message", e.getMessage());
			JSONArray stackTrace = new JSONArray();
			for (StackTraceElement ste : e.getStackTrace()) {
				stackTrace.put(ste.toString());
			}
			err.put("stackTrace", stackTrace);
			return prettyPrintJSON(err);
		} catch (JSONException e1) {
			log.error("Could not create json error object: " + e);
		}

		return prettyPrintJSON(err);
	}

	private String prettyPrintJSON(Object json) {
		try {
			if (json instanceof JSONArray) {
				JSONArray arr = (JSONArray) json;
				return arr.toString(JSON_PRETTY_PRINT_INDENT);
			}
			if (json instanceof JSONObject) {
				JSONObject obj = (JSONObject) json;
				return obj.toString(JSON_PRETTY_PRINT_INDENT);
			}
		} catch (JSONException e) {
			// ignore, should never happen
			// e.printStackTrace();
		}
		if (json == null) {
			return null;
		}
		return json.toString();
	}

	private String toJSONString(Object dto) {
		if (dto instanceof JSONObject || dto instanceof JSONArray) {
			// object already json
			if (JSON_PRETTY_PRINT) {
				return prettyPrintJSON(dto);
			} else {
				return dto.toString();
			}
		} else {
			// not json, candidate for gson
			String str = gson.toJson(dto);
			if (JSON_PRETTY_PRINT) {
				try {
					if (str.startsWith("[")) {
						return prettyPrintJSON(new JSONArray(str));
					} else {
						return prettyPrintJSON(new JSONObject(str));
					}
				} catch (JSONException e) {
					// ignore, should never happen
					// e.printStackTrace();
				}
			}
			return str;
		}
	}
}