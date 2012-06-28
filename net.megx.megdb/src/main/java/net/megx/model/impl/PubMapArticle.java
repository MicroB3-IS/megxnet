package net.megx.model.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.megx.model.Article;
import net.megx.model.Author;
import net.megx.model.Journal;
import net.megx.model.Sample;

public class PubMapArticle implements Article {

	private String abstractHTML;
	private String abstractText;
	private List<Author> authors;
	private Date created;
	private String doi;
	private String firstPage = "";
	private String fullTextHTML;
	private String issue = "";
	private Journal journal;
	private String lastPage = "";
	private String linkout;
	/*
	 * Actually the URL to the pdf
	 */
	private String pdf;

	private String pmid;
	private String publicationMonth = "";

	private String publicationYear = "";
	private Boolean published;
	private String pubStatus;
	/*
	 * has to be set
	 */
	private String title;

	private Date updated;
	private String volume = "";
	private List<Sample> samples;
	private String createdBy;
	private String updatedBy;

	@Override
	public void addAuthor(Author author) {
		if (this.authors == null) {
			this.authors = new ArrayList<Author>();
		}
		authors.add(author);
	}

	@Override
	public void addJournal(Journal journal) {
		this.journal = journal;

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
	public Author getAuthor(int position) {
		if (authors != null) {
			return authors.get(position);
		}
		return null;
	}

	@Override
	public Date getCreated() {
		return this.created;
	}

	@Override
	public String getCreatedBy() {
		return createdBy;
	}

	@Override
	public String getDOI() {
		return this.doi;
	}

	@Override
	public Author getFirstAuthor() {
		if (authors != null) {
			return (Author) authors.get(0);
		}
		return null;
	}

	@Override
	public String getFirstPage() {
		return this.firstPage;
	}

	@Override
	public String getFullTextHTML() {
		return this.fullTextHTML;
	}

	/**
	 * @return the issue
	 */
	@Override
	public String getIssue() {
		return issue;
	}

	@Override
	public Journal getJournal() {
		return this.journal;
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

	@Override
	public String getLastPage() {
		return lastPage;
	}

	@Override
	public String getLinkout() {
		return this.linkout;
	}

	@Override
	public int getNumAuthors() {
		if (authors != null) {
			return authors.size();
		}
		return 0;
	}

	public int getNumSamples() {
		if (samples != null) {
			return samples.size();
		}
		return 0;
	}

	@Override
	public String getPdf() {
		return this.pdf;
	}

	@Override
	public String getPmid() {
		return this.pmid;
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
	 * @see net.megx.model.impl.Article#getPublicationYear()
	 */
	@Override
	public String getPublicationYear() {
		return publicationYear;
	}

	@Override
	public Boolean getPublished() {
		return published;
	}

	@Override
	public String getPubStatus() {
		return this.pubStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.impl.Article#getTitle()
	 */
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public Date getUpdated() {
		return updated;
	}

	@Override
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	@Override
	public String getVolume() {
		return this.volume;
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
		this.createdBy = createdBy;
	}

	@Override
	public void setDOI(String doi) {
		this.doi = doi;
	}

	@Override
	public void setFirstPage(String page) {
		this.firstPage = page;
	}

	@Override
	public void setFullTextHTML(String fullTextHTML) {
		this.fullTextHTML = fullTextHTML;
	}

	/**
	 * @param issue
	 *            the issue to set
	 */
	@Override
	public void setIssue(String issue) {
		this.issue = issue;
	}

	@Override
	public void setLastPage(String page) {
		this.lastPage = page;
	}

	@Override
	public void setLinkout(String linkout) {
		this.linkout = linkout;
	}

	@Override
	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	@Override
	public void setPmid(String pmid) {
		this.pmid = pmid;
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
	 * @see net.megx.model.impl.Article#setPublicationYear(java.lang.String)
	 */
	@Override
	public void setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
	}

	@Override
	public void setPublished(Boolean published) {
		this.published = published;
	}

	@Override
	public void setPubStatus(String status) {
		this.pubStatus = status;
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

	@Override
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
 
	@Override
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public void setVolume(String volume) {
		this.volume = volume;
	}

	@Override
	public void addSample(Sample sample) {
		if (samples == null) {
			samples = new ArrayList<Sample>();
		}
		samples.add(sample);

	}

	@Override
	public Sample getSample(int i) {
		if (samples != null) {
			return samples.get(i);
		} else {
			return null;
		}
	}
	
	@Override
	public List<Sample> getAllSamples() {
		return this.samples;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PubMapArticle [abstractHTML=").append(abstractHTML)
				.append(", pmid=").append(pmid).append(", abstractText=")
				.append(abstractText).append(", authors=").append(authors)
				.append(", created=").append(created).append(", doi=")
				.append(doi).append(", firstPage=").append(firstPage)
				.append(", fullTextHTML=").append(fullTextHTML)
				.append(", issue=").append(issue).append(", journal=")
				.append(journal).append(", lastPage=").append(lastPage)
				.append(", linkout=").append(linkout).append(", pdf=")
				.append(pdf).append(", publicationMonth=")
				.append(publicationMonth).append(", publicationYear=")
				.append(publicationYear).append(", published=")
				.append(published).append(", pubStatus=").append(pubStatus)
				.append(", title=").append(title).append(", updated=")
				.append(updated).append(", volume=").append(volume)
				.append(", samples=").append(samples).append(", createdBy=")
				.append(createdBy).append(", updatedBy=").append(updatedBy)
				.append("]");
		return builder.toString();
	}

}
