package net.megx.ws.contact;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.HashMap;

import net.megx.test.TestServer;
import net.megx.test.categories.IntegrationTest;
import net.megx.test.categories.RESTServiceTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;

public class ContactSubmissionITCase {

	@Rule
	public TestServer ts = new TestServer();
	private String wsPrefix = "";

	@Before
	public void setUp() {
		this.wsPrefix = ts.getWSPrefix() + "/contact/v1.0.0";
	}

	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void successfulContactSubmission() throws IOException {

		HashMap<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put("email", "test@gmail.com");
		parametersMap.put("name", "Test Name");
		parametersMap.put("comment", "Test Comment");

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.URLENC).formParams(parametersMap)
				.when().post(this.wsPrefix + "/store-contact").then()
				.statusCode(200).log();

	}
}
