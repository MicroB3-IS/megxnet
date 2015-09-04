package net.megx.pubmap.model;

public class GeoLocation {

  private Integer pmid;
  private Double lon;
  private Double lat;
  private String worldRegion;
  private String place;

  public Integer getPmid() {
    return pmid;
  }

  public void setPmid(Integer pmid) {
    this.pmid = pmid;
  }

  public Double getLon() {
    return lon;
  }

  public void setLon(Double lon) {
    this.lon = lon;
  }

  public Double getLat() {
    return lat;
  }

  public void setLat(Double lat) {
    this.lat = lat;
  }

  public String getWorldRegion() {
    return worldRegion;
  }

  public void setWorldRegion(String worldRegion) {
    this.worldRegion = worldRegion;
  }

  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }

  @Override
  public String toString() {
    return "GeoLocation [pmid=" + pmid + ", lon=" + lon + ", lat=" + lat
        + ", worldRegion=" + worldRegion + ", place=" + place + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((lat == null) ? 0 : lat.hashCode());
    result = prime * result + ((lon == null) ? 0 : lon.hashCode());
    result = prime * result + ((place == null) ? 0 : place.hashCode());
    result = prime * result + ((pmid == null) ? 0 : pmid.hashCode());
    result = prime * result
        + ((worldRegion == null) ? 0 : worldRegion.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    GeoLocation other = (GeoLocation) obj;
    if (lat == null) {
      if (other.lat != null)
        return false;
    } else if (!lat.equals(other.lat))
      return false;
    if (lon == null) {
      if (other.lon != null)
        return false;
    } else if (!lon.equals(other.lon))
      return false;
    if (place == null) {
      if (other.place != null)
        return false;
    } else if (!place.equals(other.place))
      return false;
    if (pmid == null) {
      if (other.pmid != null)
        return false;
    } else if (!pmid.equals(other.pmid))
      return false;
    if (worldRegion == null) {
      if (other.worldRegion != null)
        return false;
    } else if (!worldRegion.equals(other.worldRegion))
      return false;
    return true;
  }

}
