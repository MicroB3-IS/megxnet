package net.megx.security.auth.db;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class BaseDBService {
	protected interface Task<M,R>{
		public R execute(M mapper) throws Exception;
		public void setSession(SqlSession session);
	}
	
	public static abstract class DBTask<M,R> implements Task<M, R>{
		protected SqlSession session;
		public void setSession(SqlSession session){
			this.session = session;
		}
	}
	
	protected SqlSessionFactory sessionFactory;
	
	public void setSqlSessionFactory(SqlSessionFactory factory){
		sessionFactory = factory;
	}
	
	protected <M,R> R  doInTransaction(Task<M, R> task, Class<M> mapperClass) throws Exception{
		SqlSession session = sessionFactory.openSession();
		task.setSession(session);
		M mapper = session.getMapper(mapperClass);
		try {
			R result = task.execute(mapper);
			session.commit();
			return result;
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally{
			session.close();
		}
	}
	protected <M,R> R  doInSession(Task<M, R> task, Class<M> mapperClass) throws Exception{
		SqlSession session = sessionFactory.openSession();
		task.setSession(session);
		M mapper = session.getMapper(mapperClass);
		try {
			R result = task.execute(mapper);
			return result;
		} catch (Exception e) {
			throw e;
		} finally{
			session.close();
		}
	}
}
