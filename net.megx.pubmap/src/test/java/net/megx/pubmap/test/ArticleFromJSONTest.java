package net.megx.pubmap.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import net.megx.model.Article;
import net.megx.pubmap.rest.json.ArticleDTO;

import org.chon.core.common.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

public class ArticleFromJSONTest {
	private static Gson gson = new Gson();
	
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	public static Article createArticleFromJSON() throws IOException {
		String jsonStr = readResource("/net/megx/pubmap/test/article.json");
		ArticleDTO dto = gson.fromJson(jsonStr, ArticleDTO.class);
		
		Article article = dto.toArticle();
		return article;
	}
	
	@Test
	public void articleFromJSON() throws Exception {
		String jsonStr = readResource("/net/megx/pubmap/test/article.json");
		ArticleDTO dto = gson.fromJson(jsonStr, ArticleDTO.class);
		
		Article article = dto.toArticle();
		assertNotNull(article);
		//website mapped to fullTextHTML
		assertEquals(dto.getWebsite(), article.getFullTextHTML());
		
	}
	
	private static String readResource(String cpRes) throws IOException {
		InputStream is = ArticleFromJSONTest.class.getResourceAsStream(cpRes);
		String s = FileUtils.readInputStreamToString(is);
		
		return s;
	}
}
