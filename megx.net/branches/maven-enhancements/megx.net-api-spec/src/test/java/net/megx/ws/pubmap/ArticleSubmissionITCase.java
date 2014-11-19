package net.megx.ws.pubmap;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.io.InputStream;

import net.megx.test.TestServer;
import net.megx.test.categories.IntegrationTest;
import net.megx.test.categories.RESTServiceTest;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;

public class ArticleSubmissionITCase {

	@Rule
	public TestServer ts = new TestServer();
	private String wsPrefix = "";

	@Before
	public void setUp() {
		this.wsPrefix = ts.getWSPrefix() + "/pubmap/v1.0.0";
	}

	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void successfulArticleSubmission() throws IOException {

		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("pubmap-article.json");

		String article = IOUtils.toString(in);

		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON).formParam("article", article)
				.when().post(this.wsPrefix + "/article").then().statusCode(200)
				.log();

	}

	@Test
	@Category({ IntegrationTest.class, RESTServiceTest.class })
	public void retrieveAllArticles() {
		given().filter(ResponseLoggingFilter.logResponseTo(System.out))
				.contentType(ContentType.JSON).get(this.wsPrefix + "/all")
				.then().statusCode(200).log();
	}

}
