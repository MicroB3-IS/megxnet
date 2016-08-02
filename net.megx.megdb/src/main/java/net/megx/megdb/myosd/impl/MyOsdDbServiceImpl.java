package net.megx.megdb.myosd.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.myosd.MyOsdDbService;
import net.megx.megdb.myosd.MyOsdMyBatisMapper;
import net.megx.megdb.myosd.MyOsdParticipantRegistration;
import net.megx.megdb.myosd.MyOsdSample;
import net.megx.megdb.myosd.dto.MyOsdSampleImpl;

public class MyOsdDbServiceImpl extends BaseMegdbService
    implements MyOsdDbService {

  public void saveSample(MyOsdSample sample) {
    SqlSession session = super.sessionFactory.openSession();
    log.debug("mybatis saving sample");
    try {
      MyOsdMyBatisMapper mapper = session.getMapper(MyOsdMyBatisMapper.class);
      mapper.saveSample(sample);
      session.commit();
    } finally {
      session.close();
    }
  }

  public MyOsdSample sampleByMyOsdId(MyOsdSample sample) {
    SqlSession session = super.sessionFactory.openSession();

    try {
      MyOsdMyBatisMapper mapper = session.getMapper(MyOsdMyBatisMapper.class);
      sample = mapper.sampleByMyOsdId(sample);
      session.commit();
      session.close();
    } finally {
      session.close();
    }
    return sample;
  }

  @Override
  public List<MyOsdSample> getSamples() {
    SqlSession session = super.sessionFactory.openSession();

    List<MyOsdSample> result = new ArrayList<MyOsdSample>();
    try {
      MyOsdMyBatisMapper mapper = session.getMapper(MyOsdMyBatisMapper.class);
      result = mapper.getSamples();
      session.commit();
      session.close();
    } finally {
      session.close();
    }
    return result;
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
      session.close();
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
