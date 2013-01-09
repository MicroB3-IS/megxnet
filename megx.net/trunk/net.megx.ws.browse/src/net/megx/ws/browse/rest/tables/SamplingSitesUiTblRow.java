package net.megx.ws.browse.rest.tables;

/**
 * Row for table: Sampling Sites
 * 
 * http://www.megx.net/sites/sites.html
 * 
 * @author Jovica
 *
 */
public class SamplingSitesUiTblRow {
	private String samplingSite;
	private String description;
	private String location;
	private String noOfSamples;
	
	public String getSamplingSite() {
		return samplingSite;
	}
	public void setSamplingSite(String samplingSite) {
		this.samplingSite = samplingSite;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getNoOfSamples() {
		return noOfSamples;
	}
	public void setNoOfSamples(String noOfSamples) {
		this.noOfSamples = noOfSamples;
	}
}
