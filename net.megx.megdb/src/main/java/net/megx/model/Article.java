package net.megx.model;

import java.util.Date;

public interface Article {

	public String getTitle();

	public void setTitle(String title);

	public String getJournalName();

	public void addAuthor();
	public Author getAuthor(int position);
	public int getNumAuthors();
	
	public void addJournal(Journal journal);
	public Journal getJournal();
	

	/**
	 * @return the publicationMonth
	 */
	public String getPublicationMonth();

	/**
	 * @param publicationMonth
	 *            the publicationMonth to set
	 */
	public void setPublicationMonth(String publicationMonth);

	/**
	 * @return the publicationYear
	 */
	public String getPublicationYear();

	/**
	 * @param publicationYear
	 *            the publicationYear to set
	 */
	public void setPublicationYear(String publicationYear);

	public String getPMID();
	
	public void setPMID(String pmid);
	
	public String getDOI();
	
	public void setDOI(String doi);
	

	/**
	 * @param issue the issue to set
	 */
	public void setIssue(String issue);
	
	public String getVolume();
	public void setVolume(String Volume);
	
	public String getFirstPage();
	public void setFirstPage(String page);
	
	public String getLastPage();
	public void setLastPage(String page);
	
}