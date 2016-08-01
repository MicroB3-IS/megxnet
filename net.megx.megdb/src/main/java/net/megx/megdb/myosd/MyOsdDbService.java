package net.megx.megdb.myosd;

import org.apache.ibatis.session.SqlSessionFactory;

public interface MyOsdDbService extends MyOsdMyBatisMapper {

  public void setSqlSessionFactory(SqlSessionFactory factory);

}
