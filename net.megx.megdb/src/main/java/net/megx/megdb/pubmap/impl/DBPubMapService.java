package net.megx.megdb.pubmap.impl;

import java.sql.SQLException;
import java.util.List;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.pubmap.PubMapService;
import net.megx.megdb.pubmap.mappers.PubMapMapper;
import net.megx.model.Article;
import net.megx.model.Author;
import net.megx.model.Journal;
import net.megx.model.ModelMockFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

public class DBPubMapService extends BaseMegdbService implements PubMapService {
	private static final Log log = LogFactory.getLog(DBPubMapService.class);

	@Override
	public List<Article> getAllArticles() throws Exception {
		/*
		 * TODO have to cut the better way to do it return doInSession(new
		 * BaseMegdbService.DBTask<PubMapMapper,List<Article>>() {
		 * 
		 * @Override public List<Article> execute(PubMapMapper mapper) throws
		 * Exception { return mapper.getAllArticles(); } }, PubMapMapper.class);
		 */

		return ModelMockFactory.createArticleList();

	}

	@Override
	public int insertArticle(Article article) {
		// do several things not auto-commit!
		SqlSession sqlSession = super.sessionFactory.openSession(false);

		PubMapMapper mapper = sqlSession.getMapper(PubMapMapper.class);

		try {
			mapper.insertJournalSelective(article.getJournal());
		} catch (PersistenceException pe) {

			if (pe.getCause() instanceof SQLException) {
				SQLException sqle = (SQLException) pe.getCause();

				// if unique violation TODO make better
				if ("23505".equals(sqle.getSQLState())) {
					log.warn("Journal already exists. Silently continue without update."
							+ sqle.getErrorCode() + sqle.getSQLState());
					sqlSession.rollback();
				} else {
					// other error just rethrow and also continue
					throw new PersistenceException(pe);
				}
			}
		} 
		
		try {
			System.out.println(article.getAuthor(0));
			mapper.insertAuthorSelective(article.getAuthor(0));
		} catch (BuilderException e) {
			System.out.println(e);
			sqlSession.rollback();
		} catch (PersistenceException pe) {

			if (pe.getCause() instanceof SQLException) {
				SQLException sqle = (SQLException) pe.getCause();

				System.out.println(sqle.getErrorCode() + sqle.getSQLState() + sqle);
				// if unique violation TODO make better
				if ("23505".equals(sqle.getSQLState())) {
					log.warn("Author already exists. Silently continue without update."
							+ sqle.getErrorCode() + sqle.getSQLState());
					sqlSession.rollback();
				} else {
					// other error just re-throw and also continue
					throw new PersistenceException(pe);
				}
			}
			
		}	

		try {
			mapper.insertArticleSelective(article);

			sqlSession.commit();
		} catch (PersistenceException pe) {
			System.out.println(pe);
			sqlSession.rollback();
		}
		finally {
			sqlSession.close();
		}
		return 0;
	}



	@Override
	public int insertJournal(Journal journal) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteArticle(Article article) {
		SqlSession sqlSession = super.sessionFactory.openSession(false);

		PubMapMapper mapper = sqlSession.getMapper(PubMapMapper.class);

		try {
			mapper.deleteArticle(article);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
		
		return 0;
	}

	
	@Override
	public int insertAuthor(Author author) {
		SqlSession sqlSession = super.sessionFactory.openSession(false);

		PubMapMapper mapper = sqlSession.getMapper(PubMapMapper.class);

		try {
			mapper.insertAuthorSelective(author);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
		
		return 0;
	}
}
