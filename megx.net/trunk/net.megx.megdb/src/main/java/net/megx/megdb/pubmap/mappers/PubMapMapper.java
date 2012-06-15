package net.megx.megdb.pubmap.mappers;

import java.util.List;

import net.megx.model.Article;
import net.megx.model.Author;
import net.megx.model.Journal;
/*
 * This is just for MyBatis to be able to do simple java calls which get automatically mapped to the XML mappers.
 * <code>
 * <pre>
 * <mapper namespace="net.megx.megdb.pubmap.mappers.PubMapMapper">
	<resultMap type="Article" id="article_map">
		<result property="title" column="db_title"/>
	</resultMap>

	<select id="getAllArticles" resultMap="article_map">
		select * from some.table
	</select>
</mapper>
</pre>
</code>
 */
public interface PubMapMapper {
	public List<Article> getAllArticles();
	public void insertArticleSelective(Article article);
	public void insertJournalSelective(Journal journal);
	public void insertAuthorSelective(Author author);
	public void deleteArticle(Article article);
}
