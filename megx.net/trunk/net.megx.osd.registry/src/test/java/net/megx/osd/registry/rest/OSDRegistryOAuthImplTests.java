package net.megx.osd.registry.rest;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class OSDRegistryOAuthImplTests {
	
	private Properties p = new Properties();
	private InputStream in = null;
	private OAuthService service;
	private Token accessToken;
	

	String participant = "{\"osdID\": \"3\",\"siteName\": \"TestSite\",\"institution\": \"TestInstitution\",\"institutionAddress\": \"TestAddress\",\"country\": \"TestCountry\",\"institutionWebAddress\": \"http://test-web.com\",\"siteCoordinator\": \"test\",\"coordinatorEmail\": \"test@mail.com\",\"siteLat\": \"3.3\",\"siteLong\": \"3.3\",\"institutionLat\": \"3.3\",\"institutionLong\": \"3.3\",\"id\": \"2a5887dc-d329-4bab-adec-a8186c8bf7e22\"}";
	//String updatedParticipant = "{\"osdID\": \"3\",\"siteName\": \"TestSite\",\"institution\": \"TestInstitution\",\"institutionAddress\": \"TestAddress\",\"country\": \"TestCountry\",\"institutionWebAddress\": \"http://test-web.com\",\"siteCoordinator\": \"test\",\"coordinatorEmail\": \"test@mail.com\",\"siteLat\": \"3.3\",\"siteLong\": \"3.3\",\"institutionLat\": \"3.3\",\"institutionLong\": \"3.3\",\"id\": \"\"}";
	//String id = "2a5887dc-d329-4bab-adec-a8186c8bf7e22";
	
	@Before
	public void setUp() throws Exception {
		
		in = new FileInputStream("src/test/resources/accessToken.properties");
		p.load(in);
		service = new ServiceBuilder().provider(MegxApi.class)
				.apiKey(p.getProperty("apiKey"))
				.apiSecret(p.getProperty("apiSecret"))
				.callback(p.getProperty("callback"))
				.build();

		accessToken = new Token(p.getProperty("accessTokenApiKey"),
				p.getProperty("accessTokenApiSecret"));
		
	}
	
	@Test 
	public void testGetAllParticipants(){
		OAuthRequest request = new OAuthRequest(Verb.GET, p.getProperty("GET_ALL_PARTICIPANTS_URL"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(200, response.getCode());
		
	}

	@Test
	public void testAddParticipant(){
		OAuthRequest request = new OAuthRequest(Verb.POST, p.getProperty("POST_ADD_PARTICIPANT_URL"));
		request.addHeader("Content-Type", "application/json");
		request.addBodyParameter("participant", participant);
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(200, response.getCode());
	}
	
	/*@Test
	public void testUpdateParticipant(){
		OAuthRequest request = new OAuthRequest(Verb.POST, p.getProperty("UPDATE_PARTICIPANT_URL"));
		request.addHeader("Content-Type", "application/json");
		request.addBodyParameter("participant", updatedParticipant);
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(200, response.getCode());
	}
	
	@Test
	public void testDeleteParticipant(){
		OAuthRequest request = new OAuthRequest(Verb.DELETE, p.getProperty("DELETE_PARTICIPANT_URL"));
		request.addHeader("Content-Type", "application/json");
		request.addBodyParameter("id", id);
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(200, response.getCode());
	}*/
	
	@Test
	public void testGetParticipant(){
		OAuthRequest request = new OAuthRequest(Verb.GET, p.getProperty("GET_PARTICIPANT_URL"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(200, response.getCode());
	}
	
	
}
