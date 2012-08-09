package net.megx.pubmap.mock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.megx.model.Article;
import net.megx.pubmap.rest.json.ArticleDTO;

import org.apache.commons.io.IOUtils;
import org.chon.cms.core.Utils;

import com.google.gson.Gson;

public class PubMapMockService {

	public static String JSON_DIR_NAME = System.getProperty("app.work.dir") + "/pubmap";
	public static Gson gson = new Gson();
	
	
	

	public static void saveTofile(String id, String json) throws IOException{
		File file = new File(JSON_DIR_NAME);
		file.mkdirs();
		
		OutputStream toFile = new FileOutputStream(new File(file, getFileName(id)));
		
		Reader reader = new StringReader(json);
		IOUtils.copy(reader, toFile);
		toFile.close();
		reader.close();
	}
	
	private static String readFromFile(String name) throws IOException{
		InputStream is = new FileInputStream(name);
		StringWriter sw = new StringWriter();
		IOUtils.copy(is, sw);
		return sw.toString();
	}
	
	
	public static List<Article> getAllArticles() throws IOException{
		File f = new File(JSON_DIR_NAME);
		String [] names = f.list();
		List<Article> articles = new ArrayList<Article>(names.length);
		
		for(String name: names){
			String articleJSONString = readFromFile(JSON_DIR_NAME + "/" +name);
			ArticleDTO articleDTO = gson.fromJson(articleJSONString, ArticleDTO.class);
			articles.add(articleDTO.toDAO());
		}
		Collections.sort(articles, new Comparator<Article>() {
			@Override
			public int compare(Article o1, Article o2) {
				int titleCmp = o1.getTitle().compareTo(o2.getTitle());
				return titleCmp == 0 ? o1.getPublicationYear().compareTo(o2.getPublicationYear()) : titleCmp;
			}
		});
		return articles;
	}
	
	
	
	
	
	private static String getFileName(String id){
		return Utils.getMd5Digest(id) + ".json";
	}
	
}
