package net.megx.megdb;

import net.megx.megdb.pubmap.PubMapService;
import net.megx.megdb.pubmap.impl.DBPubMapService;

import org.junit.BeforeClass;

public class PubMapServiceTestBase extends MyBatisTestBase {

	static PubMapService pms;
	
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

}