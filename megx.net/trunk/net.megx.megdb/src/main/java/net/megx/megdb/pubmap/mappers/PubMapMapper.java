package net.megx.megdb.pubmap.mappers;

import java.util.List;

import net.megx.model.pubmap.Article;

public interface PubMapMapper {
	public void storeArticle(Article article);
	
	public Boolean articleExists(Integer pmid);
	
	public List<Article> getAllArticles();

}
