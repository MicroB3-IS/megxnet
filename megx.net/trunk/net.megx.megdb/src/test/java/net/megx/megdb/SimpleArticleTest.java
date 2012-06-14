package net.megx.megdb;

import java.io.Reader;
import java.sql.Connection;
import java.util.List;

import net.megx.megdb.pubmap.PubMapService;
import net.megx.megdb.pubmap.impl.DBPubMapService;
import net.megx.megdb.pubmap.mappers.PubMapMapper;
import net.megx.model.Article;
import net.megx.model.Journal;
import net.megx.model.ModelFactory;
import net.megx.model.impl.PubMapArticle;
import net.megx.model.impl.PubMapJournal;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

public class SimpleArticleTest {

	private static SqlSessionFactory sqlSessionFactory;
	private static PubMapService pms;

	Article art = new PubMapArticle();

	@BeforeClass
	public static void setUp() throws Exception {
		// create a SqlSessionFactory
		Reader reader = Resources.getResourceAsReader("my-batis-config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		reader.close();
		pms = new DBPubMapService();

	}

	@Before
	public void setUpObject() throws Exception {

		art.setTitle("articele Title");
		art.setDOI("doi/10.journal.article");
		Journal j = new PubMapJournal();
		j.setTitle("petrational geographic");
		art.addJournal(j);

	}

	@Test
	public void insertSimpleArticle() {
		SqlSession sqlSession = sqlSessionFactory.openSession(false);

		try {
			PubMapMapper mapper = sqlSession.getMapper(PubMapMapper.class);
			mapper.insertJournalSelective(art.getJournal());
			mapper.addArticle(art);

			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void insertArticle() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			PubMapMapper mapper = sqlSession.getMapper(PubMapMapper.class);
			// User user = mapper.getUser(1);
			// Assert.assertEquals("User1", user.getName());

		} finally {
			sqlSession.close();
		}

	}

}
