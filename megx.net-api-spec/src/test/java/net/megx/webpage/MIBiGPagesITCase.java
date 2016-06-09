package net.megx.webpage;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.containsString;
import net.megx.test.TestServer;
import net.megx.test.categories.WebPageTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MIBiGPagesITCase {

	private final String MIBIG_PATH = "/mibig/bgc-registration";

	@Rule
	public TestServer ts = new TestServer();

	@Test
	@Category(WebPageTest.class)
	public void containAlpacaLibrary() {
		get(MIBIG_PATH)
				.then()
				.assertThat()
				.body(containsString("/net.megx.form-widget/js/alpaca-full-1.1.3.min.js"));

	}

	@Test
	@Category(WebPageTest.class)
	public void containAlpacaStyle() {
		get(MIBIG_PATH)
				.then()
				.assertThat()
				.body(containsString("/net.megx.form-widget/css/alpaca-1.1.3.css"));

	}

	@Test
	@Category(WebPageTest.class)
	public void containAlpacaJQuery() {
		get(MIBIG_PATH)
				.then()
				.assertThat()
				.body(containsString("/net.megx.form-widget/js/jquery.tmpl-1.0.0pre.js"));

	}

	@Test
	@Category(WebPageTest.class)
	public void containMegxFormWidget() {
		get(MIBIG_PATH)
				.then()
				.assertThat()
				.body(containsString("/net.megx.form-widget/js/megx-form-widget-0.1.min.js"));

	}

}
