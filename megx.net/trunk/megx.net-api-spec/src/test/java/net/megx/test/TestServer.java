package net.megx.test;

import org.junit.rules.ExternalResource;

import com.jayway.restassured.RestAssured;

/**
 * JUnit Rule to set-up the remote HTTP Server configuration against which the
 * tests run as for RESTAss.
 * 
 * TODO: get configuration from the environment
 * 
 * @author rkottman
 * 
 */
public class TestServer extends ExternalResource {

	@Override
	public void before() {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "http://mb3is.megx.net";
		RestAssured.port = 80;
		RestAssured.basePath = "/";
		// RestAssured.rootPath = "x.y.z";
	}

}
