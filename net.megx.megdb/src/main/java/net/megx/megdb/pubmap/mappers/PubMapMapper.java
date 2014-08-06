package net.megx.megdb.pubmap.mappers;

import net.megx.model.pubmap.Article;

public interface PubMapMapper {
	public void storeArticle(Article article);
	
	public Boolean articleExists(Integer pmid);

}
