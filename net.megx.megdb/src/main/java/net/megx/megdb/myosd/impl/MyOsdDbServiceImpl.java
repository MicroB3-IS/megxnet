package net.megx.megdb.myosd.impl;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.myosd.MyOsdDbService;
import net.megx.megdb.myosd.MyOsdParticipantRegistration;

public class MyOsdDbServiceImpl extends BaseMegdbService implements MyOsdDbService {

  
    
  public void saveParticipant(MyOsdParticipantRegistration participant) {
    SqlSession session = super.sessionFactory.openSession();
    try {
      MyOsdMyBatisMapper mapper = session.getMapper(MyOsdMyBatisMapper.class);
      mapper.saveParticipant(participant);
      session.commit();
    } finally {
      session.close();
    }
  }

}
