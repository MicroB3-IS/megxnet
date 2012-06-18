package net.megx.megdb;

import net.megx.megdb.pubmap.PubMapService;
import net.megx.megdb.pubmap.impl.DBPubMapService;
import net.megx.model.Article;

import org.junit.Before;
import org.junit.BeforeClass;

public class PubMapServiceTestBase extends MyBatisTestBase {

	static PubMapService pms;
	protected Article article = null;
	
	@BeforeClass
	public static void setUpInsertTest() throws Exception {
		DBPubMapService dbpms = new DBPubMapService();
		dbpms.setSqlSessionFactory(sqlSessionFactory);
	    // cast to interface for better testing
		pms = (PubMapService) dbpms;
	}
 
	

	public PubMapServiceTestBase() {
		super();
	}

	@Before
	public void setUpObject() throws Exception {
		//article = ModelMockFactory.createArticleFromJSON();
	}

}