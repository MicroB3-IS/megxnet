package net.megx.megdb.pubmap.mappers;

import java.util.List;

import net.megx.model.pubmap.Article;
import net.megx.model.pubmap.Ocean;

public interface PubMapMapper {
	public void storeArticle(Article article);
	
	public List<Article> getAllArticles();
	
	public Boolean isOcean(String worldRegion);
	
	public Ocean getOceanByName(String oceanName);
	
	public List<Article> getArticlesByPmid(int pmid);

}
