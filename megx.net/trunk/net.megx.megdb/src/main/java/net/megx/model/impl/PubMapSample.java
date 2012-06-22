package net.megx.model.impl;

import java.math.BigDecimal;
import java.util.Date;

import net.megx.model.DateResolution;
import net.megx.model.Sample;

public class PubMapSample implements Sample {

	private String study;

	private String label;

	private BigDecimal maxUncertain;

	private Date dateTaken;

	private DateResolution dateRes;

	private String material;

	private String habitat;

	private String habLite;

	private String country;

	private Integer project;

	private String own;

	private Short pool;

	private String device;

	private String biome;

	private String feature;

	private BigDecimal latitude;

	private BigDecimal longitude;

	private Date created;

	private String createdBy;

	private Date updated;

	private String updatedBy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#getStudy()
	 */
	@Override
	public String getStudy() {
		return study;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#setStudy(java.lang.String)
	 */
	@Override
	public void setStudy(String study) {
		this.study = study;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#getLabel()
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#setLabel(java.lang.String)
	 */
	@Override
	public void setLabel(String label) {
		this.label = label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#getMaxUncertain()
	 */
	@Override
	public BigDecimal getMaxUncertain() {
		return maxUncertain;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#setMaxUncertain(java.math.BigDecimal)
	 */
	@Override
	public void setMaxUncertain(BigDecimal maxUncertain) {
		this.maxUncertain = maxUncertain;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#getDateTaken()
	 */
	@Override
	public Date getDateTaken() {
		return dateTaken;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#setDateTaken(java.util.Date)
	 */
	@Override
	public void setDateTaken(Date dateTaken) {
		this.dateTaken = dateTaken;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#getDateRes()
	 */
	@Override
	public DateResolution getDateRes() {
		return dateRes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#setDateRes(java.lang.String)
	 */
	@Override
	public void setDateRes(DateResolution dateRes) {
		this.dateRes = dateRes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#getMaterial()
	 */
	@Override
	public String getMaterial() {
		return material;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#setMaterial(java.lang.String)
	 */
	@Override
	public void setMaterial(String material) {
		this.material = material;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#getHabitat()
	 */
	@Override
	public String getHabitat() {
		return habitat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#setHabitat(java.lang.String)
	 */
	@Override
	public void setHabitat(String habitat) {
		this.habitat = habitat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#getHabLite()
	 */
	@Override
	public String getHabLite() {
		return habLite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#setHabLite(java.lang.String)
	 */
	@Override
	public void setHabLite(String habLite) {
		this.habLite = habLite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#getCountry()
	 */
	@Override
	public String getCountry() {
		return country;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#setCountry(java.lang.String)
	 */
	@Override
	public void setCountry(String country) {
		this.country = country;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#getProject()
	 */
	@Override
	public Integer getProject() {
		return project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#setProject(java.lang.Integer)
	 */
	@Override
	public void setProject(Integer project) {
		this.project = project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#getOwn()
	 */
	@Override
	public String getOwn() {
		return own;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#setOwn(java.lang.String)
	 */
	@Override
	public void setOwn(String own) {
		this.own = own;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#getPool()
	 */
	@Override
	public Short getPool() {
		return pool;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#setPool(java.lang.Short)
	 */
	@Override
	public void setPool(Short pool) {
		this.pool = pool;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#getDevice()
	 */
	@Override
	public String getDevice() {
		return device;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#setDevice(java.lang.String)
	 */
	@Override
	public void setDevice(String device) {
		this.device = device;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#getBiome()
	 */
	@Override
	public String getBiome() {
		return biome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#setBiome(java.lang.String)
	 */
	@Override
	public void setBiome(String biome) {
		this.biome = biome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#getFeature()
	 */
	@Override
	public String getFeature() {
		return feature;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.model.Sample#setFeature(java.lang.String)
	 */
	@Override
	public void setFeature(String feature) {
		this.feature = feature;
	}

	@Override
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	@Override
	public BigDecimal getLatitude() {
		return this.latitude;
	}

	@Override
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	@Override
	public BigDecimal getLongitude() {
		return this.longitude;
	}

	@Override
	public Date getCreated() {
		return this.created;
	}

	@Override
	public String getCreatedBy() {
		return createdBy;
	}



	@Override
	public Date getUpdated() {
		return updated;
	}

	@Override
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	@Override
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	@Override
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@Override
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String getRegion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRegion(String region) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDepth(BigDecimal depth, String unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal getDepth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDepthUnit() {
		// TODO Auto-generated method stub
		return null;
	}

}