package net.megx.webpage;

import static com.jayway.restassured.RestAssured.head;

import net.megx.test.TestServer;
import net.megx.test.categories.AvailabilityTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class LoginITCase extends TestServer {

	@Rule public TestServer ts = new TestServer();
	
	@Test
	@Category(AvailabilityTest.class)
	public void loginPageAvail() {
		head("/login").then().assertThat().statusCode(200);
	}

}
