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
		
		in = new FileInputStream("src/test/resources/accessToken.properties");
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
				p.getProperty("GET_FUNCTION_TABLE_URL"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(response.getCode(), 200);
	}

	@Test
	public void testGetAminoAcidContent() {
		OAuthRequest request = new OAuthRequest(Verb.GET,
				p.getProperty("GET_AMINO_ACID_CONTENT_URL"));
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
	
	@Test
	public void testGetJobDetails() {
		OAuthRequest request = new OAuthRequest(Verb.GET,
				p.getProperty("GET_JOB_DETAILS_URL"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertTrue(response.getCode() >= 200 && response.getCode() < 400);
	}
	
	@Test
	public void testPostJob() {
		OAuthRequest request = new OAuthRequest(Verb.POST,
				p.getProperty("POST_JOB_URL"));
		request.addBodyParameter("customer", p.getProperty("POST_JOB_CUSTOMER"));
		request.addBodyParameter("mg_url", p.getProperty("POST_JOB_MG_URL"));
		request.addBodyParameter("sample_label", p.getProperty("POST_JOB_SAMPLE_LABEL"));
		request.addBodyParameter("sample_environment", p.getProperty("POST_JOB_SAMPLE_ENVIRONMENT"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(response.getCode(), 201);
	}
	
	@Test
	public void testGetCodonUsage(){
		OAuthRequest request = new OAuthRequest(Verb.GET,
				p.getProperty("GET_CODON_USAGE_URL"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(response.getCode(), 200);
	}
	
	@Test
	public void testGetTaxonomicContent(){
		OAuthRequest request = new OAuthRequest(Verb.GET,
				p.getProperty("GET_TAXONOMIC_CONTENT_URL"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(response.getCode(), 200);
	}
}
