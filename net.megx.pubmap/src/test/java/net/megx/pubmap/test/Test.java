package net.megx.pubmap.test;

import java.io.InputStream;

import net.megx.model.Article;
import net.megx.pubmap.rest.json.ArticleDTO;

import org.chon.core.common.util.FileUtils;

import com.google.gson.Gson;

public class Test {
	private static Gson gson = new Gson();
	public static void main(String[] args) throws Exception {
		InputStream is = Test.class.getResourceAsStream("/net/megx/pubmap/test/a.js");
		String s = FileUtils.readInputStreamToString(is);
		s = s.substring(s.indexOf("=")+1);
		
		//DAO article from json
		ArticleDTO dto = gson.fromJson(s, ArticleDTO.class);
		Article a = dto.toArticle();
		//we have the article, print it
		System.out.println(a);
		
		//Convert it back to json
		//dto = ArticleDTO.fromArticle(a);
		//String jsonStr = gson.toJson(dto);
		//System.out.println(new JSONObject(jsonStr).toString(3));
	}
}
