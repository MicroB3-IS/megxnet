package net.megx.model.impl;

import java.math.BigDecimal;
import java.util.Date;

import net.megx.model.Sample;

public class PubMapSamples implements Sample {

	private String study;

    private String label;

    private BigDecimal maxUncertain;

    private Date dateTaken;

    private String dateRes;

    private String material;

    private String habitat;

    private String habLite;

    private String country;

    private Integer project;

    private String own;

    private Short pool;

    private Integer id;

    private Object geom;

    private String device;

    private String biome;

    private String feature;

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#getStudy()
	 */
    @Override
	public String getStudy() {
        return study;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#setStudy(java.lang.String)
	 */
    @Override
	public void setStudy(String study) {
        this.study = study;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#getLabel()
	 */
    @Override
	public String getLabel() {
        return label;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#setLabel(java.lang.String)
	 */
    @Override
	public void setLabel(String label) {
        this.label = label;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#getMaxUncertain()
	 */
    @Override
	public BigDecimal getMaxUncertain() {
        return maxUncertain;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#setMaxUncertain(java.math.BigDecimal)
	 */
    @Override
	public void setMaxUncertain(BigDecimal maxUncertain) {
        this.maxUncertain = maxUncertain;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#getDateTaken()
	 */
    @Override
	public Date getDateTaken() {
        return dateTaken;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#setDateTaken(java.util.Date)
	 */
    @Override
	public void setDateTaken(Date dateTaken) {
        this.dateTaken = dateTaken;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#getDateRes()
	 */
    @Override
	public String getDateRes() {
        return dateRes;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#setDateRes(java.lang.String)
	 */
    @Override
	public void setDateRes(String dateRes) {
        this.dateRes = dateRes;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#getMaterial()
	 */
    @Override
	public String getMaterial() {
        return material;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#setMaterial(java.lang.String)
	 */
    @Override
	public void setMaterial(String material) {
        this.material = material;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#getHabitat()
	 */
    @Override
	public String getHabitat() {
        return habitat;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#setHabitat(java.lang.String)
	 */
    @Override
	public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#getHabLite()
	 */
    @Override
	public String getHabLite() {
        return habLite;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#setHabLite(java.lang.String)
	 */
    @Override
	public void setHabLite(String habLite) {
        this.habLite = habLite;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#getCountry()
	 */
    @Override
	public String getCountry() {
        return country;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#setCountry(java.lang.String)
	 */
    @Override
	public void setCountry(String country) {
        this.country = country;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#getProject()
	 */
    @Override
	public Integer getProject() {
        return project;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#setProject(java.lang.Integer)
	 */
    @Override
	public void setProject(Integer project) {
        this.project = project;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#getOwn()
	 */
    @Override
	public String getOwn() {
        return own;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#setOwn(java.lang.String)
	 */
    @Override
	public void setOwn(String own) {
        this.own = own;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#getPool()
	 */
    @Override
	public Short getPool() {
        return pool;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#setPool(java.lang.Short)
	 */
    @Override
	public void setPool(Short pool) {
        this.pool = pool;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#getId()
	 */
    @Override
	public Integer getId() {
        return id;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#setId(java.lang.Integer)
	 */
    @Override
	public void setId(Integer id) {
        this.id = id;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#getGeom()
	 */
    @Override
	public Object getGeom() {
        return geom;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#setGeom(java.lang.Object)
	 */
    @Override
	public void setGeom(Object geom) {
        this.geom = geom;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#getDevice()
	 */
    @Override
	public String getDevice() {
        return device;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#setDevice(java.lang.String)
	 */
    @Override
	public void setDevice(String device) {
        this.device = device;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#getBiome()
	 */
    @Override
	public String getBiome() {
        return biome;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#setBiome(java.lang.String)
	 */
    @Override
	public void setBiome(String biome) {
        this.biome = biome;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#getFeature()
	 */
    @Override
	public String getFeature() {
        return feature;
    }

    /* (non-Javadoc)
	 * @see net.megx.model.Sample#setFeature(java.lang.String)
	 */
    @Override
	public void setFeature(String feature) {
        this.feature = feature;
    }
}