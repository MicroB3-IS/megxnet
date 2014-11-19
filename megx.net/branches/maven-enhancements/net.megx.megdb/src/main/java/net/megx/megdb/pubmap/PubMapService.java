package net.megx.megdb.pubmap;

import java.util.List;

import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.model.pubmap.Article;

public interface PubMapService {

	public String storeArticle(final Article article)
			throws DBGeneralFailureException;

	public Boolean articleExists(final Integer pmid)
			throws DBGeneralFailureException;

	public List<Article> getAllArticles() throws DBGeneralFailureException,
			DBNoRecordsException;

}
