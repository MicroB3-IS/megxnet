package net.megx.model.impl;

import java.util.Date;
import java.util.List;

import net.megx.model.Article;
import net.megx.model.Author;
import net.megx.model.Journal;

public class PubMapArticle implements Article {

	private String title;
	private List<Author> authors;

	private String publicationMonth = "";
	private String publicationYear = "";

	private Date datePublished;
	private String pmid ="";
	private String doi;
	private Journal journal;
	private String issue = "";
	private String volume = "";
	private String lastPage = "";
	private String firstPage = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.impl.Article#getTitle()
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.impl.Article#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.impl.Article#getJournalName()
	 */
	@Override
	public String getJournalName() {
		if (journal != null) {
			return journal.getTitle();
		}
		return null;
	}

	/**
	 * @return the issue
	 */
	public String getIssue() {
		return issue;
	}

	/**
	 * @param issue
	 *            the issue to set
	 */
	public void setIssue(String issue) {
		this.issue = issue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.impl.Article#getPublicationMonth()
	 */
	@Override
	public String getPublicationMonth() {
		return publicationMonth;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.impl.Article#setPublicationMonth(java.lang.String)
	 */
	@Override
	public void setPublicationMonth(String publicationMonth) {
		this.publicationMonth = publicationMonth;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.impl.Article#getPublicationYear()
	 */
	@Override
	public String getPublicationYear() {
		return publicationYear;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.impl.Article#setPublicationYear(java.lang.String)
	 */
	@Override
	public void setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
	}

	@Override
	public String getPMID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPMID(String pmid) {
		this.pmid = pmid;
	}

	@Override
	public String getDOI() {
		// TODO Auto-generated method stub
		return this.doi;
	}

	@Override
	public void setDOI(String doi) {
		// TODO Auto-generated method stub
		this.doi = doi;
	}

	@Override
	public void addAuthor() {
		// TODO Auto-generated method stub

	}

	@Override
	public Author getAuthor(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addJournal(Journal journal) {
		this.journal = journal;

	}

	@Override
	public Journal getJournal() {
		return this.journal;
	}

	@Override
	public String getVolume() {
		return this.volume;
	}

	@Override
	public void setVolume(String Volume) {
		this.volume = volume;
	}

	@Override
	public String getFirstPage() {
		return this.firstPage;
	}

	@Override
	public void setFirstPage(String page) {
		this.firstPage = page;
	}

	@Override
	public String getLastPage() {
		return lastPage;
	}

	@Override
	public void setLastPage(String page) {
		this.lastPage = page;
	}

}
