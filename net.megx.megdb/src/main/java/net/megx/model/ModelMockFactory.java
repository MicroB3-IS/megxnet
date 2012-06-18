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
import net.megx.model.impl.PubMapSample;

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

		Author author2 = new PubMapAuthor();
		author2.setFirstName("Petra");
		author2.setLastName("Pop Ristova");
		author2.setSex(Gender.FEMALE.toString());

		Sample sample = new PubMapSample();
		sample.setLabel("Joco");
		sample.setStudy("unknown");

		art.addAuthor(author2);
		art.addJournal(j);
		art.addSample(sample);

		return art;
	}
/*
	public static Article createArticleFromJSON() {
		Gson gson = new Gson();
		InputStream is = Test.class
				.getResourceAsStream("/net/megx/pubmap/test/a.js");
		
		String json = null;
		
		try {
	        json = new java.util.Scanner(is,"UTF-8").useDelimiter("\\A").next();
	    } catch (java.util.NoSuchElementException e) {
	      json = ""; 
	    }

		// DAO article from json
		ArticleDTO dto = gson.fromJson(json, ArticleDTO.class);
		Article a = dto.toArticle();
		// we have the article, print it
		return a;
	}
 */

	public static List<Article> createArticleList() {
		List<Article> articles = new ArrayList<Article>();
		articles.add(createArticle());
		return articles;

	}
}
