package net.megx.megdb;

import static org.junit.Assert.*;
import net.megx.megdb.myosd.MyOsdDbService;
import net.megx.megdb.myosd.MyOsdParticipantRegistration;
import net.megx.megdb.myosd.dto.MyOsdParticipantDTOImpl;
import net.megx.megdb.myosd.impl.MyOsdDbServiceImpl;

import org.apache.ibatis.exceptions.PersistenceException;
import org.junit.Rule;
import org.junit.Test;

public class MyOsdDbServiceTest {

  @Rule
  public TestDb db = new TestDb();

  @Test(expected=PersistenceException.class)
  public void saveEmptyParticpant() {

    MyOsdParticipantRegistration participant = new MyOsdParticipantDTOImpl();

    MyOsdDbService service = new MyOsdDbServiceImpl();
    service.setSqlSessionFactory(db.getSqlSessionFactory());

    service.saveParticipant(participant);
  }

  @Test
  public void saveMinimumParticpant() {

    MyOsdParticipantRegistration participant = new MyOsdParticipantDTOImpl();
    participant.setEmail("test@test.de");
    participant.setRawJson("{}");
    int v = 1;
    participant.setVersion(v);
    int num = 271;
    participant.setMyOsdId(num);
    
    MyOsdDbService service = new MyOsdDbServiceImpl();
    service.setSqlSessionFactory(db.getSqlSessionFactory());

    service.saveParticipant(participant);
    
    assertTrue(true);
    
  }
}
