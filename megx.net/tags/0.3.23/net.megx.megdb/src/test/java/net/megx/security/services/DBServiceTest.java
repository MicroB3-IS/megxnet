package net.megx.security.services;

import java.io.IOException;
import java.util.Properties;

import net.megx.megdb.BaseMegdbService;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBServiceTest {
	protected SqlSessionFactory sqlSessionFactory;

	public static final String MY_BATIS_PROPERTIES = "mybatis-config.properties";
	public static final String MY_BATIS_CONFIG_XML = "my-batis-config.xml";

	protected void setupSQLSessionFactory() throws IOException {
		Properties testProperties = new Properties();
		testProperties.load(Resources.getResourceAsReader(MY_BATIS_PROPERTIES));
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(Thread
				.currentThread().getContextClassLoader()
				.getResourceAsStream(MY_BATIS_CONFIG_XML));
	}

	@SuppressWarnings("unchecked")
	protected <S, T extends BaseMegdbService> S buildService(
			Class<T> implementation) throws InstantiationException,
			IllegalAccessException, IOException {
		T instance = null;
		instance = implementation.newInstance();
		if (sqlSessionFactory == null) {
			setupSQLSessionFactory();
		}
		instance.setSqlSessionFactory(sqlSessionFactory);
		return (S) instance;
	}

}
