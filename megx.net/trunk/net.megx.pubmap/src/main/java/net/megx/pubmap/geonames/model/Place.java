package net.megx.pubmap.geonames.model;

public class Place {

  private String worldRegion;
  private String placeName;
  private String lat;
  private String lon;

  public String getWorldRegion() {
    return worldRegion;
  }

  public void setWorldRegion(String worldRegion) {
    this.worldRegion = worldRegion;
  }

  public String getPlaceName() {
    return placeName;
  }

  public void setPlaceName(String placeName) {
    this.placeName = placeName;
  }

  public String getLat() {
    return lat;
  }

  public void setLat(String lat) {
    this.lat = lat;
  }

  public String getLon() {
    return lon;
  }

  public void setLon(String lon) {
    this.lon = lon;
  }

  @Override
  public String toString() {
    return "Place [worldRegion=" + worldRegion + ", placeName=" + placeName
        + ", lat=" + lat + ", lon=" + lon + "]";
  }

}
