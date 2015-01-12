package net.megx.model.pubmap;

public class Ocean {

	private String oceanName;
	private Double lat;
	private Double lon;

	public String getOceanName() {
		return oceanName;
	}

	public void setOceanName(String oceanName) {
		this.oceanName = oceanName;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return "Ocean [oceanName=" + oceanName + ", lat=" + lat + ", lon="
				+ lon + "]";
	}

}
