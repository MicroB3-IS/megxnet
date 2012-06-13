package net.megx.megdb.pubmap;

import java.util.List;
import net.megx.model.Article;

public interface PubMapService {
	//TODO: what if huge list, 
	// 1. return iterator instead of list 
	// 2. add arguments start and limit
	public List<Article> getAllArticles() throws Exception;
}
