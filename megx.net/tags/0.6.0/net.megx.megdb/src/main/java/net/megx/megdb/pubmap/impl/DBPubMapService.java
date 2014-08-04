package net.megx.megdb.pubmap.impl;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.exceptions.DBGeneralFailureException;
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

}
