package net.megx.security.apps;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import org.json.JSONObject;

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
	public String getConsumers(@Context HttpServletRequest request){
		String user = request.getUserPrincipal().getName();
		try {
			return gson.toJson(consumerService.getConsumersForUser(user));
		} catch (Exception e) {
			log.error("Error while fetching applications:",e);
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Path("{key}/accessToken")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String generateAccessToken(@Context HttpServletRequest request, @PathParam("key") String appKey){
		try{
			String user = request.getUserPrincipal().getName();
			List<Token> allTokens = tokenService.getTokensForUser(user);
			for(Token token: allTokens){
				if(token.getConsumerKey().equals(appKey)){
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
			return gson.toJson(token);
		}catch (Exception e) {
			log.error("Failed to generate access token: ", e);
			e.printStackTrace();
		}
		return null;
 	}
	
	@PUT
	@Path("{key}")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateConsumer(
			@PathParam("key") String appKey,
			InputStream input, 
			@Context HttpServletRequest request,
			@FormParam("name") String name,
			@FormParam("description") String description,
			@FormParam("callbackUrl") String callback,
			@FormParam("oob") Boolean oob){
		try{
			Consumer consumer = consumerService.getConsumerForKey(appKey);
			
			if(name != null)
				consumer.setName(name);
			if(description != null)
				consumer.setDescription(description);
			if(callback != null)
				consumer.setCallbackUrl(callback);
			if(oob != null)
				consumer.setOob(oob);
			
			consumerService.updateConsumer(consumer);
			return  gson.toJson(consumer);
			
		}catch (Exception e) {
			log.error("Unable to update consumer",e);
			e.printStackTrace();
		}
		return null;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String addConsumer(@FormParam("name") String name,
			@FormParam("description")String description,
			@FormParam("callbackUrl") String callback,
			@FormParam("oob") Boolean oob, 
			@Context HttpServletRequest request){
		try{
			Consumer consumer = new Consumer();
			KeySecret keySecret = keySecretProvider.createKeySecretPair();
			consumer.setKey(keySecret.getKey());
			consumer.setSecret(keySecret.getSecret());
			
			String user = request.getUserPrincipal().getName();
			consumer.setLogname(user);
			
			
			consumer.setName(name);
			consumer.setDescription(description);
			consumer.setOob(oob);
			consumer.setCallbackUrl(callback);
			
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 3);
			consumer.setExpirationDate(calendar.getTime());
			
			if(callback == null){
				consumer.setOob(true); // out of band anyway
			}
			
			return gson.toJson(consumerService.addConsumer(consumer));
		}catch (Exception e) {
			log.error("Unable to add consumer",e);
			e.printStackTrace();
		}
		return null;
	}
	
	@Path("{key}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public String removeConsumer(@PathParam("key") String appKey,
			@Context HttpServletRequest request){
		Consumer consumer = new Consumer();
		consumer.setKey(appKey);
		try{
			consumerService.removeConsumer(consumer);
			return gson.toJson(consumer);
		}catch (Exception e) {
			log.error("Failed to remove Consumer: ",e);
			e.printStackTrace();
		}
		return null;
	}
	
}
