package net.megx.test;

import org.junit.rules.ExternalResource;
import com.jayway.restassured.authentication.AuthenticationScheme;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.authentication.OAuthScheme;

/**
 * JUnit Rule to set-up the remote HTTP Server configuration against which the
 * tests run as for RESTAssured.
 * 
 * TODO: get configuration from the environment
 * 
 * @author rkottman
 * 
 */
public class TestServer extends ExternalResource {

	private final String WS_PREFIX = "/ws/v1";

	private final String ESA_CONSUMER_KEY = "Paste Your Esa Consumer Key Here";
	private final String ESA_CONSUMER_SECRET = "Paste Your Esa Consumer Secret Here";
	private final String ESA_TOKEN_KEY = "Paste Your Esa Token Key Here";
	private final String ESA_TOKEN_SECRET = "Paste Your Esa Token Secret Here";
	
	private OAuthScheme esaOAuthScheme;

	@Override
	public void before() {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "${megx.test.server}";
		RestAssured.port = ${megx.test.port};
		RestAssured.basePath = "${megx.test.base.path}";
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		// RestAssured.rootPath = "x.y.z";
		
		esaOAuthScheme = new OAuthScheme();
		esaOAuthScheme.setConsumerKey(ESA_CONSUMER_KEY);
		esaOAuthScheme.setConsumerSecret(ESA_CONSUMER_SECRET);
		esaOAuthScheme.setAccessToken(ESA_TOKEN_KEY);
		esaOAuthScheme.setSecretToken(ESA_TOKEN_SECRET);
		 
	}

	public String getWSPrefix() {
		return WS_PREFIX;
	}

	public AuthenticationScheme getEsaOauthAuthenticationScheme() {
		return esaOAuthScheme;
	}

}
