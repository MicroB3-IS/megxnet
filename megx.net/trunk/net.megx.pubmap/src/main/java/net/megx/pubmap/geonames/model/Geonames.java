package net.megx.pubmap.geonames.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "geonames")
@XmlAccessorType(XmlAccessType.FIELD)
public class Geonames {

	@XmlElement(name = "geoname")
	private List<Geoname> geonamesLst;

	private Ocean ocean;

	private Address address;

	private String countryName;

	private Status status;

	private Integer totalResultsCount;

	public Integer getTotalResultsCount() {
		return totalResultsCount;
	}

	public void setTotalResultsCount(Integer totalResultsCount) {
		this.totalResultsCount = totalResultsCount;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Geoname> getGeonamesLst() {
		return geonamesLst;
	}

	public void setGeonamesLst(List<Geoname> geonamesLst) {
		this.geonamesLst = geonamesLst;
	}

	public Ocean getOcean() {
		return ocean;
	}

	public void setOcean(Ocean ocean) {
		this.ocean = ocean;
	}

}
