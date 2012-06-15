package net.megx.model.impl;

import java.util.ArrayList;
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

	private String pmid;
	private String doi;
	private Journal journal;
	private String issue = "";
	private String volume = "";
	private String lastPage = "";
	private String firstPage = "";
	private String abstractHTML;
	private String abstractText;
	private Date created;
	private String fullTextHTML;
	private String pdf;
	private String pubStatus;
	private String linkout;
	private Boolean published;
	private Date updated;

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
	 * @see net.megx.model.impl.Article#getJournalName()
	 */
	@Override
	public String getJournalName() {
		if (journal != null) {
			return journal.getTitle();
		}
		return null;
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
		return this.pmid;
	}

	public Boolean getPublished() {
		return published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}

	@Override
	public void setPMID(String pmid) {
		this.pmid = pmid;
	}

	@Override
	public String getDOI() {
		return this.doi;
	}

	@Override
	public void setDOI(String doi) {
		this.doi = doi;
	}

	@Override
	public void addAuthor(Author author) {
		if (this.authors == null) {
			this.authors = new ArrayList<Author>();
		}
		authors.add(author);
	}

	@Override
	public int getNumAuthors() {
		if (authors != null) {
			return authors.size();
		}
		return 0;
	}

	@Override
	public Author getAuthor(int position) {
		if (authors != null) {
			return authors.get(position);
		}
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
	public void setVolume(String volume) {
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

	@Override
	public String getAbstractHTML() {
		return this.abstractHTML;
	}

	@Override
	public String getAbstractText() {
		return this.abstractText;
	}

	@Override
	public Date getCreated() {
		return this.created;
	}

	@Override
	public String getCreatedBy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFullTextHTML() {
		return this.fullTextHTML;
	}

	@Override
	public String getPdf() {
		return this.pdf;
	}

	@Override
	public String getUpdatedBy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAbstractHTML(String abstractHTML) {
		this.abstractHTML = abstractHTML;
	}

	@Override
	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;

	}

	@Override
	public void setCreatedBy(String createdBy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setFullTextHTML(String fullTextHTML) {
		this.fullTextHTML = fullTextHTML;
	}

	@Override
	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	@Override
	public void setUpdatedBy(String updatedBy) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPubStatus() {
		return this.pubStatus;
	}

	@Override
	public void setPubStatus(String status) {
		this.pubStatus = status;
	}

	@Override
	public String getLinkout() {
		return this.linkout;
	}

	@Override
	public void setLinkout(String linkout) {
		this.linkout = linkout;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
