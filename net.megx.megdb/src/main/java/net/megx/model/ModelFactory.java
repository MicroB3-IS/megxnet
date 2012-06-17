/**
 * 
 */
package net.megx.model;

import net.megx.model.impl.PubMapArticle;
import net.megx.model.impl.PubMapAuthor;
import net.megx.model.impl.PubMapJournal;

/**
 * @author rkottman
 *
 */
public class ModelFactory {

	
	public static Article createArticle() {
		PubMapArticle article = new PubMapArticle();
		
		return article;
	}
	
	public static Author createAuthor() {
		return new PubMapAuthor();
	}

	public static Journal createJournal() {
		return new PubMapJournal();
	}
}
