package net.megx.megdb;

import static org.junit.Assert.*;
import net.megx.megdb.AdminMyBatisMapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Rule;
import org.junit.Test;
import org.omg.PortableInterceptor.SUCCESSFUL;

public class DbConnectionTest {

  @Rule
  public TestDb db = new TestDb();

  @Test
  public void connect() {

    SqlSession session = db.getSqlSessionFactory().openSession();
    boolean success = false;
    try {
      AdminMyBatisMapper mapper = session.getMapper(AdminMyBatisMapper.class);
      success = mapper.pingQuery();
    } finally {
      session.close();
    }
    assertTrue(success);
  }
}
