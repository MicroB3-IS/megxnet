package net.megx.ws.browse.rest.tables;

/**
 * Row for table: Prokaryotic Genomes
 * 
 * http://www.megx.net/genomes/genomes.html
 * 
 * @author Jovica
 *
 */
public class GenomesUiTblRow {
	private String organismName;
	private String location;
	private String depth;
	private String dateSampled;
	private String envOLite;
	private String taxID;
	private String genomePID;
	
	public String getOrganismName() {
		return organismName;
	}
	public void setOrganismName(String organismName) {
		this.organismName = organismName;
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
	public String getTaxID() {
		return taxID;
	}
	public void setTaxID(String taxID) {
		this.taxID = taxID;
	}
	public String getGenomePID() {
		return genomePID;
	}
	public void setGenomePID(String genomePID) {
		this.genomePID = genomePID;
	}
}
