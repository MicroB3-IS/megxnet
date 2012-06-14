/**
 * 
 */
package net.megx.model;

import net.megx.model.impl.PubMapArticle;

/**
 * @author rkottman
 *
 */
public class ModelFactory {

	
	public static Article createArticle() {
		PubMapArticle article = new PubMapArticle();
		
		return article;
	}
	
}
