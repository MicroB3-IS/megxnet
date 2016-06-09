package net.megx.ws.contact;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.HashMap;

import net.megx.test.TestServer;
import net.megx.test.categories.IntegrationTest;
import net.megx.test.categories.RESTServiceTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;

public class ContactSubmissionITCase {

  @Rule
  public TestServer ts = new TestServer();
  private String wsPrefix = "";
  private String path = "/store-contact";

  @Before
  public void setUp() {
    this.wsPrefix = ts.getWSPrefix() + "/contact/v1.0.0";
  }

  /**
   * Test case for successful contact form submission
   * 
   * @param email
   *          the sender email
   * @param name
   *          the sender name
   * @param comment
   *          the sender comment
   * @return Status.BAD_REQUEST if the given email,name or comment
   *         are empty or null.
   */
  @Test
  @Category({ IntegrationTest.class, RESTServiceTest.class })
  public void successfulContactSubmission() {

    HashMap<String, String> parametersMap = new HashMap<String, String>();
    parametersMap.put("email", "test@gmail.com");
    parametersMap.put("name", "Test Name");
    parametersMap.put("comment", "Test comment");

    given().filter(ResponseLoggingFilter.logResponseTo(System.out))
        .contentType(ContentType.URLENC).formParams(parametersMap).when()
        .post(this.wsPrefix + path).then().statusCode(200).log();

  }

  /**
   * Test case for contact form submission when email is null
   * 
   * @param email
   *          the sender email
   * @param name
   *          the sender name
   * @param comment
   *          the sender comment
   * @return Status.BAD_REQUEST if the given email,name or comment
   *         are empty or null.
   */
  @Test
  @Category({ IntegrationTest.class, RESTServiceTest.class })
  public void failEmailContactSumbission() {

    HashMap<String, String> parametersMap = new HashMap<String, String>();
    parametersMap.put("email", null);
    parametersMap.put("name", "Test Name");
    parametersMap.put("comment", "Test comment");

    given().filter(ResponseLoggingFilter.logResponseTo(System.out))
        .contentType(ContentType.JSON).formParams(parametersMap).when()
        .post(this.wsPrefix + path).then().statusCode(400).log();
  }

  /**
   * Test case for contact form submission when email is not provided
   * 
   * @param email
   *          the sender email
   * @param name
   *          the sender name
   * @param comment
   *          the sender comment
   * @return Status.BAD_REQUEST if the given email,name or comment
   *         are empty or null.
   */
  @Test
  @Category({ IntegrationTest.class, RESTServiceTest.class })
  public void failEmailMissingContactSumbission() {

    HashMap<String, String> parametersMap = new HashMap<String, String>();
    parametersMap.put("name", "Test Name");
    parametersMap.put("comment", "Test comment");

    given().filter(ResponseLoggingFilter.logResponseTo(System.out))
        .contentType(ContentType.JSON).formParams(parametersMap).when()
        .post(this.wsPrefix + path).then().statusCode(400).log();
  }

  /**
   * Test case for contact form submission when name is not provided
   * 
   * @param email
   *          the sender email
   * @param name
   *          the sender name
   * @param comment
   *          the sender comment
   * @return Status.BAD_REQUEST if the given email,name or comment
   *         are empty or null.
   */
  @Test
  @Category({ IntegrationTest.class, RESTServiceTest.class })
  public void failNameMissingContactSumbission() {

    HashMap<String, String> parametersMap = new HashMap<String, String>();
    parametersMap.put("email", "sender_test@mail.com");
    parametersMap.put("comment", "Test comment");

    given().filter(ResponseLoggingFilter.logResponseTo(System.out))
        .contentType(ContentType.JSON).formParams(parametersMap).when()
        .post(this.wsPrefix + path).then().statusCode(400).log();
  }
  
  /**
   * Test case for contact form submission when name is null
   * 
   * @param email
   *          the sender email
   * @param name
   *          the sender name
   * @param comment
   *          the sender comment
   * @return Status.BAD_REQUEST if the given email,name or comment
   *         are empty or null.
   */
  @Test
  @Category({ IntegrationTest.class, RESTServiceTest.class })
  public void failNameContactSumbission() {

    HashMap<String, String> parametersMap = new HashMap<String, String>();
    parametersMap.put("email", "sender_test@mail.com");
    parametersMap.put("name", null);
    parametersMap.put("comment", "Test comment");

    given().filter(ResponseLoggingFilter.logResponseTo(System.out))
        .contentType(ContentType.JSON).formParams(parametersMap).when()
        .post(this.wsPrefix + path).then().statusCode(400).log();
  }

  /**
   * Test case for contact form submission when comment is null
   * 
   * @param email
   *          the sender email
   * @param name
   *          the sender name
   * @param comment
   *          the sender comment
   * @return Status.BAD_REQUEST if the given email,name or comment
   *         are empty or null.
   */
  @Test
  @Category({ IntegrationTest.class, RESTServiceTest.class })
  public void failCommentContactSumbission() {

    HashMap<String, String> parametersMap = new HashMap<String, String>();
    parametersMap.put("email", "sender_test@mail.com");
    parametersMap.put("name", "Test Name");
    parametersMap.put("comment", null);

    given().filter(ResponseLoggingFilter.logResponseTo(System.out))
        .contentType(ContentType.JSON).formParams(parametersMap).when()
        .post(this.wsPrefix + path).then().statusCode(400).log();
  }

  /**
   * Test case for contact form submission when comment is not provided
   * 
   * @param email
   *          the sender email
   * @param name
   *          the sender name
   * @param comment
   *          the sender comment
   * @return Status.BAD_REQUEST if the given email,name or comment
   *         are empty or null.
   */
  @Test
  @Category({ IntegrationTest.class, RESTServiceTest.class })
  public void failCommentMissingContactSumbission() {

    HashMap<String, String> parametersMap = new HashMap<String, String>();
    parametersMap.put("email", "sender_test@mail.com");
    parametersMap.put("name", "Test Name");

    given().filter(ResponseLoggingFilter.logResponseTo(System.out))
        .contentType(ContentType.JSON).formParams(parametersMap).when()
        .post(this.wsPrefix + path).then().statusCode(400).log();
  }
}
