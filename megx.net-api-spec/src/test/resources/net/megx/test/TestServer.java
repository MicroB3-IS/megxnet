package net.megx.test;

import org.junit.rules.ExternalResource;

import com.jayway.restassured.RestAssured;

/**
 * JUnit Rule to set-up the remote HTTP Server configuration against which the
 * tests run as for RESTAssured.
 * 
 * TODO: get configuration from the environment
 * 
 * @author rkottman
 * 
 */
public class TestServer extends ExternalResource {

    private final String WS_PREFIX = "/ws/v1";
    
	@Override
	public void before() {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "${mvn.test.server}";
		RestAssured.port = ${mvn.test.port};
		RestAssured.basePath = "${mvn.test.base.path}";
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		// RestAssured.rootPath = "x.y.z";
	}

	
	
	public String getWSPrefix() {
	    return WS_PREFIX;
	}
	
}
