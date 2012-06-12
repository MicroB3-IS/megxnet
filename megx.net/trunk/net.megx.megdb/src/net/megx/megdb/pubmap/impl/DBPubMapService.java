package net.megx.megdb.pubmap.impl;

import java.util.ArrayList;
import java.util.List;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.pubmap.PubMapArticle;
import net.megx.megdb.pubmap.PubMapService;

public class DBPubMapService extends BaseMegdbService implements PubMapService{

	@Override
	public List<PubMapArticle> getAllArticles() {
		//TODO: implement db call
		List<PubMapArticle> rv = new ArrayList<PubMapArticle>();
		PubMapArticle a = new PubMapArticle();
		a.setTitle("Mock article 1");
		 // ...
		rv.add(a);
		return rv;
	}

}
