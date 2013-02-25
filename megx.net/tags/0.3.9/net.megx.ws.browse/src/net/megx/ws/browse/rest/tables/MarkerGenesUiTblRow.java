package net.megx.ws.browse.rest.tables;

/**
 * Row for table: Marker Genes
 * 
 * http://www.megx.net/marker_genes/rrna/rrna.html
 * 
 * @author Jovica
 * 
 */
public class MarkerGenesUiTblRow {
	private String samplingSite;
	private String location;
	private String depth;
	private String dateSampled;
	private String envOLite;
	private String noOfSequences;
	public String getSamplingSite() {
		return samplingSite;
	}
	public void setSamplingSite(String samplingSite) {
		this.samplingSite = samplingSite;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public String getDateSampled() {
		return dateSampled;
	}
	public void setDateSampled(String dateSampled) {
		this.dateSampled = dateSampled;
	}
	public String getEnvOLite() {
		return envOLite;
	}
	public void setEnvOLite(String envOLite) {
		this.envOLite = envOLite;
	}
	public String getNoOfSequences() {
		return noOfSequences;
	}
	public void setNoOfSequences(String noOfSequences) {
		this.noOfSequences = noOfSequences;
	}
}
