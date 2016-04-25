package net.megx.megdb.myosd;

import org.apache.ibatis.session.SqlSessionFactory;

public interface MyOsdDbService {

  public void setSqlSessionFactory(SqlSessionFactory factory);
  
  public void saveParticipant(MyOsdParticipantRegistration participant);
  
  
}
