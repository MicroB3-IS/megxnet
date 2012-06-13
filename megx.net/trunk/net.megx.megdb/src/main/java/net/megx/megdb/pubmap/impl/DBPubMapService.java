package net.megx.megdb.pubmap.impl;

import java.util.List;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.pubmap.PubMapService;
import net.megx.megdb.pubmap.mappers.PubMapMapper;
import net.megx.model.Article;

public class DBPubMapService extends BaseMegdbService implements PubMapService{

	@Override
	public List<Article> getAllArticles() throws Exception{
		return doInSession(new BaseMegdbService.DBTask<PubMapMapper,List<Article>>() {

			@Override
			public List<Article> execute(PubMapMapper mapper) throws Exception {
				return mapper.getAllArticles();
			}
		}, PubMapMapper.class);
	}

}
