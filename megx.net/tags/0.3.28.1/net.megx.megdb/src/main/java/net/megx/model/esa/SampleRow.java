package net.megx.model.esa;

import java.util.Arrays;
import java.util.Date;

public class SampleRow {
	private String id;
	
	private Date taken;
	private Date modified;
	private String collectorId;
	private String label;
	
	private String barcode;
	
	private Double elevation;
	private String biome;
	private String feature;
	private String collection;
	private String permit;
	private String material;
	private Double secchiDepth;
	private Double samplingDepth;
	private Double waterDepth;
	private Integer sampleSize;
	private String weatherCondition;
	private Double airTemperature;
	private Double waterTemperature;
	private String conductivity;
	private Double windSpeed;
	private Double salinity;
	private String comment;
	private Double lat;
	private Double lon;
	private Double accuracy;
	private Double phosphate;
	private Double nitrate;
	private Double nitrite;
	private Double ph;
	
	public Double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Double accuracy) {
		this.accuracy = accuracy;
	}
	private SamplePhoto [] photos;
	
	public SamplePhoto[] getPhotos() {
		return photos;
	}
	public void setPhotos(SamplePhoto[] photos) {
		this.photos = photos;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getTaken() {
		return taken;
	}
	public void setTaken(Date taken) {
		this.taken = taken;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public String getCollectorId() {
		return collectorId;
	}
	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	public Double getElevation() {
		return elevation;
	}
	public void setElevation(Double elevation) {
		this.elevation = elevation;
	}
	public String getBiome() {
		return biome;
	}
	public void setBiome(String biome) {
		this.biome = biome;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public String getCollection() {
		return collection;
	}
	public void setCollection(String collecton) {
		this.collection = collecton;
	}
	public String getPermit() {
		return permit;
	}
	public void setPermit(String permit) {
		this.permit = permit;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public Double getSecchiDepth() {
		return secchiDepth;
	}
	public void setSecchiDepth(Double secchiDepth) {
		this.secchiDepth = secchiDepth;
	}
	public Double getSamplingDepth() {
		return samplingDepth;
	}
	public void setSamplingDepth(Double samplingDepth) {
		this.samplingDepth = samplingDepth;
	}
	public Double getWaterDepth() {
		return waterDepth;
	}
	public void setWaterDepth(Double waterDepth) {
		this.waterDepth = waterDepth;
	}
	public Integer getSampleSize() {
		return sampleSize;
	}
	public void setSampleSize(Integer sampleSize) {
		this.sampleSize = sampleSize;
	}
	public String getWeatherCondition() {
		return weatherCondition;
	}
	public void setWeatherCondition(String weatherCondition) {
		this.weatherCondition = weatherCondition;
	}
	public Double getAirTemperature() {
		return airTemperature;
	}
	public void setAirTemperature(Double airTemperature) {
		this.airTemperature = airTemperature;
	}
	public Double getWaterTemperature() {
		return waterTemperature;
	}
	public void setWaterTemperature(Double waterTemperature) {
		this.waterTemperature = waterTemperature;
	}
	public String getConductivity() {
		return conductivity;
	}
	public void setConductivity(String conductivity) {
		this.conductivity = conductivity;
	}
	public Double getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(Double windSpeed) {
		this.windSpeed = windSpeed;
	}
	public Double getSalinity() {
		return salinity;
	}
	public void setSalinity(Double salinity) {
		this.salinity = salinity;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	public Double getPhosphate() {
		return phosphate;
	}
	public void setPhosphate(Double phosphate) {
		this.phosphate = phosphate;
	}
	public Double getNitrate() {
		return nitrate;
	}
	public void setNitrate(Double nitrate) {
		this.nitrate = nitrate;
	}
	public Double getNitrite() {
		return nitrite;
	}
	public void setNitrite(Double nitrite) {
		this.nitrite = nitrite;
	}
	public Double getPh() {
		return ph;
	}
	public void setPh(Double ph) {
		this.ph = ph;
	}
	@Override
	public int hashCode() {
		final Integer prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		SampleRow other = (SampleRow) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SampleRow [id=" + id + ", taken=" + taken + ", modified="
				+ modified + ", collectorId=" + collectorId + ", label="
				+ label + ", barcode=" + barcode + ", elevation=" + elevation
				+ ", biome=" + biome + ", feature=" + feature + ", collection="
				+ collection + ", permit=" + permit + ", material=" + material
				+ ", secchiDepth=" + secchiDepth + ", samplingDepth="
				+ samplingDepth + ", waterDepth=" + waterDepth
				+ ", sampleSize=" + sampleSize + ", weatherCondition="
				+ weatherCondition + ", airTemperature=" + airTemperature
				+ ", waterTemperature=" + waterTemperature + ", conductivity="
				+ conductivity + ", windSpeed=" + windSpeed + ", salinity="
				+ salinity + ", comment=" + comment + ", lat=" + lat + ", lon="
				+ lon + ", accuracy=" + accuracy + ", phosphate=" + phosphate
				+ ", nitrate=" + nitrate + ", nitrite=" + nitrite + ", ph="
				+ ph + ", photos=" + Arrays.toString(photos) + "]";
	}
}
