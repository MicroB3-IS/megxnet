package net.megx.megdb.pubmap.impl;

import java.sql.SQLException;
import java.util.List;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.pubmap.PubMapService;
import net.megx.megdb.pubmap.mappers.PubMapMapper;
import net.megx.megdb.pubmap.mappers.SamplesMapper;
import net.megx.model.Article;
import net.megx.model.Author;
import net.megx.model.Journal;
import net.megx.model.ModelMockFactory;
import net.megx.model.Sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
		SamplesMapper samMapper = sqlSession.getMapper(SamplesMapper.class);

		// first insert Journal
		try {
			mapper.insertJournalSelectiveIgnoreDups(article.getJournal());
			Author author = null;
			for (int i = 0; i < article.getNumAuthors(); i++) {
				author = article.getAuthor(i);
				System.out.println(author);
				mapper.insertAuthorSelectiveIgnoreDups(author);

			}
			mapper.insertArticleSelective(article);
			for (int i = 0; i < article.getNumAuthors(); i++) {
				author = article.getAuthor(i);
				mapper.insertAuthorList(article, author, i + 1);
			}

			Sample sample = null;
			for (int i = 0; i < article.getNumSamples(); i++) {
				sample = article.getSample(i);
				samMapper.insertSampleSelective(sample);
			}

			sqlSession.commit();
		} catch (PersistenceException pe) {
			throw new PersistenceException(pe);
		} finally {
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
		int rows = 0;
		try {
			rows = mapper.deleteArticle(article);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}

		return rows;
	}

	@Override
	public int insertAuthor(Author author) {
		SqlSession sqlSession = super.sessionFactory.openSession(false);

		PubMapMapper mapper = sqlSession.getMapper(PubMapMapper.class);

		try {
			mapper.insertAuthorSelectiveIgnoreDups(author);
			sqlSession.commit();
		} catch (PersistenceException pe) {
			if (pe.getCause() instanceof SQLException) {
				SQLException sqle = (SQLException) pe.getCause();

				// if unique violation TODO make better
				if ("23505".equals(sqle.getSQLState())) {
					log.warn("Author already exists. Silently continue without update."
							+ sqle.getSQLState());
					sqlSession.rollback();
				} else {
					// other error just re-throw and also continue
					throw new PersistenceException(pe);
				}
			}

		} finally {
			sqlSession.close();
		}

		return 0;
	}

	@Override
	public Article selectArticleDetailsById(String id, String idCode) {
		SqlSession sqlSession = super.sessionFactory.openSession(false);

		PubMapMapper mapper = sqlSession.getMapper(PubMapMapper.class);
		Article article;
		try {
			article = mapper.selectArticleDetailsById(id, idCode);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
		return article;
	}
}
