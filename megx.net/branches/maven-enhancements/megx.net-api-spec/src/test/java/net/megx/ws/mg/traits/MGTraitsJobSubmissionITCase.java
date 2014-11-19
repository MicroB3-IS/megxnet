package net.megx.ws.mg.traits;

import java.util.HashMap;

import net.megx.test.TestServer;
import net.megx.test.categories.IntegrationTest;
import net.megx.test.categories.RESTServiceTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static com.jayway.restassured.RestAssured.given;

public class MGTraitsJobSubmissionITCase {

	@Rule
	public TestServer ts = new TestServer();

	private String wsPrefix = "";

	@Before
	public void setUp() {
		this.wsPrefix = ts.getWSPrefix() + "/mg-traits/v1.0.0";
	}

	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void testOAuthJobSubmission() {
		// given().auth().oauth("", "", "", "");
	}

	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void testNonOAuthJibSubmission() {
		HashMap<String, String> parametersMap = new HashMap<String, String>();
parametersMap.put("", value)
		given().formParameters(parametersMap).expect().when()
				.post(wsPrefix + "/jobs");
	}
}
