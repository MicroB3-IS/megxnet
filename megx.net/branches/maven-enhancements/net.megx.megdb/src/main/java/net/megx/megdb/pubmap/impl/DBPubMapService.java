package net.megx.megdb.pubmap.impl;

import java.util.List;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.megdb.pubmap.PubMapService;
import net.megx.megdb.pubmap.mappers.PubMapMapper;
import net.megx.model.pubmap.Article;

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

	@Override
	public Boolean articleExists(final Integer pmid)
			throws DBGeneralFailureException {
		return doInTransaction(new DBTask<PubMapMapper, Boolean>() {

			@Override
			public Boolean execute(PubMapMapper mapper) throws Exception {
				Boolean exist;
				exist = mapper.articleExists(pmid);
				return exist;
			}
		}, PubMapMapper.class);
	}

	public List<Article> getAllArticles() throws DBGeneralFailureException,
			DBNoRecordsException {
		List<Article> result = doInSession(
				new DBTask<PubMapMapper, List<Article>>() {
					@Override
					public List<Article> execute(PubMapMapper mapper)
							throws Exception {
						return mapper.getAllArticles();
					}
				}, PubMapMapper.class);
		if (result.size() == 0) {
			throw new DBNoRecordsException("Query returned zero results");
		} else {
			return result;
		}
	}

}
