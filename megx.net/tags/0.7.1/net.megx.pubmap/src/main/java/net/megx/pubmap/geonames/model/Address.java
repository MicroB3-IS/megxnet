package net.megx.pubmap.geonames.model;

public class Address {

  private String street;
  private String mtfcc;
  private int streetNumber;
  private double lat;
  private double lng;
  private double distance;
  private int postalcode;
  private String placename;
  private String adminCode2;
  private String adminName2;
  private String adminCode1;
  private String adminName1;
  private String countryCode;

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getMtfcc() {
    return mtfcc;
  }

  public void setMtfcc(String mtfcc) {
    this.mtfcc = mtfcc;
  }

  public int getStreetNumber() {
    return streetNumber;
  }

  public void setStreetNumber(int streetNumber) {
    this.streetNumber = streetNumber;
  }

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLng() {
    return lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }

  public double getDistance() {
    return distance;
  }

  public void setDistance(double distance) {
    this.distance = distance;
  }

  public int getPostalcode() {
    return postalcode;
  }

  public void setPostalcode(int postalcode) {
    this.postalcode = postalcode;
  }

  public String getPlacename() {
    return placename;
  }

  public void setPlacename(String placename) {
    this.placename = placename;
  }

  public String getAdminCode2() {
    return adminCode2;
  }

  public void setAdminCode2(String adminCode2) {
    this.adminCode2 = adminCode2;
  }

  public String getAdminName2() {
    return adminName2;
  }

  public void setAdminName2(String adminName2) {
    this.adminName2 = adminName2;
  }

  public String getAdminCode1() {
    return adminCode1;
  }

  public void setAdminCode1(String adminCode1) {
    this.adminCode1 = adminCode1;
  }

  public String getAdminName1() {
    return adminName1;
  }

  public void setAdminName1(String adminName1) {
    this.adminName1 = adminName1;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  @Override
  public String toString() {
    return "Address [street=" + street + ", mtfcc=" + mtfcc + ", streetNumber="
        + streetNumber + ", lat=" + lat + ", lng=" + lng + ", distance="
        + distance + ", postalcode=" + postalcode + ", placename=" + placename
        + ", adminCode2=" + adminCode2 + ", adminName2=" + adminName2
        + ", adminCode1=" + adminCode1 + ", adminName1=" + adminName1
        + ", countryCode=" + countryCode + "]";
  }

}
