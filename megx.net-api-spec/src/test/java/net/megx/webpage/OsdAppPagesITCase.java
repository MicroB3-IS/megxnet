package net.megx.webpage;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.head;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import net.megx.test.categories.AvailabilityTest;
import net.megx.test.categories.WebPageTest;

import org.junit.Test;
import org.junit.experimental.categories.Category;

public class OsdAppPagesITCase {

    private final String APP_SAMPLES_PATH = "/osd-app/samples";  

    @Test
    @Category(AvailabilityTest.class)
    public void OsdSamplesPageAvail() {
        head(APP_SAMPLES_PATH).then().statusCode(200);
    }
    
    @Test
    @Category(WebPageTest.class)
    public void correctTitle() {
        String title = get(APP_SAMPLES_PATH).andReturn().htmlPath()
                .getString("html.head.title");
        assertThat(title, equalTo("All OSD Citizen Samples"));
    }
}
