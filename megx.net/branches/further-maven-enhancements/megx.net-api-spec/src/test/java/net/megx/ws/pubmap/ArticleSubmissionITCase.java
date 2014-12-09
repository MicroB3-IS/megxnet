package net.megx.ws.pubmap;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import net.megx.test.TestServer;
import net.megx.test.categories.IntegrationTest;
import net.megx.test.categories.RESTServiceTest;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;

public class ArticleSubmissionITCase {

	@Rule
	public TestServer ts = new TestServer();
	private String wsPrefix = "";

	@Before
	public void setUp() {
		this.wsPrefix = ts.getWSPrefix() + "/pubmap/v1.0.0";
	}
	
	/**
	 *  Test case for: storing article successful.
	 *  @param article the article json provided from bookmarklet.
	 */
	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void successfulArticleSubmission() throws IOException {

		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("pubmap-article.json");

		String article = IOUtils.toString(in);

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON).formParam("article", article)
				.when().post(this.wsPrefix + "/article").then().statusCode(200)
				.log();

	}
	
	/**
	 *  Test case for: storing article failed when article not provided.
	 *  @param article the article json provided from bookmarklet.
	 */
	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class})
	public void failArticleSubmission()throws IOException {

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON).formParam("article", "")
				.when().post(this.wsPrefix + "/article").then().statusCode(400)
				.log();
	}
	
	/**
	 *  Test case for: retrieving all articles successfully.
	 */
	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void successfulRetrieveAllArticles() {
		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON).get(this.wsPrefix + "/all")
				.then().statusCode(200).log();
	}
	
	/**
	 *  Test case for: i have the coordinates give me the place name when success.
	 *  @param lat the latitude value
	 *  @param lon the longitude value 
	 */
	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void successfulFindNearby() {

		HashMap<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("lat", "42.3");
		parametersMap.put("lon", "12");

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON).queryParameters(parametersMap)
				.get(this.wsPrefix + "/placename").then().statusCode(200).log();
	}
	
	/**
	 *  Test case for: i have the coordinates give me the place name when latitude parameter not provided.
	 *  @param lat the latitude value
	 *  @param lon the longitude value 
	 */
	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void failLatMissingFindNearby() {

		HashMap<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("lat", "");
		parametersMap.put("lon", "5");

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON).queryParameters(parametersMap)
				.get(this.wsPrefix + "/placename").then().statusCode(400).log();
	}
	
	/**
	 *  Test case for: i have the coordinates give me the place name when latitude value is out of range.
	 *  @param lat the latitude value
	 *  @param lon the longitude value 
	 */
	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void failLatOutOfRangeFindNearby() {

		HashMap<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("lat", "95");
		parametersMap.put("lon", "5");

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON).queryParameters(parametersMap)
				.get(this.wsPrefix + "/placename").then().statusCode(400).log();
	}

	/**
	 *  Test case for: i have the coordinates give me the place name when longitude parameter not provided.
	 *  @param lat the latitude value
	 *  @param lon the longitude value 
	 */
	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void failLonMissingFindNearby() {

		HashMap<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("lat", "12");
		parametersMap.put("lon", "");

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON).queryParameters(parametersMap)
				.get(this.wsPrefix + "/placename").then().statusCode(400).log();
	}
	
	/**
	 *  Test case for: i have the coordinates give me the place name when longitude value is out of range.
	 *  @param lat the latitude value
	 *  @param lon the longitude value 
	 */
	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void failLonOutOfRangeFindNearby() {

		HashMap<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("lat", "-18.3");
		parametersMap.put("lon", "193");

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON).queryParameters(parametersMap)
				.get(this.wsPrefix + "/placename").then().statusCode(400).log();
	}
	
	/**
	 *  Test case for: i have location name give me the coordinates when success.
	 *  @param q the place name
	 *  @param worldRegion the region scope for the place name
	 */
	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void successfulFindCoordinates() {
		
		HashMap<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("q", "Bitola");
		parametersMap.put("worldRegion", "MK");

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON).queryParameters(parametersMap)
				.get(this.wsPrefix + "/coordinates").then().statusCode(200).log();
	}
	
	/**
	 *  Test case for: i have ocean name give me the coordinates when ocean selected and 
	 *  place name not required.
	 *  @param q the place name
	 *  @param worldRegion the region scope for the place name
	 */
	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void successfulFindOceanCoordinates() {
		
		HashMap<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("q", "");
		parametersMap.put("worldRegion", "Coral Sea");

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON).queryParameters(parametersMap)
				.get(this.wsPrefix + "/coordinates").then().statusCode(200).log();
	}
	
	/**
	 *  Test case for: i have location name give me the coordinates when 
	 *  place name not found in region scope.
	 *  @param q the place name
	 *  @param worldRegion the region scope for the place name
	 */
	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void failFindCoordinates() {
		
		HashMap<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("q", "Bremen");
		parametersMap.put("worldRegion", "MK");

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON).queryParameters(parametersMap)
				.get(this.wsPrefix + "/coordinates").then().statusCode(500).log();
	}
	
	/**
	 *  Test case for: i have location name give me the coordinates when 
	 *  place name parameter not provided.
	 *  @param q the place name
	 *  @param worldRegion the region scope for the place name
	 */
	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void failMissingFindCoordinates() {
		
		HashMap<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("q", "");
		parametersMap.put("worldRegion", "MK");

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON).queryParameters(parametersMap)
				.get(this.wsPrefix + "/coordinates").then().statusCode(400).log();
	}

}
