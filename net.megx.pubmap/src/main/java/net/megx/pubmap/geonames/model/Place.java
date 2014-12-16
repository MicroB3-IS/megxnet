package net.megx.pubmap.geonames.model;

public class Place {

  private String worldRegion;
  private String placeName;
  private String lat;
  private String lon;
  private boolean error;
  private String errorMsg;

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

  public boolean isError() {
    return error;
  }

  public void setError(boolean error) {
    this.error = error;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  @Override
  public String toString() {
    return "Place [worldRegion=" + worldRegion + ", placeName=" + placeName
        + ", lat=" + lat + ", lon=" + lon + ", error=" + error + ", errorMsg="
        + errorMsg + "]";
  }

}
