/**
 * 
 */
package net.megx.model.impl;

import java.util.Date;

import net.megx.model.Journal;

/**
 * @author rkottman
 * 
 */
public class PubMapJournal implements Journal {

	private String title;
	private String publisher;
	private String isoAbbr;
	private String medAbbr;
	private String homepage;
	private String pissn;
	private String eissn;
	private String country;
	private Date created;
	private Date updated;

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#getTitle()
	 */
	@Override
	public String getTitle() {
		return this.title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#getPublisher()
	 */
	@Override
	public String getPublisher() {
		return publisher;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#setPublisher(java.lang.String)
	 */
	@Override
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#getIsoAbbr()
	 */
	@Override
	public String getIsoAbbr() {
		return isoAbbr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#setIsoAbbr(java.lang.String)
	 */
	@Override
	public void setIsoAbbr(String isoAbbr) {
		this.isoAbbr = isoAbbr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#getMedAbbr()
	 */
	@Override
	public String getMedAbbr() {
		return medAbbr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#setMedAbbr(java.lang.String)
	 */
	@Override
	public void setMedAbbr(String medAbbr) {
		this.medAbbr = medAbbr;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#getHomepage()
	 */
	@Override
	public String getHomepage() {
		return homepage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#setHomepage(java.lang.String)
	 */
	@Override
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#getPissn()
	 */
	@Override
	public String getPissn() {
		return pissn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#setPissn(java.lang.String)
	 */
	@Override
	public void setPissn(String pissn) {
		this.pissn = pissn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#getEissn()
	 */
	@Override
	public String getEissn() {
		return eissn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#setEissn(java.lang.String)
	 */
	@Override
	public void setEissn(String eissn) {
		this.eissn = eissn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#getCountry()
	 */
	@Override
	public String getCountry() {
		return country;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#setCountry(java.lang.String)
	 */
	@Override
	public void setCountry(String country) {
		this.country = country;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#getPubstart()
	 */
	@Override
	public String getPubstart() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#setPubstart(java.lang.String)
	 */
	@Override
	public void setPubstart(String pubstart) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#getLang()
	 */
	@Override
	public String getLang() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#setLang(java.lang.String)
	 */
	@Override
	public void setLang(String lang) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#getNlmid()
	 */
	@Override
	public String getNlmid() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#setNlmid(java.lang.String)
	 */
	@Override
	public void setNlmid(String nlmid) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#getCreated()
	 */
	@Override
	public Date getCreated() {
		return created;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Journal#getUpdated()
	 */
	@Override
	public Date getUpdated() {
		return updated;
	}

	@Override
	public String getCreatedBy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCreatedBy(String createdBy) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getUpdatedBy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUpdatedBy(String updatedBy) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PubMapJournal [title=").append(title)
				.append(", publisher=").append(publisher).append(", isoAbbr=")
				.append(isoAbbr).append(", medAbbr=").append(medAbbr)
				.append(", homepage=").append(homepage).append(", pissn=")
				.append(pissn).append(", eissn=").append(eissn)
				.append(", country=").append(country).append(", created=")
				.append(created).append(", updated=").append(updated)
				.append("]");
		return builder.toString();
	}
	
	

}
