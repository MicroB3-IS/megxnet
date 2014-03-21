/**
 * 
 */
package net.megx.webpage;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import net.megx.test.categories.AvailabilityTest;
import net.megx.test.categories.WebPageTest;

import org.junit.Before;
import org.junit.Test;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

/**
 * @author rkottman
 * 
 */
public class HomePageITCase {

	
	@Before
	public void setUp() throws Exception {
		RestAssured.baseURI = "http://mb3is.megx.net";
		RestAssured.port = 80;
		RestAssured.basePath = "/";
		// RestAssured.rootPath = "x.y.z";
	}

	@Test
	@Category(AvailabilityTest.class)
	public void homePageAvail() {
		get("/").then().statusCode(200);
	}
	
	@Test
	@Category(WebPageTest.class)
	public void correctTitle() {
		get("/").then().using().parser("text/html", Parser.HTML);
		String title = get("/").htmlPath().getString("html.head.title");
		assertThat(title, equalTo("Micro B3 Information System"));
	}
	
}
