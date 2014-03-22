package net.megx.webpage;

import static com.jayway.restassured.RestAssured.head;

import net.megx.test.TestServer;
import net.megx.test.categories.AvailabilityTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RegistrationITCase {

	@Rule public TestServer ts = new TestServer();
	
	@Test
	@Category(AvailabilityTest.class)
	public void registrationPageAvail() {
		head("/register").then().assertThat().statusCode(200);
	}
	
	

}
