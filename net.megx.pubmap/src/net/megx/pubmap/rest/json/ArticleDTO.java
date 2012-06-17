package net.megx.pubmap.rest.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.megx.model.Article;
import net.megx.model.Author;

public class ArticleDTO {
	private List<AuthorDTO> authors;
	private String title;
	private String website;
	private String year;
	private JournalArticleDTO journalArticle;
	private Map<String, String> identifiers;
	// TODO: Samples
	private String abstractUrl;
	private String institute;

	public List<AuthorDTO> getAuthors() {
		return authors;
	}

	public void setAuthors(List<AuthorDTO> authors) {
		this.authors = authors;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public JournalArticleDTO getJournalArticle() {
		return journalArticle;
	}

	public void setJournalArticle(JournalArticleDTO journalArticle) {
		this.journalArticle = journalArticle;
	}

	public Map<String, String> getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(Map<String, String> identifiers) {
		this.identifiers = identifiers;
	}

	public String getAbstractUrl() {
		return abstractUrl;
	}

	public void setAbstractUrl(String abstractUrl) {
		this.abstractUrl = abstractUrl;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public static ArticleDTO fromArticle(Article a) {
		ArticleDTO rv = new ArticleDTO();
		
		rv.title = a.getTitle();
		
		rv.authors = new ArrayList<AuthorDTO>();
		int authorsCnt = a.getNumAuthors();
		if(authorsCnt <= 0) {
			throw new RuntimeException("Invalid article. Authors count is " + authorsCnt);
		}
		
		for (int i = 0; i < authorsCnt; i++) {
			rv.authors.add(AuthorDTO.fromDAO(a.getAuthor(i)));
		}
		
		Author author = a.getFirstAuthor();
		
		
		rv.website = ":::TODO:::";
		rv.year = a.getPublicationYear();
		rv.journalArticle = JournalArticleDTO.fromDAO(a);
		rv.identifiers = new HashMap<String, String>();
		rv.identifiers.put("doi", a.getDOI());
		rv.identifiers.put("pmid", a.getPMID());
		rv.abstractUrl = ":::TODO:::";
		rv.institute = author.getInstitute();
		
		return rv;
	}
}
