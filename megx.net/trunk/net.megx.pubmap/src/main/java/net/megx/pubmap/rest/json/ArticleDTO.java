package net.megx.pubmap.rest.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.megx.model.Article;
import net.megx.model.Author;
import net.megx.model.Journal;
import net.megx.model.ModelFactory;

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
			rv.authors.add(AuthorDTO.fromDAO(a.getAuthor(i), i));
		}
		
		Author author = a.getFirstAuthor();
		
		
		rv.website = a.getFullTextHTML();
		rv.year = a.getPublicationYear();
		rv.journalArticle = JournalArticleDTO.fromDAO(a);
		rv.identifiers = new HashMap<String, String>();
		rv.identifiers.put("doi", a.getDOI());
		rv.identifiers.put("pmid", a.getPMID());
		rv.abstractUrl = a.getAbstractHTML();
		rv.institute = author.getInstitute();
		
		return rv;
	}
	
	public Article toArticle() {
		Article a = ModelFactory.createArticle();
		a.setTitle( this.getTitle() );
		
		//sort by author position
		Collections.sort(authors, new Comparator<AuthorDTO>() {
			@Override
			public int compare(AuthorDTO a1, AuthorDTO a2) {
				return a1.getPosition() - a2.getPosition();
			}
		});
		
		for(AuthorDTO authorDTO : this.authors) {
			Author author = authorDTO.toAuthor();
			a.addAuthor(author);
		}
		
		a.setFullTextHTML( this.website );
		a.setPublicationYear( this.year );
		
		
		//rv.journalArticle = JournalArticleDTO.fromDAO(a);
		Journal journal = this.journalArticle.toJournal(a);
		a.addJournal(journal);
		
		if(this.identifiers != null) {
			if(this.identifiers.containsKey("doi")) {
				a.setDOI( this.identifiers.get("doi") );
			}
			if(this.identifiers.containsKey("pmid")) {
				a.setPMID( this.identifiers.get("pmid") );
			}
		}
				
		a.setAbstractHTML( this.abstractUrl );
		a.getFirstAuthor().setInstitute( this.institute );
		
		return a;
	}
}
