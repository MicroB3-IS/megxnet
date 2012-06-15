/**
 * 
 */
package net.megx.model;

import java.util.ArrayList;
import java.util.List;

import net.megx.model.impl.Gender;
import net.megx.model.impl.PubMapArticle;
import net.megx.model.impl.PubMapAuthor;
import net.megx.model.impl.PubMapJournal;

/**
 * @author rkottman
 *
 */
public class ModelMockFactory extends ModelFactory {

	
	public static Article createArticle() {
		Article art = new PubMapArticle();
		art.setTitle("Super Article Title");
		art.setDOI("huhu-doi/10.journal.article");
		Journal j = new PubMapJournal();
		j.setTitle("petrational geographic");
		
		Author author = new PubMapAuthor();
		author.setFirstName("Renzo");
		author.setLastName("Kottmann");
		author.setSex(Gender.MALE.toString());
		
		art.addAuthor(author);
		art.addJournal(j);
		
		return art; 
	}
	
	public static List<Article> createArticleList() {
		List<Article> articles = new ArrayList<Article>();
		articles.add(createArticle());
		return articles;
		
	}
	
}
