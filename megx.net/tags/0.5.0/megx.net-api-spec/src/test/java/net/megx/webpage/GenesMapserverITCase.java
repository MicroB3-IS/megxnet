package net.megx.webpage;

import static com.jayway.restassured.RestAssured.head;
import static com.jayway.restassured.RestAssured.expect;

import net.megx.test.TestServer;
import net.megx.test.categories.AvailabilityTest;
import net.megx.test.categories.IntegrationTest;
import net.megx.test.categories.WebPageTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.experimental.categories.Category;

import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.ResponseSpecification;

public class GenesMapserverITCase {

	@Rule
	public TestServer ts = new TestServer();

	private final String GENES_MAPSERVER_PATH = "/gms";
	private Response gmsPage = null;

	@Test
	@Category(AvailabilityTest.class)
	public void genesMapserverPageAvail() {
		head(GENES_MAPSERVER_PATH).then().statusCode(200);
	}

	@Test
	@Before
	public void getGmsPage() {
		ResponseSpecification responseSpec = new ResponseSpecBuilder()
				.expectStatusCode(200).expectContentType("text/html").build();

		this.gmsPage = expect().spec(responseSpec).get(GENES_MAPSERVER_PATH);

	}

	@Test
	@Category(WebPageTest.class)
	public void mapLoaded() {

		gmsPage.then().body(
				"html.body.**.find {it.@id == 'megxMapWidget'}.@id",
				equalToIgnoringCase("megxMapWidget"));

	}

	@Test
	@Category({ WebPageTest.class })
	public void hasNecessaryJS() {
		String content = gmsPage.asString();
		assertThat(content, containsString("megxmap-0.9.1.js"));
		assertThat(content, containsString("/2.13.1/OpenLayers.js"));
		assertThat(content, containsString("/stylesheets/mapwidget.css"));
		assertThat(content,
				containsString("/gms-res/js/MegxMapWidgetLayers.js"));
		assertThat(content, containsString("biojs/registry/src/Biojs.js"));

	}
	// http://mb3is.megx.net?LAYERS=limitsoceans&FORMAT=image%2Fpng&TRANSPARENT=true&SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&STYLES=&SRS=EPSG%3A4326&BBOX=-270,-151.31868131868,270,151.31868131868&WIDTH=1365&HEIGHT=765

}
