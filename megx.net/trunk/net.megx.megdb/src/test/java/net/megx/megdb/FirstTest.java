package net.megx.megdb;

import java.io.Reader;
import java.sql.Connection;

import net.megx.megdb.pubmap.mappers.PubMapMapper;
import net.megx.security.auth.model.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FirstTest {

	private static SqlSessionFactory sqlSessionFactory;

	@BeforeClass
	public static void setUp() throws Exception {
		// create a SqlSessionFactory
		Reader reader = Resources.getResourceAsReader("my-batis-config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		reader.close();
	}

	@Test
	public void shouldGetAUser() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {

		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void insertArticle() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			PubMapMapper mapper = sqlSession.getMapper(PubMapMapper.class);
//            User user = mapper.getUser(1);
//            Assert.assertEquals("User1", user.getName());

		} finally {
			sqlSession.close();
		}
	
	}

}
