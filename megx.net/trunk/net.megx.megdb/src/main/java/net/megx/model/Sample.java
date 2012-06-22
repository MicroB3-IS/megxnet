package net.megx.model;

import java.math.BigDecimal;
import java.util.Date;

public interface Sample extends SimpleAudit {

	public String getStudy();

	public void setStudy(String study);

	public String getLabel();

	public void setLabel(String label);

	public BigDecimal getMaxUncertain();

	public void setMaxUncertain(BigDecimal maxUncertain);

	public Date getDateTaken();

	public void setDateTaken(Date dateTaken);

	public DateResolution getDateRes();

	public void setDateRes(DateResolution dateRes);

	public String getMaterial();

	public void setMaterial(String material);

	public String getHabitat();

	public void setHabitat(String habitat);

	public String getHabLite();

	public void setHabLite(String habLite);

	public String getCountry();

	public void setCountry(String country);

	public Integer getProject();

	public void setProject(Integer project);

	public String getOwn();

	public void setOwn(String own);

	public Short getPool();

	public void setPool(Short pool);

	public String getDevice();

	public void setDevice(String device);

	public String getBiome();

	public void setBiome(String biome);

	public String getFeature();

	public void setFeature(String feature);

	public void setLatitude(BigDecimal latitude);

	public BigDecimal getLatitude();

	public void setLongitude(BigDecimal longitude);

	public BigDecimal getLongitude();
	
	public String getRegion();
	
	public void setRegion(String region);
	
	public void setDepth(BigDecimal depth, String unit);
	
	public BigDecimal getDepth();
	
	public String getDepthUnit();
}