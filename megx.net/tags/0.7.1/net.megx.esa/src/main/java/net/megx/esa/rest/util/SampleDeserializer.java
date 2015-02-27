package net.megx.esa.rest.util;

import java.lang.reflect.Type;
import java.util.Date;

import net.megx.model.esa.Sample;
import net.megx.model.esa.SamplePhoto;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class SampleDeserializer implements JsonDeserializer<Sample>{

	/*
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
	private double accuracy;
	 
	 */
	private static final String [] REQUIRED = {"id","taken","modified","collectorId","label","lat","lon", "permit"};
	@Override
	public Sample deserialize(JsonElement el, Type type,
			JsonDeserializationContext ctx) throws JsonParseException {
		
		Sample sample = new Sample();
		if(!el.isJsonObject()){
			throw new JsonParseException("Expected JSON Object. Got: " + (el != null ? el.getAsString() : "null") + " instead.");
		}
		JsonObject jo = el.getAsJsonObject();
		for(String required: REQUIRED){
			if(!jo.has(required) || jo.get(required).isJsonNull()){
				throw new JsonParseException("Required property: " + required + " is missing.");
			}
		}
		
		sample.setId(getString("id", jo));
		try{
			sample.setTaken((Date)ctx.deserialize(jo.get("time"), Date.class));
		}catch(Exception e){
			throw new JsonParseException("The sample taken date is invalid.",e);
		}
		try{
			sample.setModified((Date)ctx.deserialize(jo.get("modified"), Date.class));
		}catch(Exception e){
			throw new JsonParseException("The sample modified date is invalid.",e);
		}
		sample.setCollectorId(getString("collectorId", jo));
		sample.setLabel(getString("label", jo));
		
		sample.setLat(parseDouble(getString("lat", jo)));
		sample.setLon(parseDouble(getString("lon", jo)));
		
		sample.setRawData(jo.toString());
		
		sample.setBarcode(getString("barcode", jo));
		sample.setProjectId(getString("projectId", jo));
		sample.setUserName(getString("userName", jo));
		sample.setShipName(getString("shipName", jo));
		sample.setBoatManufacturer(getString("boatManufacturer", jo));
		sample.setBoatModel(getString("boatModel", jo));
		sample.setBoatLength(parseDouble(getString("boatLength", jo)));
		sample.setHomeport(getString("homeport", jo));
		sample.setNationality(getString("nationality", jo));
		sample.setMaterial(getString("material", jo));
		
		sample.setBiome(getString("biome", jo));
		sample.setFeature(getString("feature", jo));
		sample.setCollection(getString("collectionMethod", jo));
		sample.setPermit(getString("permit", jo));
		sample.setWeatherCondition(getString("weatherCondition", jo));
		sample.setConductivity(getString("conductivity", jo));
		sample.setComment(getString("comment", jo));
		sample.setAppVersion(getString("appVersion", jo));
		sample.setFun(Boolean.valueOf(getString("fun", jo)));
		
		sample.setAccuracy(parseDouble(getString("accuracy", jo)));
		sample.setAirTemperature(parseDouble(getString("airTemperature", jo)));
		sample.setWaterTemperature(parseDouble(getString("waterTemperature", jo)));
		sample.setSamplingDepth(parseDouble(getString("samplingDepth", jo)));
		sample.setWaterDepth(parseDouble(getString("waterDepth", jo)));
		sample.setElevation(parseDouble(getString("elevation", jo)));
		sample.setWindSpeed(parseDouble(getString("windSpeed", jo)));
		sample.setSalinity(parseDouble(getString("salinity", jo)));
		sample.setSampleSize(parseInt(getString("sampleSize", jo)));
		sample.setSecchiDepth(parseDouble(getString("secchiDepth", jo)));
		sample.setSamplingKit(Boolean.valueOf(getString("samplingKit", jo)));
		sample.setMyosdNumber(parseInt(getString("myosdNumber", jo)));
		sample.setFilterOne(parseDouble(getString("filterOne", jo)));
		sample.setFilterTwo(parseDouble(getString("filterTwo", jo)));
		
		//Citizen app specific fields
		sample.setPhosphate(parseDouble(getString("phosphate", jo)));
		sample.setNitrate(parseDouble(getString("nitrate", jo)));
		sample.setNitrite(parseDouble(getString("nitrite", jo)));
		sample.setPh(parseDouble(getString("ph", jo)));
		
		try{
			if(jo.has("photos")){
				sample.setPhotos((SamplePhoto[])ctx.deserialize(jo.get("photos"), SamplePhoto[].class));
			}
		}catch (Exception e) {
			throw new JsonParseException("Failed to parse the photos data for sample: " + sample.getId(), e);
		}
		
		return sample;
	}
	
	private String getString(String name, JsonObject jo){
		String s = null;
		if(jo.has(name)){
			if(!jo.get(name).isJsonNull())
				s = jo.get(name).getAsString();
		}
		return s;
	}
	
	
	private Double parseDouble(String dbl){
		Double d = null;
		try{
			if(dbl != null){
				d = Double.parseDouble(dbl);
			}
		}catch (Exception e) {
			// Ignore
		}
		return d;
	}
	
	private Integer parseInt(String nmb){
		Integer i = null;
		try{
			if(nmb != null){
				i = Integer.parseInt(nmb);
			}
		}catch (Exception e) {
			// Ignore
		}
		return i;
	}
	
}
