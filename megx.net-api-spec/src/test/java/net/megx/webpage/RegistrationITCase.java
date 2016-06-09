package net.megx.webpage;

import static io.restassured.RestAssured.head;
import static io.restassured.RestAssured.given;

import java.util.HashMap;

import net.megx.test.TestServer;
import net.megx.test.categories.AvailabilityTest;
import net.megx.test.categories.IntegrationTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.ResponseSpecification;

public class RegistrationITCase {

	@Rule
	public TestServer ts = new TestServer();

	private ResponseSpecification successfulRegistrationResponse;
	private ResponseSpecification emptyRegistrationResponse;
	
	@Before
	public void setup() {
		ResponseSpecBuilder builder = new ResponseSpecBuilder();
		builder.expectStatusCode(200);
		this.successfulRegistrationResponse = builder.build();

		builder = new ResponseSpecBuilder();
		builder.expectStatusCode(409);
		this.emptyRegistrationResponse = builder.build();

	}
	
	@Test
	@Category(AvailabilityTest.class)
	public void registrationPageAvail() {
		head("/register").then().assertThat().statusCode(200);
	}

	@Test
	@Category(IntegrationTest.class)
	public void postRegistrationAllParamsEmpty() {
		HashMap<String, Object> formParams = new HashMap<String, Object>();
		formParams.put("challenge", "");
		formParams.put("response", "");
		formParams.put("logname", "");
		formParams.put("firstName", "");
		formParams.put("lastName", "");
		formParams.put("email", "");
		formParams.put("initials", "");
		formParams.put("pass", "");


		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.formParameters(formParams).when().post("/ws/register").then()
				.spec(this.emptyRegistrationResponse ).log();

	}

	@Test
	@Category(IntegrationTest.class)
	public void successfulRegistrationMinimalUser() {
		HashMap<String, Object> formParams = new HashMap<String, Object>();
		formParams.put("challenge", "");
		formParams.put("response", "");
		formParams.put("logname", "testit");
		formParams.put("firstName", "");
		formParams.put("lastName", "");
		formParams.put("email", "test@test.de");
		formParams.put("initials", "");
		formParams.put("pass", "pass");


		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.formParameters(formParams).when().post("/ws/register").then()
				.spec(this.successfulRegistrationResponse).log();

	}

	
}
