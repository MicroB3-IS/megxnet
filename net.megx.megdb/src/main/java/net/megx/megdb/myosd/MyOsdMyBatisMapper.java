package net.megx.megdb.myosd;

import java.util.List;

public interface MyOsdMyBatisMapper {

  public void saveParticipant(MyOsdParticipantRegistration participant);

  //public void updParticipantFromJson();

  public MyOsdParticipantRegistration participantByName(String name);

  public MyOsdParticipantRegistration participantByEmail(String email);

  public MyOsdParticipantRegistration participantByMyOsdId(int myOsdId);

  public void saveSample(MyOsdSample sample);

  public MyOsdSample sampleByMyOsdId(MyOsdSample sample);
  public List<MyOsdSample> getSamples();



}
