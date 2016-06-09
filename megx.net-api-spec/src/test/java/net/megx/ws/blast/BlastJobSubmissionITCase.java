package net.megx.ws.blast;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import net.megx.test.TestServer;
import net.megx.test.categories.IntegrationTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import io.restassured.filter.log.ResponseLoggingFilter;

public class BlastJobSubmissionITCase {

	@Rule
	public TestServer ts = new TestServer();

	// @FormParam("") final String biodbVersion,
	// @FormParam("") final String rawFasta,

	@Test
	@Category(IntegrationTest.class)
	public void successfulJobSubmission() {
		HashMap<String, Object> formParams = new HashMap<String, Object>();
		formParams.put("customer", "anonymous");
		formParams.put("numNeighbors", "2");
		formParams.put("toolLabel", "blast+");
		formParams.put("toolVer", "2.2.28");
		formParams.put("programName", "blastp");
		formParams.put("biodbLabel", "unknowns");
		formParams.put("biodbVersion", "24-02-2014_aa");
		formParams
				.put("rawFasta",
						">666"
								+ "\n"
								+ "LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL"
								+ "\n"
								+ "EWIWGGFSVDKATLNRFFAFHFILPFTMVALAGVHLTFLHETGSNNPLGLTSDSDKIPFHPYYTIKDFLG"
								+ "\n"
								+ "LLILILLLLLLALLSPDMLGDPDNHMPADPLNTPLHIKPEWYFLFAYAILRSVPNKLGGVLALFLSIVIL");

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.formParameters(formParams).when().post("/ws/v1/megx-blast/v1.0.0/jobs").then()
				.statusCode(201).log();

	}
	
	@Test
	@Category(IntegrationTest.class)
	public void retrieveMegxBlastJobData() {
		given().get("/ws/v1/megx-blast/v1.0.0/jobs/3").then().statusCode(200).log();
	}

}
