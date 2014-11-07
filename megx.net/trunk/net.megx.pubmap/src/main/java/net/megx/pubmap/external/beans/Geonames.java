package net.megx.pubmap.external.beans;

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
