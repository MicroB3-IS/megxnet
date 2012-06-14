package net.megx.megdb.pubmap.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.pubmap.PubMapService;
import net.megx.megdb.pubmap.mappers.PubMapMapper;
import net.megx.model.Article;
import net.megx.model.Journal;
import net.megx.model.impl.PubMapArticle;
import net.megx.model.impl.PubMapJournal;

public class DBPubMapService extends BaseMegdbService implements PubMapService{

	@Override
	public List<Article> getAllArticles() throws Exception{
		/* TODO have to cut the better way to do it
		return doInSession(new BaseMegdbService.DBTask<PubMapMapper,List<Article>>() {

			@Override
			public List<Article> execute(PubMapMapper mapper) throws Exception {
				return mapper.getAllArticles();
			}
		}, PubMapMapper.class);
		*/
		Article art = new PubMapArticle();
		art.setTitle("articele Title");
		art.setDOI("doi/10.journal.article");
		Journal j = new PubMapJournal();
		j.setTitle("petrational geographic");
		art.addJournal(j);
		
		List<Article> articles = new ArrayList<Article>();
		articles.add(art);
		return articles;
		
//		SqlSession session = sessionFactory.openSession();
//		
//		try {
//			
//			session.commit();
//		} catch (Exception e) {
//			session.rollback();
//			throw e;
//		} finally{
//			session.close();
//		}
//		return articles;
	}
	
	@Override
	public int insertArticle(Article article) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertJournal(Journal journal) {
		// TODO Auto-generated method stub
		return 0;
	}

}
