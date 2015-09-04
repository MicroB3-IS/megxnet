package net.megx.megdb.pubmap;

import java.util.List;

import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.model.pubmap.Article;
import net.megx.model.pubmap.Ocean;

public interface PubMapService {

  public String storeArticle(final Article article)
      throws DBGeneralFailureException;

  public List<Article> getAllArticles() throws DBGeneralFailureException,
      DBNoRecordsException;

  public Boolean isOcean(final String worldRegion)
      throws DBGeneralFailureException;

  public Ocean getOceanByName(final String oceanName)
      throws DBGeneralFailureException, DBNoRecordsException;

  public List<Article> getArticlesByPmid(final int pmid)
      throws DBGeneralFailureException;

}
