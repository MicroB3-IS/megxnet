package net.megx.megdb.pubmap.impl;

import java.util.List;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.megdb.pubmap.PubMapService;
import net.megx.megdb.pubmap.mappers.PubMapMapper;
import net.megx.model.pubmap.Article;
import net.megx.model.pubmap.Ocean;

public class DBPubMapService extends BaseMegdbService implements PubMapService {

  @Override
  public String storeArticle(final Article article)
      throws DBGeneralFailureException {
    return doInTransaction(new DBTask<PubMapMapper, String>() {

      @Override
      public String execute(PubMapMapper mapper) throws Exception {
        String savedArticle = "";
        mapper.storeArticle(article);
        savedArticle = article.getMegxBarJSON();
        return savedArticle;
      }
    }, PubMapMapper.class);

  }

  public List<Article> getAllArticles() throws DBGeneralFailureException,
      DBNoRecordsException {
    List<Article> result = doInSession(
        new DBTask<PubMapMapper, List<Article>>() {
          @Override
          public List<Article> execute(PubMapMapper mapper) throws Exception {
            return mapper.getAllArticles();
          }
        }, PubMapMapper.class);
    if (result.size() == 0) {
      throw new DBNoRecordsException("Query returned zero results");
    } else {
      return result;
    }
  }

  @Override
  public Boolean isOcean(final String worldRegion)
      throws DBGeneralFailureException {
    return doInTransaction(new DBTask<PubMapMapper, Boolean>() {

      @Override
      public Boolean execute(PubMapMapper mapper) throws Exception {
        Boolean isOcean;
        isOcean = mapper.isOcean(worldRegion);
        return isOcean;
      }
    }, PubMapMapper.class);
  }

  public Ocean getOceanByName(final String oceanName)
      throws DBGeneralFailureException, DBNoRecordsException {
    Ocean ocean = doInSession(new DBTask<PubMapMapper, Ocean>() {
      @Override
      public Ocean execute(PubMapMapper mapper) throws Exception {
        return mapper.getOceanByName(oceanName);
      }
    }, PubMapMapper.class);
    if (ocean == null) {
      throw new DBNoRecordsException("Query returned zero results");
    } else {
      return ocean;
    }
  }

  public List<Article> getArticlesByPmid(final int pmid)
      throws DBGeneralFailureException {
    List<Article> result = doInSession(
        new DBTask<PubMapMapper, List<Article>>() {
          @Override
          public List<Article> execute(PubMapMapper mapper) throws Exception {
            return mapper.getArticlesByPmid(pmid);
          }
        }, PubMapMapper.class);

    return result;

  }
}
