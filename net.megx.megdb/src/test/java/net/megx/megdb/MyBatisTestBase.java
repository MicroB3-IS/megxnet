package net.megx.megdb;

import java.io.Reader;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;

public class MyBatisTestBase {

	static SqlSessionFactory sqlSessionFactory;
//	static PubMapService pms;

	@BeforeClass
	public static void setUp() throws Exception {
		// create a SqlSessionFactory
		Reader reader = Resources.getResourceAsReader("my-batis-config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		reader.close();
//		DBPubMapService dbpms = new DBPubMapService();
//		dbpms.setSqlSessionFactory(sqlSessionFactory);
//		// cast to interface for better testing
//		pms = (PubMapService) dbpms;
	}

}
