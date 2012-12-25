package net.megx.security.apps;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.megx.model.auth.Consumer;
import net.megx.security.auth.model.Token;
import net.megx.security.auth.model.User;
import net.megx.security.auth.services.ConsumerService;
import net.megx.security.auth.services.TokenService;
import net.megx.security.crypto.KeySecret;
import net.megx.security.crypto.KeySecretProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;

@Path("/apps")
public class AppsManager {

	private ConsumerService consumerService;
	private TokenService tokenService;
	private KeySecretProvider keySecretProvider;

	private Log log = LogFactory.getLog(getClass());

	private Gson gson = new Gson();

	public AppsManager(ConsumerService consumerService,
			TokenService tokenService, KeySecretProvider keySecretProvider) {
		super();
		this.consumerService = consumerService;
		this.tokenService = tokenService;
		this.keySecretProvider = keySecretProvider;
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getConsumers(@Context HttpServletRequest request) {
		String user = request.getUserPrincipal().getName();
		try {
			return toJSON(consumerService.getConsumersForUser(user));
		} catch (Exception e) {
			log.error("Error while fetching applications:", e);
			return toJSON(handleException(e));
		}
	}

	@Path("{key}/accessToken")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String generateAccessToken(@Context HttpServletRequest request,
			@PathParam("key") String appKey) {
		try {
			String user = request.getUserPrincipal().getName();
			List<Token> allTokens = tokenService.getTokensForUser(user);
			for (Token token : allTokens) {
				if (token.getConsumerKey().equals(appKey)) {
					return gson.toJson(token);
				}
			}

			Consumer consumer = consumerService.getConsumerForKey(appKey);

			Token token = new Token();
			KeySecret keySecret = keySecretProvider.createKeySecretPair();
			token.setAccessToken(true);
			token.setAuthorized(true);
			token.setConsumerKey(appKey);
			token.setCallbackUrl(consumer.getCallbackUrl());
			token.setTimestamp(new Date());
			token.setToken(keySecret.getKey());
			token.setSecret(keySecret.getSecret());
			User u = new User();
			u.setLogin(user);
			token.setUser(u);
			tokenService.saveToken(token.getToken(), token);
			return toJSON(token);
		} catch (Exception e) {
			log.error("Failed to generate access token: ", e);
			return toJSON(handleException(e));
		}
	}

	@PUT
	@Path("{key}")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateConsumer(@PathParam("key") String appKey,
			InputStream input, @Context HttpServletRequest request,
			@FormParam("name") String name,
			@FormParam("description") String description,
			@FormParam("callbackUrl") String callback,
			@FormParam("oob") Boolean oob) {
		try {
			Consumer consumer = consumerService.getConsumerForKey(appKey);
			if (consumer == null) {
				throw new Exception("No consumer with the specified key.");
			}
			if (!consumer.getLogname().equals(
					request.getUserPrincipal().getName())) {
				throw new Exception("Action forbidden.");
			}
			if (name != null)
				consumer.setName(name);
			if (description != null)
				consumer.setDescription(description);
			if (callback != null) {
				if (validURL(callback)) {
					consumer.setCallbackUrl(callback);
				} else {
					throw new Exception("Invalid callback URL.");
				}
			}
			if (oob != null)
				consumer.setOob(oob);

			consumerService.updateConsumer(consumer);
			return toJSON(consumer);

		} catch (Exception e) {
			log.error("Unable to update consumer", e);
			return toJSON(handleException(e));
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String addConsumer(@FormParam("name") String name,
			@FormParam("description") String description,
			@FormParam("callbackUrl") String callback,
			@FormParam("oob") Boolean oob, @Context HttpServletRequest request) {
		try {
			Consumer consumer = consumerService.getConsumer(name);
			if (consumer != null) {
				throw new Exception("The consumer name is not available.");
			}
			consumer = new Consumer();
			KeySecret keySecret = keySecretProvider.createKeySecretPair();
			consumer.setKey(keySecret.getKey());
			consumer.setSecret(keySecret.getSecret());

			String user = request.getUserPrincipal().getName();
			consumer.setLogname(user);

			consumer.setName(name);
			consumer.setDescription(description);
			consumer.setOob(oob);
			if (callback != null) {
				if (validURL(callback)) {
					consumer.setCallbackUrl(callback);
				} else {
					throw new Exception("Invalid callback URL.");
				}
			}

			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 3);
			consumer.setExpirationDate(calendar.getTime());

			if (callback == null) {
				consumer.setOob(true); // out of band anyway
			}

			return toJSON(consumerService.addConsumer(consumer));
		} catch (Exception e) {
			log.error("Unable to add consumer", e);
			return toJSON(handleException(e));
		}
	}

	@Path("{key}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public String removeConsumer(@PathParam("key") String appKey,
			@Context HttpServletRequest request) {
		try {
			Consumer consumer = consumerService.getConsumerForKey(appKey);
			if (consumer == null) {
				throw new Exception("No consumer with the specified key.");
			}
			if (!consumer.getLogname().equals(
					request.getUserPrincipal().getName())) {
				throw new Exception("Action forbidden.");
			}
			consumerService.removeConsumer(consumer);
			return toJSON(consumer);
		} catch (Exception e) {
			log.error("Failed to remove Consumer: ", e);
			return toJSON(handleException(e));
		}
	}

	public String toJSON(Object object) {
		return gson.toJson(object);
	}

	public <T> T fromJSON(String jsonSrc, Class<T> type) {
		return gson.fromJson(jsonSrc, type);
	}

	public Map<String, Object> handleException(Exception e) {
		Map<String, Object> result = new HashMap<String, Object>();
		log.error("An error occured while processing: ", e);
		result.put("error", true);
		result.put("message", e.getMessage());
		result.put("data", transformException(e));
		return result;
	}

	protected Map<String, Object> transformException(Exception e) {
		Map<String, Object> result = new HashMap<String, Object>();

		_mapException(result, e, new HashSet<Throwable>());

		return result;
	}

	private void _mapException(Map<String, Object> map, Throwable e,
			Set<Throwable> mapped) {
		if (mapped.contains(e)) {
			return;
		}
		map.put("message", e.getMessage());
		StackTraceElement[] ses = e.getStackTrace();
		if (ses != null && ses.length > 0) {
			String[] stackTrace = new String[ses.length];
			for (int i = 0; i < ses.length; i++) {
				StackTraceElement el = ses[i];
				String line = String.format("%s#%s (%s, line %d)",
						el.getClassName(), el.getMethodName(),
						el.getFileName(), el.getLineNumber());
				stackTrace[i] = line;
			}
			map.put("stackTrace", stackTrace);
		}
		mapped.add(e);
		if (e.getCause() != null) {
			Map<String, Object> cause = new HashMap<String, Object>();
			_mapException(cause, e.getCause(), mapped);
			map.put("cause", cause);
		}
	}

	private boolean validURL(String url) {
		try {
			new URL(url);
		} catch (MalformedURLException e) {
			return false;
		}
		return true;
	}
}
