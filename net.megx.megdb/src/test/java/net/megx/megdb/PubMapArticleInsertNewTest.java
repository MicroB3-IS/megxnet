package net.megx.megdb;

import net.megx.model.Article;
import net.megx.model.ModelMockFactory;

import org.junit.Before;
import org.junit.Test;

public class PubMapArticleInsertNewTest extends PubMapServiceTestBase {

	Article article = null;

	@Before
	public void setUpObject() throws Exception {
		article = ModelMockFactory.createArticle();
	}

	@Test
	public void insertAuthor() {
		pms.insertAuthor(article.getAuthor(0));

	}

	
	@Test
	public void insertArticle() {
		pms.insertArticle(article);

	}

}
