package net.megx.ws.esa;

import static io.restassured.RestAssured.given;

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

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;

public class EarthSamplingAppITCase {

	@Rule
	public TestServer ts = new TestServer();
	private String wsPrefix = "";
	private AuthenticationScheme authentication;

	@Before
	public void setUp() {
		this.wsPrefix = ts.getWSPrefix() + "/esa/v1.0.0";
		this.authentication = ts.getEsaOauthAuthenticationScheme();
	}

	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void retrieveAllSamples() {
		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON)
				.get(this.wsPrefix + "/allSamples").then().statusCode(200)
				.log();
	}

	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void retrieveAllSampledOceans() {
		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON)
				.get(this.wsPrefix + "/sampledOceans").then().statusCode(200)
				.log();
	}

	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void downloadSamples() {
		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON)
				.formParam(
						"sampleIds",
						"f69b5cf64456926e:5d4358e7-32c6-48d5-be5a-c2010d78f19a,"
								+ "a64b9e722fc639ee:487d2eea-5811-4a2e-a768-865f88111059,"
								+ "a64b9e722fc639ee:dd4cca7a-be0d-4f60-bb63-228f3aa42762")
				.post(this.wsPrefix + "/downloadSamples.csv").then()
				.statusCode(200).log();
	}

	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void retrieveSample() {
		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON)
				.queryParam("sampleId",
						"f69b5cf64456926e:5d4358e7-32c6-48d5-be5a-c2010d78f19a")
				.get(this.wsPrefix + "/sample").then().statusCode(200).log();
	}

	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void retrieveObservations() {
		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON).pathParam("nbObservations", 2)
				.get(this.wsPrefix + "/observations/{nbObservations}").then()
				.statusCode(200).log();
	}

	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void retrieveSamplesForCollector() {
		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.TEXT)
				.pathParam("creator", "anonymous")
				.get(this.wsPrefix + "/samples/{creator}").then()
				.statusCode(200).log();
	}

	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void successfulSingleSampleSubmission() throws IOException {

		HashMap<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("air_temperature", "25");
		parametersMap.put("biome", "22.2");
		parametersMap.put("comment", "TestComment");
		parametersMap.put("date_taken", "2014-07-10 11:00:00-04");
		parametersMap.put("depth", "5");
		parametersMap.put("gps_accuracy", "1");
		parametersMap.put("json", "(\"testJson\" : \"testJson\")");
		parametersMap.put("latitude", "15");
		parametersMap.put("longitude", "15");
		parametersMap.put("nitrate", "0.1");
		parametersMap.put("nitrite", "0.1");
		parametersMap.put("origin", "TestOrigin");
		parametersMap.put("phosphate", "0.1");
		parametersMap.put("salinity", "0.1");
		parametersMap.put("sample_name", "TestSample");
		parametersMap.put("secchi_depth", "5");
		parametersMap.put("submit", "2014-07-10 11:00:00-04");
		parametersMap.put("time_taken", "2014-07-10 11:00:00-04e");
		parametersMap.put("version", "1.6");
		parametersMap.put("water_temperature", "15");
		parametersMap.put("weather_condition", "TestSunny");
		parametersMap.put("wind_speed", "25");

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.URLENC).formParams(parametersMap)
				.when().post(this.wsPrefix + "/observation").then()
				.statusCode(200).log();

	}

	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void successfulSamplesSubmission() throws IOException {

		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("osd-app-samples.json");
		String samplesJson = IOUtils.toString(in);

		RestAssured.authentication = this.authentication;

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON)
				.formParam("samples", samplesJson).when()
				.post(this.wsPrefix + "/samples").then().statusCode(200).log();

	}

	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void retrieveConfigurationForCitizen() {
		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON)
				.get(this.wsPrefix + "/citizenConfig").then().statusCode(200)
				.log();
	}

	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void retrieveConfigurationForScientist() {
		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON)
				.get(this.wsPrefix + "/scientistConfig").then().statusCode(200)
				.log();
	}

}
