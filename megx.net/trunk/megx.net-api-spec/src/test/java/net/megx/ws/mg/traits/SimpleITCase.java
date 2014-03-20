/**
 * 
 */
package net.megx.ws.mg.traits;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author rkottman
 * 
 */
public class SimpleITCase {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		RestAssured.baseURI = "http://mb3is.megx.net";
		RestAssured.port = 80;
		RestAssured.basePath = "/";
		// RestAssured.rootPath = "x.y.z";
	}

	@Test
	public void homePageAvail() {
		get("/").then().statusCode(200);
		get("/").then().using().parser("text/html", Parser.HTML);
		String title = get("/").htmlPath().getString("html.head.title");
		assertThat(title, equalTo("Megx.Net Home"));

	}
}
