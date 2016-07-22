package net.megx.megdb.myosd.impl;


import org.apache.ibatis.session.SqlSession;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.myosd.MyOsdDbService;
import net.megx.megdb.myosd.MyOsdParticipantRegistration;
import net.megx.megdb.myosd.MyOsdSample;

public class MyOsdDbServiceImpl extends BaseMegdbService implements MyOsdDbService {

  public void saveSample(MyOsdSample sample) {
    SqlSession session = super.sessionFactory.openSession();
    try {
      MyOsdMyBatisMapper mapper = session.getMapper(MyOsdMyBatisMapper.class);
      mapper.saveSample(sample);
      session.commit();
    } finally {
      session.close();
    }
  }

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

  @Override
  public MyOsdParticipantRegistration participantByName(String name) {
    SqlSession session = super.sessionFactory.openSession();
    MyOsdParticipantRegistration p = null;
    try {
      MyOsdMyBatisMapper mapper = session.getMapper(MyOsdMyBatisMapper.class);
      p = mapper.participantByName(name);
      session.commit();
    } finally {
      session.close();
    }
    return p;
  }

  @Override
  public MyOsdParticipantRegistration participantByEmail(String email) {
    SqlSession session = super.sessionFactory.openSession();
    MyOsdParticipantRegistration p = null;
    try {
      MyOsdMyBatisMapper mapper = session.getMapper(MyOsdMyBatisMapper.class);
      p = mapper.participantByEmail(email);
      session.commit();
    } finally {
      session.close();
    }
    return p;
  }

  @Override
  public MyOsdParticipantRegistration participantByMyOsdId(int myOsdId) {
    SqlSession session = super.sessionFactory.openSession();
    MyOsdParticipantRegistration p = null;
    try {
      MyOsdMyBatisMapper mapper = session.getMapper(MyOsdMyBatisMapper.class);
      p = mapper.participantByMyOsdId(myOsdId);
      session.commit();
    } finally {
      session.close();
    }
    return p;
  }

}
