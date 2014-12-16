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
		this.wsPrefix = ts.getWSPrefix() + "/mibig";
	}
	
	/**
	 *  Test case for: store MIBIG annotation form successful.
	 *  @param json the json provided from registration form.
	 *  @param version the form version number
	 */
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
				.post(this.wsPrefix + "/v1.0.0/bgc-registration").then()
				.statusCode(200).log();

	}
	
	/**
	 *  Test case for: store MIBIG annotation form when json not provided.
	 *  @param json the json provided from registration form.
	 *  @param version the form version number
	 */
	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class})
	public void failMibigSubmission(){
		
		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
		.contentType(ContentType.URLENC).formParam("json", "")
		.formParam("version", "1").when()
		.post(this.wsPrefix + "/v1.0.0/bgc-registration").then()
		.statusCode(400).log();
	}
	
	/**
	 *  Test case for: store MIBIG annotation form when version parameter not provided.
	 *  @param json the json provided from registration form.
	 *  @param version the form version number
	 */
	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class})
	public void failVersionMibigSubmission() throws IOException{
		
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("mibig-submission.json");
		
		String json = IOUtils.toString(in);
		
		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
		.contentType(ContentType.URLENC).formParam("json", json)
		.formParam("version", "").when()
		.post(this.wsPrefix + "/v1.0.0/bgc-registration").then()
		.statusCode(400).log();
	}
	
	/**
	 *  Test case for: submit gene_info successful
	 *  @param data the form data 
	 *  @param target the target table where data should be store
	 *  @param version the form version number
	 *  @param bgc_id the id for bgc data
	 */
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
				.post(this.wsPrefix + "/v2.0.0/bgc-detail-registration").then()
				.statusCode(204).log();

	}
	
	/**
	 *  Test case for: submit nrps_info successful
	 *  @param data the form data 
	 *  @param target the target table where data should be store
	 *  @param version the form version number
	 *  @param bgc_id the id for bgc data
	 */
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
				.post(this.wsPrefix + "/v2.0.0/bgc-detail-registration").then()
				.statusCode(204).log();

	}
	
	/**
	 *  Test case for: submit nrps_info when data not provided
	 *  @param data the form data 
	 *  @param target the target table where data should be store
	 *  @param version the form version number
	 *  @param bgc_id the id for bgc data
	 */
	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void failBgcDetailDataInfoSubmission() throws IOException {

		HashMap<String, Object> formParams = new HashMap<String, Object>();

		formParams.put("data", "");
		formParams.put("target", "nrps_info");
		formParams.put("version", 1);
		formParams.put("bgc_id", "BGC00001");

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.URLENC).formParams(formParams).when()
				.post(this.wsPrefix + "/v2.0.0/bgc-detail-registration").then()
				.statusCode(400).log();

	}
	
	/**
	 *  Test case for: submit data when target not provided
	 *  @param data the form data 
	 *  @param target the target table where data should be store
	 *  @param version the form version number
	 *  @param bgc_id the id for bgc data
	 */
	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void failBgcDetailTargetInfoSubmission() throws IOException {

		HashMap<String, Object> formParams = new HashMap<String, Object>();
		// read json file data to String
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("bgc-detail-submission.json");

		String data = IOUtils.toString(in);

		formParams.put("data", data);
		formParams.put("target", "");
		formParams.put("version", 1);
		formParams.put("bgc_id", "BGC00001");

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.URLENC).formParams(formParams).when()
				.post(this.wsPrefix + "/v2.0.0/bgc-detail-registration").then()
				.statusCode(400).log();

	}
	


}
