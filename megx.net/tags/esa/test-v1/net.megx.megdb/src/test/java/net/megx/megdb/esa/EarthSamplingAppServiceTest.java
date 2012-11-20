package net.megx.megdb.esa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.megx.megdb.esa.impl.DBEarthSamplingAppService;
import net.megx.model.esa.Sample;
import net.megx.model.esa.SamplePhoto;
import net.megx.security.services.DBServiceTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EarthSamplingAppServiceTest extends DBServiceTest{

private EarthSamplingAppService earthAppService;
	
	public static String FIRST_ID = "1";
	public static String SECOND_ID = "2";
	public static String THIRD_ID = "3";
	
	public static Date TAKEN = new Date();
	public static Date MODIFIED = new Date();
	public static String COLLECTOR_ID = "TEST COLLECTOR ID";
	public static String LABEL = "TEST LABEL";
	public static String RAW_DATA = "JSON REP OF SAMPLE";
	public static String BARCODE = "TEST BARCODE";
	public static String PROJECT_ID = "TEST PROJECT ID";
	public static String USERNAME = "TEST USERNAME";
	public static String SHIPNAME = "TEST SHIP NAME";
	public static String NATIONALITY = "TEST NATIONALITY";
	public static double ELEVATION = 123.456;
	public static String BIOME = "TEST BIOME";
	public static String FEATURE = "TEST FEATURE";
	public static String COLLECTION = "TEST COLLECTION";
	public static String PERMIT = "TEST PERMIT";
	public static double SAMPLING_DEPTH = 111.222;
	public static double WATER_DEPTH = 6.2;
	public static int SAMPLE_SIZE = 4;
	public static String WEATHER_CONDITION = "TEST WEATHER CONDITION";
	public static double AIR_TEMPERATURE = 14.98;
	public static double WATER_TEMPERATURE = 89.3;
	public static String CONDUCTIVITY = "TEST CONDUCTIVITY";
	public static double WIND_SPEED = 5.6;
	public static double SALINITY = 0.29;
	public static String COMMENT = "TEST COMMENT";
	public static double LAT = 41.01;
	public static double LON = 21.20;
	
	public static String FIRST_CATEGORY = "TEST CATEGORY";
	public static String SECOND_CATEGORY = "SECOND TEST CATEGORY";
	public static String THIRD_CATEGORY = "THIRD TEST CATEGORY";
	
	public static String FIRST_NAME = "FIRST TEST NAME";
	public static String SECOND_NAME = "SECOND TEST NAME";
	public static String THIRD_NAME = "THIRD NAME";
	
	public static String FIRST_VALUE = "TEST VALUE";
	public static String SECOND_VALUE = "SECOND TEST VALUE";
	public static String THIRD_VALUE = "THIRD VALUE";
	
	public static String FIRST_UUID = "FIRST TEST UUID";
	public static String SECOND_UUID = "SECOND TEST UUID";
	public static String THIRD_UUID = "THIRD TEST UUID";
	public static String PATH = "TEST PATH";
	public static String MIME_TYPE = "TEST MIME TYPE";
	
	private Sample createNewSample(String id){
		Sample sample = new Sample();
		sample.setId(id);
		sample.setAirTemperature(AIR_TEMPERATURE);
		sample.setBarcode(BARCODE);
		sample.setBiome(BIOME);
		sample.setCollection(COLLECTION);
		sample.setCollectorId(COLLECTOR_ID);
		sample.setComment(COMMENT);
		sample.setConductivity(CONDUCTIVITY);
		sample.setElevation(ELEVATION);
		sample.setFeature(FEATURE);
		sample.setLabel(LABEL);
		sample.setLat(LAT);
		sample.setLon(LON);
		sample.setModified(MODIFIED);
		sample.setNationality(NATIONALITY);
		sample.setPermit(PERMIT);
		sample.setProjectId(PROJECT_ID);
		sample.setRawData(RAW_DATA);
		sample.setSalinity(SALINITY);
		sample.setSampleSize(SAMPLE_SIZE);
		sample.setSamplingDepth(SAMPLING_DEPTH);
		sample.setShipName(SHIPNAME);
		sample.setTaken(TAKEN);
		sample.setUserName(USERNAME);
		sample.setWaterDepth(WATER_DEPTH);
		sample.setWaterTemerature(WATER_TEMPERATURE);
		sample.setWeatherCondition(WEATHER_CONDITION);	
		sample.setWindSpeed(WIND_SPEED);
		SamplePhoto[] samplePhotos = {createNewPhoto(), createNewPhoto(), createNewPhoto()};
		sample.setPhotos(samplePhotos);
		return sample;
	}

	private SamplePhoto createNewPhoto(){
		SamplePhoto photoToReturn = new SamplePhoto();
		photoToReturn.setUuid(UUID.randomUUID().toString());
		photoToReturn.setMimeType(MIME_TYPE);
		
		return photoToReturn;
	}
	
	private Sample defaultSample, secondSample, thirdSample;
	
	@Before
	public void setup() throws Exception{
		earthAppService = buildService(DBEarthSamplingAppService.class);
		
		defaultSample = createNewSample(FIRST_ID);
		secondSample = createNewSample(SECOND_ID);
		thirdSample = createNewSample(THIRD_ID);
	}
	
	@Test
	public void storeMultipleSamples() throws Exception{
		List<Sample> samplesToStore = new ArrayList<Sample>();
		samplesToStore.add(defaultSample);
		samplesToStore.add(secondSample);
		samplesToStore.add(thirdSample);
		earthAppService.storeSamples(samplesToStore);
		
		List<Sample> retrievedSamples = earthAppService.getSamples(COLLECTOR_ID);
		Assert.assertNotNull(retrievedSamples);
		Assert.assertTrue(retrievedSamples.size() > 0 && retrievedSamples.size() == 3);
		
	}
	
	@Test
	public void storeSingleSample() throws Exception{
		List<Sample> samplesToStore = new ArrayList<Sample>();
		samplesToStore.add(defaultSample);
		earthAppService.storeSamples(samplesToStore);
		
		List<Sample> retrievedSamples = earthAppService.getSamples(COLLECTOR_ID);
		Assert.assertNotNull(retrievedSamples);
		Assert.assertTrue(retrievedSamples.size() > 0 && retrievedSamples.size() == 1);
		Assert.assertEquals(defaultSample, retrievedSamples.get(0));
	}
	
	@Test
	public void storeConfiguration() throws Exception{
		Map<String, String> configurationsToSave = new HashMap<String, String>();
		configurationsToSave.put(FIRST_NAME, FIRST_VALUE);
		configurationsToSave.put(SECOND_NAME, SECOND_VALUE);
		configurationsToSave.put(THIRD_NAME, THIRD_VALUE);
		
		earthAppService.storeConfiguration(FIRST_CATEGORY, configurationsToSave);
		
		Map<String, String> retrievedConfiguration = earthAppService.getConfiguration(FIRST_CATEGORY);
		Assert.assertTrue(retrievedConfiguration.containsKey(FIRST_NAME));
		Assert.assertTrue(retrievedConfiguration.containsKey(SECOND_NAME));
		Assert.assertTrue(retrievedConfiguration.containsKey(THIRD_NAME));
		Assert.assertTrue(retrievedConfiguration.containsValue(FIRST_VALUE));
		Assert.assertTrue(retrievedConfiguration.containsValue(SECOND_VALUE));
		Assert.assertTrue(retrievedConfiguration.containsValue(THIRD_VALUE));
	}
	
	@Test
	public void store() throws Exception{
		earthAppService.store(SECOND_CATEGORY, FIRST_NAME, THIRD_VALUE);
		earthAppService.store(THIRD_CATEGORY, SECOND_NAME, FIRST_VALUE);
		earthAppService.store(FIRST_CATEGORY, THIRD_NAME, SECOND_VALUE);
		
		Map<String, String> firstConfiguration = earthAppService.getConfiguration(SECOND_CATEGORY);
		Assert.assertTrue(firstConfiguration.containsKey(FIRST_NAME));
		Assert.assertTrue(firstConfiguration.containsValue(THIRD_VALUE));
		
		Map<String, String> secondConfiguration = earthAppService.getConfiguration(THIRD_CATEGORY);
		Assert.assertTrue(secondConfiguration.containsKey(SECOND_NAME));
		Assert.assertTrue(secondConfiguration.containsValue(FIRST_VALUE));
		
		Map<String, String> thirdConfiguration = earthAppService.getConfiguration(FIRST_CATEGORY);
		Assert.assertTrue(thirdConfiguration.containsKey(THIRD_NAME));
		Assert.assertTrue(thirdConfiguration.containsValue(SECOND_VALUE));
	}
	
	@Test
	public void storePhotos() throws Exception{
		List<Sample> samplesToStore = new ArrayList<Sample>();
		samplesToStore.add(defaultSample);
		earthAppService.storeSamples(samplesToStore);
		
		List<SamplePhoto> savedPhotos = earthAppService.getSamplePhotos(defaultSample.getId());
		Assert.assertTrue(savedPhotos.size() == 3);
		Assert.assertTrue(savedPhotos.get(0).getData() == null);
		Assert.assertTrue(savedPhotos.get(0).getPath() == null);
		Assert.assertTrue(savedPhotos.get(1).getData() == null);
		Assert.assertTrue(savedPhotos.get(1).getPath() == null);
		Assert.assertTrue(savedPhotos.get(2).getData() == null);
		Assert.assertTrue(savedPhotos.get(2).getPath() == null);
		
		savedPhotos.get(0).setData(FIRST_ID.getBytes());
		savedPhotos.get(0).setPath(PATH);
		
		savedPhotos.get(1).setData(SECOND_ID.getBytes());
		savedPhotos.get(1).setPath(PATH);
		
		savedPhotos.get(2).setData(THIRD_ID.getBytes());
		savedPhotos.get(2).setPath(PATH);
		
		List<String> updatedPhotosIds = earthAppService.storePhotos(savedPhotos);
		Assert.assertTrue(updatedPhotosIds.size() > 0 && updatedPhotosIds.size() == 3);
		
		List<SamplePhoto> updatedPhotos = earthAppService.getSamplePhotos(defaultSample.getId());
		Assert.assertTrue(updatedPhotos.size() == 3);
		Assert.assertTrue(Arrays.equals(updatedPhotos.get(0).getData(), FIRST_ID.getBytes()));
		Assert.assertTrue(updatedPhotos.get(0).getPath().equals(PATH));
		Assert.assertTrue(Arrays.equals(updatedPhotos.get(1).getData(), SECOND_ID.getBytes()));
		Assert.assertTrue(updatedPhotos.get(1).getPath().equals(PATH));
		Assert.assertTrue(Arrays.equals(updatedPhotos.get(2).getData(), THIRD_ID.getBytes()));
		Assert.assertTrue(updatedPhotos.get(2).getPath().equals(PATH));
	}
	
	@After
	public void tearDown() throws Exception{
		try{
			earthAppService.removeSample(FIRST_ID);
			earthAppService.removeSample(SECOND_ID);
			earthAppService.removeSample(THIRD_ID);
			earthAppService.clearConfig(FIRST_CATEGORY);
			earthAppService.clearConfig(SECOND_CATEGORY);
			earthAppService.clearConfig(THIRD_CATEGORY);
		}
		catch(Exception e)
		{
			
		}
	}
	
}
