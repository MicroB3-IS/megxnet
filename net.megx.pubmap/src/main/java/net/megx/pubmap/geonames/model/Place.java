package net.megx.pubmap.geonames.model;

public class Place {

	private String country;
	private String placeName;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	@Override
	public String toString() {
		return "Place [country=" + country + ", placeName=" + placeName + "]";
	}

}
