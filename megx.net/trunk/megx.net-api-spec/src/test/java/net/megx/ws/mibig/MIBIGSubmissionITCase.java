package net.megx.ws.mibig;

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

public class MIBIGSubmissionITCase {

	@Rule
	public TestServer ts = new TestServer();
	private String wsPrefix = "";

	@Before
	public void setUp() {
		this.wsPrefix = ts.getWSPrefix() + "/mibig/v1.0.0";
	}

	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void successfulMibigSubmission() throws IOException {

		// read json file data to String
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("mibig-submission.json");

		String json = IOUtils.toString(in);

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.URLENC).formParam("json", json)
				.formParam("version", "1").when()
				.post(this.wsPrefix + "/bgc-registration").then()
				.statusCode(200).log();

	}

	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void successfulBgcDetailGeneInfoSubmission() throws IOException {

		HashMap<String, Object> formParams = new HashMap<String, Object>();
		// read json file data to String
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("bgc-detail-submission.json");

		String data = IOUtils.toString(in);

		formParams.put("data", data);
		formParams.put("target", "gene_info");
		formParams.put("version", 1);
		formParams.put("bgc_id", "BGC00000");

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.URLENC).formParams(formParams).when()
				.post(this.wsPrefix + "/bgc-detail-registration").then()
				.statusCode(204).log();

	}

	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void successfulBgcDetailNrpsInfoSubmission() throws IOException {

		HashMap<String, Object> formParams = new HashMap<String, Object>();
		// read json file data to String
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("bgc-detail-submission.json");

		String data = IOUtils.toString(in);

		formParams.put("data", data);
		formParams.put("target", "nrps_info");
		formParams.put("version", 1);
		formParams.put("bgc_id", "BGC00000");

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.URLENC).formParams(formParams).when()
				.post(this.wsPrefix + "/bgc-detail-registration").then()
				.statusCode(204).log();

	}

}
