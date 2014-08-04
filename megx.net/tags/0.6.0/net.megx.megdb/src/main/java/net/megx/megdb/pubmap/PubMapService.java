package net.megx.megdb.pubmap;

import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.model.pubmap.Article;

public interface PubMapService {

	public String storeArticle(final Article article)
			throws DBGeneralFailureException;

}
