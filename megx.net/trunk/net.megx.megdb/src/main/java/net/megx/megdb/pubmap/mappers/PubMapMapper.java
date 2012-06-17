package net.megx.megdb.pubmap.mappers;

import java.util.List;

import net.megx.model.Article;
import net.megx.model.Author;
import net.megx.model.Journal;

import org.apache.ibatis.annotations.Param;

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

	public int insertArticleSelective(Article article);

	public int insertJournalSelective(Journal journal);

	public int insertJournalSelectiveIgnoreDups(Journal journal);

	public int insertAuthorSelective(Author author);

	public int insertAuthorSelectiveIgnoreDups(Author author);

	public int deleteArticle(Article article);

	public int insertAuthorList(@Param("article") Article article,
			@Param("author") Author author, @Param("position") int position);

}
