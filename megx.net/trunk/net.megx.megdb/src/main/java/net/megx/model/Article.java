package net.megx.model;

import java.util.Date;


public interface Article {

	
	public String getTitle();

	public void setTitle(String title);

	public String getJournalName();

	public void addAuthor(Author author);

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

	public String getPublicationYear();

	public void setPublicationYear(String publicationYear);

	public String getPMID();
	
	public String getAbstractHTML();

	public String getAbstractText();

	public Date getCreated();

	public String getCreatedBy();

	public String getFullTextHTML();

	public String getPdf();

	public String getUpdatedBy();

	public void setAbstractHTML(String abstractHTML);

	public void setAbstractText(String abstractText);

	public void setCreatedBy(String createdBy);

	public void setFullTextHTML(String fullTextHTML);

	public void setPdf(String pdf);

	public void setUpdatedBy(String updatedBy);

	public void setPMID(String pmid);
	
	public String getDOI();
	
	public void setDOI(String doi);
	
	public void setIssue(String issue);
	
	public String getVolume();
	public void setVolume(String Volume);
	
	public String getFirstPage();
	public void setFirstPage(String page);
	
	public String getLastPage();
	public void setLastPage(String page);
	public String getPubStatus();
	public void setPubStatus(String status);
	
	public String getLinkout();
	public void setLinkout(String status);
	
	public Boolean getPublished();
	public void setPublished(Boolean status);
	
	public Date getUpdated();

	public void setUpdated(Date updated);

}