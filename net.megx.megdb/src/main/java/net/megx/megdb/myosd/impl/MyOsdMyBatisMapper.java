package net.megx.megdb.myosd.impl;

import net.megx.megdb.myosd.MyOsdParticipantRegistration;

public interface MyOsdMyBatisMapper {

  public void saveParticipant(MyOsdParticipantRegistration participant);
  
  public void updParticipantFromJson();

  public MyOsdParticipantRegistration participantByName(String name);

  public MyOsdParticipantRegistration participantByEmail(String email);

  public MyOsdParticipantRegistration participantByMyOsdId(int myOsdId);
  
  
}
