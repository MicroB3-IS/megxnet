package net.megx.pubmap.geonames.model;

public class Geoname {

	private String toponymName;
	private String name;
	private double lat;
	private double lng;
	private int geonameId;
	private String countryCode;
	private String countryName;
	private String fcl;
	private String fcode;

	public String getToponymName() {
		return toponymName;
	}

	public void setToponymName(String toponymName) {
		this.toponymName = toponymName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(int lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(int lng) {
		this.lng = lng;
	}

	public int getGeonameId() {
		return geonameId;
	}

	public void setGeonameId(int geonameId) {
		this.geonameId = geonameId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getFcl() {
		return fcl;
	}

	public void setFcl(String fcl) {
		this.fcl = fcl;
	}

	public String getFcode() {
		return fcode;
	}

	public void setFcode(String fcode) {
		this.fcode = fcode;
	}

	@Override
	public String toString() {
		return "Geoname [toponymName=" + toponymName + ", name=" + name
				+ ", lat=" + lat + ", lng=" + lng + ", geonameId=" + geonameId
				+ ", countryCode=" + countryCode + ", countryName="
				+ countryName + ", fcl=" + fcl + ", fcode=" + fcode + "]";
	}

}
