package net.megx.megdb;

 
import org.junit.Test;

public class PubMapArticleInsertNewTest extends PubMapServiceTestBase {

	@Test
	public void insertAuthor() {
		pms.insertAuthor(article.getAuthor(0));

	}
	
	@Test
	public void insertArticle() {
		//pms.insertArticle(article);

	}

}
