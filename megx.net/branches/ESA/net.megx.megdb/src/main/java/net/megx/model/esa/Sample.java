package net.megx.model.esa;

import java.util.Date;

public class Sample {
	private String id;
	
	private Date taken;
	private Date modified;
	private String collectorId;
	private String label;
	private transient String rawData;
	
	
	private String barcode;
	private String projectId;
	private String userName;
	private String shipName;
	private String nationality;
	
	private double elevation;
	private String biome;
	private String feature;
	private String collection;
	private String permit;
	private double samplingDepth;
	private double waterDepth;
	private int sampleSize;
	private String weatherCondition;
	private double airTemperature;
	private double waterTemerature;
	private String conductivity;
	private double windSpeed;
	private double salinity;
	private String comment;
	private double lat;
	private double lon;
	
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
	public String getRawData() {
		return rawData;
	}
	public void setRawData(String rawData) {
		this.rawData = rawData;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getShipName() {
		return shipName;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public double getElevation() {
		return elevation;
	}
	public void setElevation(double elevation) {
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
	public double getSamplingDepth() {
		return samplingDepth;
	}
	public void setSamplingDepth(double samplingDepth) {
		this.samplingDepth = samplingDepth;
	}
	public double getWaterDepth() {
		return waterDepth;
	}
	public void setWaterDepth(double waterDepth) {
		this.waterDepth = waterDepth;
	}
	public int getSampleSize() {
		return sampleSize;
	}
	public void setSampleSize(int sampleSize) {
		this.sampleSize = sampleSize;
	}
	public String getWeatherCondition() {
		return weatherCondition;
	}
	public void setWeatherCondition(String weatherCondition) {
		this.weatherCondition = weatherCondition;
	}
	public double getAirTemperature() {
		return airTemperature;
	}
	public void setAirTemperature(double airTemperature) {
		this.airTemperature = airTemperature;
	}
	public double getWaterTemerature() {
		return waterTemerature;
	}
	public void setWaterTemerature(double waterTemerature) {
		this.waterTemerature = waterTemerature;
	}
	public String getConductivity() {
		return conductivity;
	}
	public void setConductivity(String conductivity) {
		this.conductivity = conductivity;
	}
	public double getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}
	public double getSalinity() {
		return salinity;
	}
	public void setSalinity(double salinity) {
		this.salinity = salinity;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
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
		Sample other = (Sample) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
