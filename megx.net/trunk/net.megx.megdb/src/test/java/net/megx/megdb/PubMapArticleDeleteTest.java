package net.megx.megdb;


import org.junit.Test;

public class PubMapArticleDeleteTest extends PubMapServiceTestBase {

	@Test
	public void deleteArticle() {
		pms.deleteArticle(article);

	}

}
