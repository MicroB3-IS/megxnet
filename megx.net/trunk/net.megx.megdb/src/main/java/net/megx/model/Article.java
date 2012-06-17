package net.megx.model;


public interface Article extends SimpleAudit {

	public void addAuthor(Author author);

	public void addJournal(Journal journal);

	public void addSample(Sample sam);

	public String getAbstractHTML();

	public String getAbstractText();

	public Author getAuthor(int position);

	public String getDOI();

	public Author getFirstAuthor();

	public String getFirstPage();

	public String getFullTextHTML();

	public String getIssue();

	public Journal getJournal();

	public String getJournalName();

	public String getLastPage();

	public String getLinkout();

	public int getNumAuthors();

	public int getNumSamples();

	public String getPdf();

	public String getPMID();

	/**
	 * @return the publicationMonth
	 */
	public String getPublicationMonth();

	public String getPublicationYear();

	public Boolean getPublished();

	public String getPubStatus();

	public Sample getSample(int i);

	public String getTitle();

	public String getVolume();

	public void setAbstractHTML(String abstractHTML);

	public void setAbstractText(String abstractText);

	public void setDOI(String doi);

	public void setFirstPage(String page);

	public void setFullTextHTML(String fullTextHTML);

	public void setIssue(String issue);

	public void setLastPage(String page);

	public void setLinkout(String status);

	public void setPdf(String pdf);

	public void setPMID(String pmid);

	/**
	 * @param publicationMonth
	 *            the publicationMonth to set
	 */
	public void setPublicationMonth(String publicationMonth);

	public void setPublicationYear(String publicationYear);

	public void setPublished(Boolean status);

	public void setPubStatus(String status);

	public void setTitle(String title);

	public void setVolume(String Volume);

}