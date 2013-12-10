package net.megx.model.geopfam;

public class GeoPfamRow {
	private String latlon;
	private String sampleName;
	private long numHits;
	
	private String targetAccession;

	public String getLatlon() {
		return latlon;
	}

	public void setLatlon(String latlon) {
		this.latlon = latlon;
	}

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	public long getNumHits() {
		return numHits;
	}

	public void setNumHits(long numHits) {
		this.numHits = numHits;
	}

	public String getTargetAccession() {
		return targetAccession;
	}

	public void setTargetAccession(String targetAccession) {
		this.targetAccession = targetAccession;
	}
}
