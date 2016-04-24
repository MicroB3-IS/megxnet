package net.megx.megdb;

import java.io.IOException;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.rules.ExternalResource;

import static org.junit.Assert.fail;

public class TestDb extends ExternalResource {

  private SqlSessionFactory factory = null;

 private final String MYBATIS_PROPERTIES = "mybatis-config.properties";
  private final String MYBATIS_CONFIG_XML = "my-batis-config.xml";

  @Override
  public void before() throws ClassNotFoundException, IOException {
    Class<?> pgDriver = null;
    ;
    try {
      pgDriver = Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    if (pgDriver == null) {
      fail("org.postgresql.Driver not found!!!");
    }
    Properties testProperties = new Properties();
    testProperties.load(Resources.getResourceAsReader(MYBATIS_PROPERTIES));
    
    System.out.println(testProperties.toString());

    factory = new SqlSessionFactoryBuilder().build(Resources
        .getResourceAsReader(MYBATIS_CONFIG_XML), testProperties);
  }

  public SqlSessionFactory getSqlSessionFactory() {
    return this.factory;
  }
  
  @Override
  protected void after() {
    super.after();
  }

}
