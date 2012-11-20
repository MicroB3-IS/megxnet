package net.megx.megdb.esa.mappers;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.megx.model.esa.Sample;
import net.megx.model.esa.SamplePhoto;

import org.apache.ibatis.annotations.Param;

public interface ESAMapper {
	public Sample getSample(String id);
	public List<Sample> getSamples(String collectorId);
	
	/**
	 * Adds entry to the samples table
	 * @param sample
	 */
	public void addSample(Sample sample);
	
	/**
	 * Adds entry into the photos table
	 * @param photo
	 */
	public void addPhoto(
			@Param("photo")   SamplePhoto photo, 
			@Param("sampleId") String sampleId);
	
	/**
	 * Generally used when the photo data is received
	 * @param photo
	 */
	public void updatePhoto(SamplePhoto photo);
	
	public List<SamplePhoto> getPhotosForSample(String sampleId);
	
	public void removeSample(String id);
	
	public List<Map<String, String>> getConfiguration(String category);
	public void storeConfiguration(
			@Param("category") String category, 
			@Param("config")   Set<Map.Entry<String, String>> config);
	
	public String getConfigValue(
			@Param("category") String category, 
			@Param("name")    String name);
	
	
	public void storeConfigValue(
			@Param("category") String category, 
			@Param("name")     String name,
			@Param("value")    String value);
	
	public void clearConfigValue(
			@Param("category") String category, 
			@Param("name")     String name);
	
	public void clearConfig(String category);
}
