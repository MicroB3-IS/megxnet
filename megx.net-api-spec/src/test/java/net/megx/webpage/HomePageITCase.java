/**
 * 
 */
package net.megx.webpage;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import net.megx.test.TestServer;
import net.megx.test.categories.AvailabilityTest;
import net.megx.test.categories.WebPageTest;

import org.junit.Test;

import org.junit.Rule;
import org.junit.experimental.categories.Category;

/**
 * @author rkottman
 * 
 */
public class HomePageITCase {

  @Rule
  public TestServer ts = new TestServer();

  @Test
  @Category(AvailabilityTest.class)
  public void homePageAvail() {
    head("/").then().statusCode(200);
  }

  @Test
  @Category(WebPageTest.class)
  public void correctTitle() {
    String title = get("/").andReturn().htmlPath().getString("html.head.title");
    assertThat(title, equalTo("Micro B3 Information System"));
  }

}
