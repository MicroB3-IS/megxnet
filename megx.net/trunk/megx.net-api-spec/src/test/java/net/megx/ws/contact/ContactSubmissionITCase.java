package net.megx.ws.contact;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;

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
	private String email = "test@gmail.com";
	private String name = "Test Name";
	private String comment = "Test Comment";

	@Before
	public void setUp() {
		this.wsPrefix = ts.getWSPrefix() + "/contact/v1.0.0";
	}

	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void successfulContactSubmission() throws IOException {

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.URLENC).formParam("email", email)
				.formParam("name", name).formParam("comment", comment).when()
				.post(this.wsPrefix + "/store-contact").then().statusCode(200)
				.log();

	}
}
