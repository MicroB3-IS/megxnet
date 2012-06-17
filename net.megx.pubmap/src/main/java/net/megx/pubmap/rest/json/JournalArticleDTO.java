package net.megx.pubmap.rest.json;

import net.megx.model.Article;
import net.megx.model.Journal;
import net.megx.model.ModelFactory;

public class JournalArticleDTO {
	private String issue;
	private String isoab;
	private String pages;
	private String publication;
	private String volume;
	private String month;
	private String day;
	private String abstractText;
	private String eissn;
	private String pissn;
	
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getIsoab() {
		return isoab;
	}
	public void setIsoab(String isoab) {
		this.isoab = isoab;
	}
	public String getPages() {
		return pages;
	}
	public void setPages(String pages) {
		this.pages = pages;
	}
	public String getPublication() {
		return publication;
	}
	public void setPublication(String publication) {
		this.publication = publication;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getAbstractText() {
		return abstractText;
	}
	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}
	
	public String getEissn() {
		return eissn;
	}
	public void setEissn(String eissn) {
		this.eissn = eissn;
	}
	public String getPissn() {
		return pissn;
	}
	public void setPissn(String pissn) {
		this.pissn = pissn;
	}
	public static JournalArticleDTO fromDAO(Article article) {
		Journal journal = article.getJournal();
		//Author author = article.getFirstAuthor();
		JournalArticleDTO rv = new JournalArticleDTO();
		rv.issue = article.getIssue();
		rv.isoab = journal.getIsoAbbr();
		
		//TOCO: check this
		rv.pages = article.getFirstPage() + " - " + article.getLastPage();
		
		rv.publication = journal.getPublisher();
		rv.volume = article.getVolume();
		rv.month = article.getPublicationMonth();
		//rv.day = ":::TODO:::";
		rv.abstractText = article.getAbstractText();
		rv.eissn = journal.getEissn();
		rv.pissn = journal.getPissn();
		return rv;
	}
	
	// since not 1-1 mappings, we need artice and we modify it ...
	public Journal toJournal(Article a) {
		a.setIssue( this.issue );
		Journal journal = ModelFactory.createJournal();
		journal.setIsoAbbr( this.isoab );
		if(this.pages != null) {
			String[] pagesArr = this.pages.split("-");
			a.setFirstPage( pagesArr[0].trim() );
			if(pagesArr.length > 1) {
				a.setLastPage(pagesArr[1].trim());
			}
		}
		journal.setPublisher( this.publication );
		a.setVolume( this.volume );
		a.setPublicationMonth( this.month );
		a.setAbstractText( this.abstractText );
		journal.setEissn( this.eissn );
		journal.setPissn( this.pissn );
		return journal;
	}
}
