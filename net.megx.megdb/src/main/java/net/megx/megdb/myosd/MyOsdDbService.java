package net.megx.megdb.myosd;

import org.apache.ibatis.session.SqlSessionFactory;

public interface MyOsdDbService {

  public void setSqlSessionFactory(SqlSessionFactory factory);
  
  public void saveParticipant(MyOsdParticipantRegistration participant);
  
  public MyOsdParticipantRegistration participantByName(String name);
  
  public MyOsdParticipantRegistration participantByEmail(String email);
  
  public MyOsdParticipantRegistration participantByMyOsdId(int myOsdId);
  
}
