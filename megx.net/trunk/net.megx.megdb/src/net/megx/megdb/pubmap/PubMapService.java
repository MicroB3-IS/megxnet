package net.megx.megdb.pubmap;

import java.util.List;

public interface PubMapService {
	//TODO: what if huge list, 
	// 1. return iterator instead of list 
	// 2. add arguments start and limit
	public List<PubMapArticle> getAllArticles();
}
