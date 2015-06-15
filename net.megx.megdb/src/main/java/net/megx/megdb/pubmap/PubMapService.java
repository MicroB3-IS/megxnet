package net.megx.megdb.pubmap;

import java.sql.SQLException;
import java.util.List;

import net.megx.model.Article;
import net.megx.model.Author;
import net.megx.model.Journal;

public interface PubMapService {
	//TODO: what if huge list, 
	// 1. return iterator instead of list 
	// 2. add arguments start and limit
	public List<Article> getAllArticles() throws Exception;
	public Article selectArticleDetailsById(String id, String idCode);
	public int insertArticle(Article article) throws SQLException;
//	public int insertArticleOnly(Article article);
	public int insertJournal(Journal journal);
	
	public int deleteArticle(Article article);
	int insertAuthor(Author author);
}
