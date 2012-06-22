package net.megx.pubmap.rest.json;

import java.math.BigDecimal;

import net.megx.model.ModelFactory;
import net.megx.model.Sample;

public class SampleDTO {
	private String label;
	private String region;
	private String longitude;
	private String latitude;
	private String material;
	private String depth;
	private String depthunit;
	private String samyear;
	private String sammonth;
	private String samday;
	private String samhour;
	private String sammin;
	private String samsec;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public String getDepthunit() {
		return depthunit;
	}
	public void setDepthunit(String depthunit) {
		this.depthunit = depthunit;
	}
	public String getSamyear() {
		return samyear;
	}
	public void setSamyear(String samyear) {
		this.samyear = samyear;
	}
	public String getSammonth() {
		return sammonth;
	}
	public void setSammonth(String sammonth) {
		this.sammonth = sammonth;
	}
	public String getSamday() {
		return samday;
	}
	public void setSamday(String samday) {
		this.samday = samday;
	}
	public String getSamhour() {
		return samhour;
	}
	public void setSamhour(String samhour) {
		this.samhour = samhour;
	}
	public String getSammin() {
		return sammin;
	}
	public void setSammin(String sammin) {
		this.sammin = sammin;
	}
	public String getSamsec() {
		return samsec;
	}
	public void setSamsec(String samsec) {
		this.samsec = samsec;
	}
	
	public Sample toDAO() {
		Sample sample = ModelFactory.createSample();
		sample.setLabel(this.label);
		sample.setRegion(this.region);
		BigDecimal lat = new BigDecimal(latitude);
		BigDecimal lon = new BigDecimal(longitude);
		sample.setLatitude(lat);
		sample.setLongitude(lon);
		
		
		sample.setMaterial(this.material);
		
		//sample.setDepth
		//sample.setDepthunit
		// sample.setYear, month day min sec ...
		return sample;
	}
	
	public static SampleDTO fromDAO(Sample sample) {
		SampleDTO rv = new SampleDTO();
		rv.label = sample.getLabel();
		rv.region = sample.getRegion();
		BigDecimal lat = sample.getLatitude();
		if(lat != null) {
			rv.latitude = lat.toPlainString();
		}
		BigDecimal lon = sample.getLongitude();
		if(lon != null) {
			rv.longitude = lon.toPlainString();
		}
		return rv;
	}	
}
