package net.megx.ws.osd.registry;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import net.megx.test.TestServer;
import net.megx.test.categories.IntegrationTest;
import net.megx.test.categories.RESTServiceTest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;

public class OSDSampleSubmissionITCase {

    @Rule
    public TestServer ts = new TestServer();
    private String wsPrefix = "";

    @Before
    public void setUp() {
        this.wsPrefix = ts.getWSPrefix() + "/OSDRegistry/v1.0.0";
    }

    @Test
    @Category({ IntegrationTest.class, RESTServiceTest.class })
    public void successfulOSDSampleSubmission() throws IOException {

        // read json file data to String
        InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream("osd-registry-sample.json");

        String json = IOUtils.toString(in);
        // create ObjectMapper instance
//        ObjectMapper jsonMapper = new ObjectMapper();

        // convert json string to object
//        Map<String, Object> data = jsonMapper.readValue(in, Map.class);

        given().filter(ResponseLoggingFilter.logResponseTo(System.out))
                .contentType(ContentType.URLENC).formParam("json", json)
                // .formParameters()
                .when().post(this.wsPrefix + "/sample").then().statusCode(201)
                .log();

    }

}
