package net.megx.model;

import java.util.Date;

public interface Journal {

	public String getTitle();

	public void setTitle(String title);

	public String getPublisher();

	public void setPublisher(String publisher);

	public String getIsoAbbr();

	public void setIsoAbbr(String isoAbbr);

	public String getMedAbbr();

	public void setMedAbbr(String medAbbr);

	public String getHomepage();

	public void setHomepage(String homepage);

	public String getPissn();

	public void setPissn(String pissn);

	public String getEissn();

	public String getCreatedBy();

	public void setCreatedBy(String createdBy);

	public void setEissn(String eissn);

	public String getCountry();

	public void setCountry(String country);

	public String getPubstart();

	public void setPubstart(String pubstart);

	public String getLang();

	public void setLang(String lang);

	public String getNlmid();

	public void setNlmid(String nlmid);

	public String getUpdatedBy();

	public void setUpdatedBy(String updatedBy);

	public Date getCreated();

	public Date getUpdated();

}