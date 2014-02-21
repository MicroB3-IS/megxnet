package net.megx.ws.mg.traits.rest;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class MGTraitsAPITest {

	Properties p = new Properties();
	InputStream in = null;
	OAuthService service = null;
	Token accessToken = null;

	@Before
	public void setUp() throws Exception {
		in = new FileInputStream("accessToken.properties");
		p.load(in);
		service = new ServiceBuilder().provider(MegxApi.class)
				.apiKey(p.getProperty("apiKey"))
				.apiSecret(p.getProperty("apiSecret"))
				.callback(p.getProperty("callback")).build();

		accessToken = new Token(p.getProperty("accessTokenApiKey"),
				p.getProperty("accessTokenApiSecret"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetTraitOverview() {
		OAuthRequest request = new OAuthRequest(Verb.GET,
				p.getProperty("GET_TRAIT_OVERVIEW_URL"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(response.getCode(), 200);
	}

	@Test
	public void testGetAllFinishedJobs() {
		OAuthRequest request = new OAuthRequest(Verb.GET,
				p.getProperty("GET_ALL_FINISHED_JOBS_URL"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(response.getCode(), 200);
	}

	@Test
	public void testGetSimpleTraits() {
		OAuthRequest request = new OAuthRequest(Verb.GET,
				p.getProperty("GET_SIMPLE_TRAITS_URL"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(response.getCode(), 200);
	}

	@Test
	public void testGetFunctionTable() {
		OAuthRequest request = new OAuthRequest(Verb.GET,
				p.getProperty("GET_FUNCTION_TABLE"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(response.getCode(), 200);
	}

	@Test
	public void testGetAminoAcidContent() {
		OAuthRequest request = new OAuthRequest(Verb.GET,
				p.getProperty("GET_AMINO_ACID_URL"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(response.getCode(), 200);
	}

	@Test
	public void testGetDiNucleotideOddsRatio() {
		OAuthRequest request = new OAuthRequest(Verb.GET,
				p.getProperty("GET_DI_NUCLEOTIDE_ODDS_RATIO_URL"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(response.getCode(), 200);
	}

}
