package net.megx.security.apps;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
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

import net.megx.security.auth.model.Consumer;
import net.megx.security.auth.model.Token;
import net.megx.security.auth.model.User;
import net.megx.security.auth.services.ConsumerService;
import net.megx.security.auth.services.TokenService;
import net.megx.security.crypto.KeySecret;
import net.megx.security.crypto.KeySecretProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;


@Path("/apps")
public class AppsManager {
	
	private ConsumerService consumerService;
	private TokenService tokenService;
	private KeySecretProvider keySecretProvider;
	
	private Log log = LogFactory.getLog(getClass());
	
	public AppsManager(ConsumerService consumerService,
			TokenService tokenService) {
		super();
		this.consumerService = consumerService;
		this.tokenService = tokenService;
	}
	
	@Path("all")
	@GET
	@Produces("text/json")
	public List<Consumer> getConsumers(@Context HttpServletRequest request){
		String user = request.getUserPrincipal().getName();
		try {
			return consumerService.getConsumersForUser(user);
		} catch (Exception e) {
			log.error("Error while fetching applications:",e);
		}
		return null;
	}
	
	
	@Path("{key}/accessToken")
	@GET
	@Produces("text/json")
	public Token generateAccessToken(@Context HttpServletRequest request, @PathParam("key") String appKey){
		try{
			String user = request.getUserPrincipal().getName();
			List<Token> allTokens = tokenService.getTokensForUser(user);
			for(Token token: allTokens){
				if(token.getConsumerKey().equals(appKey)){
					return token;
				}
			}
			
			Consumer consumer = consumerService.getConsumer(appKey);
			
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
			return token;
		}catch (Exception e) {
			log.error("Failed to generate access token: ", e);
		}
		return null;
 	}
	
	@PUT
	@Path("{key}")
	@Produces("text/json")
	public Consumer updateConsumer(
			@PathParam("key") String appKey,
			InputStream input, 
			@Context HttpServletRequest request){
		try{
			Consumer consumer = consumerService.getConsumer(appKey);
			StringWriter writer = new StringWriter();
			Reader reader = new InputStreamReader(input);
			
			char [] buffer = new char[1024];
			int r = 0;
			while((r = reader.read(buffer, 0, buffer.length)) != -1){
				writer.write(buffer,0,r);
			}
			reader.close();
			
			JSONObject jsonConsumer = new JSONObject(writer.toString());
			
			if(jsonConsumer.has("name"))
				consumer.setName(jsonConsumer.getString("name"));
			if(jsonConsumer.has("description"))
				consumer.setDescription(jsonConsumer.getString("description"));
			if(jsonConsumer.has("callback"))
				consumer.setCallbackUrl(jsonConsumer.getString("callback"));
			if(jsonConsumer.has("oob"))
				consumer.setOob(jsonConsumer.getBoolean("oob"));
			
			consumerService.updateConsumer(consumer);
			return consumer;
			
		}catch (Exception e) {
			log.error("Unable to update consumer",e);
		}
		return null;
	}
	
	@POST
	@Produces("text/json")
	public Consumer addConsumer(@FormParam("name") String name,
			@FormParam("description")String description,
			@FormParam("callback") String callback,
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
			
			if(callback == null){
				consumer.setOob(true); // out of band anyway
			}
			
			return consumerService.addConsumer(consumer);
		}catch (Exception e) {
			log.error("Unable to add consumer",e);
		}
		return null;
	}
	
	@Path("{key}")
	@DELETE
	@Produces("text/json")
	public Consumer removeConsumer(@PathParam("key") String appKey,
			@Context HttpServletRequest request){
		Consumer consumer = new Consumer();
		consumer.setKey(appKey);
		try{
			consumerService.removeConsumer(consumer);
			return consumer;
		}catch (Exception e) {
			log.error("Failed to remove Consumer: ",e);
		}
		return null;
	}
	
}
