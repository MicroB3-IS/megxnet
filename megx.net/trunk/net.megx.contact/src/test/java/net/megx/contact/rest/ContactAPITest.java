package net.megx.contact.rest;

import java.io.FileInputStream;
import java.io.IOException;
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

public class ContactAPITest {

	private Properties p = new Properties();
	private InputStream in = null;
	private Token accessToken;
	private OAuthService service;

	@Before
	public void setUp() throws IOException {
		in = new FileInputStream("src/test/resources/accessToken.properties");
		p.load(in);
		service = new ServiceBuilder().provider(MegxApi.class)
				.apiKey(p.getProperty("apiKey"))
				.apiSecret(p.getProperty("apiSecret"))
				.callback(p.getProperty("callback")).build();

		accessToken = new Token(p.getProperty("accessTokenApiKey"),
				p.getProperty("accessTokenApiSecret"));

	}

	@Test
	public void storeContactMailTest() {
		OAuthRequest request = new OAuthRequest(Verb.POST,
				p.getProperty("POST_CONTACT_MAIL_URL"));
		request.addBodyParameter("email", p.getProperty("POST_EMAIL"));
		request.addBodyParameter("name", p.getProperty("POST_NAME"));
		request.addBodyParameter("comment", p.getProperty("POST_COMMENT"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(200, response.getCode());
	}
}
