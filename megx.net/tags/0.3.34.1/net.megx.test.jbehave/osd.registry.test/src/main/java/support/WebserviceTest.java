package support;

import junit.framework.Assert;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import static com.jayway.restassured.RestAssured.expect;

public class WebserviceTest {
	
	private Response response;	
	
	public WebserviceTest(){
		
		RestAssured.baseURI = "";
		RestAssured.basePath = "/rest";				
	}

	public void pageAvailablilityRequest(String pagePath){		
		
		RestAssured.baseURI = System.getProperty("base.url");
		expect().statusCode(200).when().get(RestAssured.baseURI + pagePath);	
		
	}
	public void sendFormDataRequest(String storeDataPath){			
		
		RestAssured.baseURI = System.getProperty("base.url");		
		response = expect().statusCode(201).when().post(storeDataPath);
		
	}
	public void ResponseMessageTest(String expectedMessage){		
		
		Assert.assertEquals(expectedMessage, response.getBody().asString()); 		
	}
}
