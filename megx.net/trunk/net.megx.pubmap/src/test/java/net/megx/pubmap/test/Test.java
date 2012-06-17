package net.megx.pubmap.test;

import java.io.InputStream;

import net.megx.model.Article;
import net.megx.pubmap.rest.json.ArticleDTO;

import org.chon.core.common.util.FileUtils;
import org.json.JSONObject;

import com.google.gson.Gson;

public class Test {
	private static Gson gson = new Gson();
	public static void main(String[] args) throws Exception {
		InputStream is = Test.class.getResourceAsStream("/net/megx/pubmap/test/a.js");
		String s = FileUtils.readInputStreamToString(is);
		s = s.substring(s.indexOf("=")+1);
		ArticleDTO dto = gson.fromJson(s, ArticleDTO.class);
		Article a = dto.toArticle();
		System.out.println(a);
		
		dto = ArticleDTO.fromArticle(a);
		String jsonStr = gson.toJson(dto);
		System.out.println(new JSONObject(jsonStr).toString(3));
	}
}
