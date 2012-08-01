package net.megx.megdb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * Base MegDB service. This class is intended to be sub-classed by concrete
 * implementation for specific DataBase access object.
 * It provides helper methods for getting MyBatis mappers and executing 
 * database queries in transactions or in sessions.
 * <p>
 * A concrete example would be as follows:
 * <code>
 * <pre>
  public class UserDBService extends BaseMegdbService{
  
  		public User getUserByUsername(final String username) throws Exception{
  			return doInSession(new BaseMegdbService.DBTask<UserMapper,User>(){
  				public User execute(UserMapper mapper) throws Exception{
  					return mapper.selectUserByUsername(username);
 				}
  			}, UserMapper.class);
  		}
  		
  
  		public void removeUser(final String username) throws Exception{
  			doInTrasaction(new BaseMegdbService.DBTask<UserMapper,Object>(){
  				public Object execute(UserMapper mapper) throws Exception{
  					mapper.clearRoleFor(username);
  					mapper.clearConsumersFor(username);
  					mapper.removeUser(username);
  					return null;
 				}
  			}, UserMapper.class);
 		}
  		
  }
  </pre>
 * </code>
 * 
 * As seen from the example, UserDBService implements two methods - query for user and removal of user.
 * A precondition here is that the UserMapper needs to exist. This is the MyBatis mapper interface, and User is
 * the DTO for database access.
 * 
 * The first method performs a simple query and this would be logical place not to use transaction.
 * The second method uses the helper "doInTransation", which explicitly sets a transaction for the "execute" method.
 * @author pavle
 *
 */
public class BaseMegdbService {
	
	/**
	 * Represents a DB query task to be executed.<br/>
	 * The interface is <code>protected</code> and is intended to be used only by the
	 * subclasses of {@link BaseMegdbService}.
	 * @author pavle
	 *
	 * @param <M> is the MyBatis mapper interface
	 * @param <R> is the type of the result object that is to be returned.
	 */
	protected interface Task<M,R>{
		/**
		 * Execute the database task.
		 * This method is called after the SQL session is set and the Task is ready to execute.
		 * @param mapper is the MyBatis mapper instance.
		 * @return the result from the task
		 * @throws Exception
		 */
		public R execute(M mapper) throws Exception;
		
		/**
		 * Sets the MyBatis SQLSession instance available for this Task.<br/>
		 * The SQL session needs to be available, if some other mapper is needed at runtime
		 * and must be provided in the same session.
		 * @param session
		 */
		public void setSession(SqlSession session);
	}
	
	/**
	 * Is a basic implementation of {@link Task}. This class implemnts the boilerplate code around
	 * the SQL session setup.
	 * 
	 * @author pavle
	 *
	 * @param <M>
	 * @param <R>
	 */
	public static abstract class DBTask<M,R> implements Task<M, R>{
		protected SqlSession session;
		public void setSession(SqlSession session){
			this.session = session;
		}
	}
	
	
	protected Log log = LogFactory.getLog(getClass());
	
	
	protected SqlSessionFactory sessionFactory;
	
	
	public void setSqlSessionFactory(SqlSessionFactory factory){
		sessionFactory = factory;
	}
	
	
	protected <M,R> R  doInTransaction(Task<M, R> task, Class<M> mapperClass) throws Exception{
		log.debug("Executing Task in transaction...");
		SqlSession session = sessionFactory.openSession();
		log.debug("Session is opened.");
		task.setSession(session);
		M mapper = session.getMapper(mapperClass);
		log.debug("Mapper instance created...");
		try {
			log.debug("Executing task...");
			R result = task.execute(mapper);
			log.debug("Task executed. Committing transaction...");
			session.commit();
			log.debug("Transaction committed.");
			return result;
		} catch (Exception e) {
			log.debug("An error occured. The transaction will be rolled back. Exception: ", e);
			session.rollback(true);
			log.debug("Transaction has been rolled back.");
			throw e;
		} finally{
			session.close();
			log.debug("Session closed.");
		}
	}
	protected <M,R> R  doInSession(Task<M, R> task, Class<M> mapperClass) throws Exception{
		log.debug("Executing Task in session...");
		SqlSession session = sessionFactory.openSession();
		log.debug("Session is opened.");
		task.setSession(session);
		M mapper = session.getMapper(mapperClass);
		log.debug("Mapper instance created...");
		try {
			log.debug("Executing task...");
			R result = task.execute(mapper);
			log.debug("Task executed.");
			return result;
		} catch (Exception e) {
			log.debug("An error occured: ", e);
			throw e;
		} finally{
			session.close();
			log.debug("Session closed.");
		}
	}
}
