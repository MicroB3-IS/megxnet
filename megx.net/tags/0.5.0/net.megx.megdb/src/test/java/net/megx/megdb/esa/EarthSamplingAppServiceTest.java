package net.megx.megdb.esa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
	
	private static String FIRST_ID = "1";
	private static String SECOND_ID = "2";
	private static String THIRD_ID = "3";
	
	private static Date TAKEN = new Date();
	private static Date MODIFIED = new Date();
	private static String COLLECTOR_ID = "TEST COLLECTOR ID";
	private static String LABEL = "TEST LABEL";
	private static String RAW_DATA = "JSON REP OF SAMPLE";
	private static String BARCODE = "TEST BARCODE";
	private static String PROJECT_ID = "TEST PROJECT ID";
	private static String USERNAME = "TEST USERNAME";
	private static String SHIPNAME = "TEST SHIP NAME";
	private static String NATIONALITY = "TEST NATIONALITY";
	private static double ELEVATION = 123.456;
	private static String BIOME = "TEST BIOME";
	private static String FEATURE = "TEST FEATURE";
	private static String COLLECTION = "TEST COLLECTION";
	private static String PERMIT = "TEST PERMIT";
	private static double SAMPLING_DEPTH = 111.222;
	private static double WATER_DEPTH = 6.2;
	private static int SAMPLE_SIZE = 4;
	private static String WEATHER_CONDITION = "TEST WEATHER CONDITION";
	private static double AIR_TEMPERATURE = 14.98;
	private static double WATER_TEMPERATURE = 89.3;
	private static String CONDUCTIVITY = "TEST CONDUCTIVITY";
	private static double WIND_SPEED = 5.6;
	private static double SALINITY = 0.29;
	private static String COMMENT = "TEST COMMENT";
	private static double LAT = 41.01;
	private static double LON = 21.20;
	private static double ACCURACY = 5.9;
	
	private static String FIRST_CATEGORY = "TEST CATEGORY";
	private static String SECOND_CATEGORY = "SECOND TEST CATEGORY";
	private static String THIRD_CATEGORY = "THIRD TEST CATEGORY";
	
	private static String FIRST_NAME = "FIRST TEST NAME";
	private static String SECOND_NAME = "SECOND TEST NAME";
	private static String THIRD_NAME = "THIRD NAME";
	
	private static String FIRST_VALUE = "TEST VALUE";
	private static String SECOND_VALUE = "SECOND TEST VALUE";
	private static String THIRD_VALUE = "THIRD VALUE";
	
	private static String FIRST_UUID = "FIRST TEST UUID";
	private static String SECOND_UUID = "SECOND TEST UUID";
	private static String THIRD_UUID = "THIRD TEST UUID";
	private static String PATH = "TEST PATH";
	private static String MIME_TYPE = "TEST MIME TYPE";
	
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
		sample.setWaterTemperature(WATER_TEMPERATURE);
		sample.setWeatherCondition(WEATHER_CONDITION);	
		sample.setWindSpeed(WIND_SPEED);
		sample.setAccuracy(ACCURACY);
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
	public void getSample() throws Exception{
		List<Sample> samplesToStore = new ArrayList<Sample>();
		samplesToStore.add(defaultSample);
		samplesToStore.add(secondSample);
		samplesToStore.add(thirdSample);
		earthAppService.storeSamples(samplesToStore);
		
		Assert.assertNotNull(earthAppService.getSample(defaultSample.getId()));
		Assert.assertNotNull(earthAppService.getSample(secondSample.getId()));
		Assert.assertNotNull(earthAppService.getSample(thirdSample.getId()));
		
		compareSamplesProperties(defaultSample, earthAppService.getSample(defaultSample.getId()));
		compareSamplesProperties(secondSample, earthAppService.getSample(secondSample.getId()));
		compareSamplesProperties(thirdSample, earthAppService.getSample(thirdSample.getId()));
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
		
		compareSamplesProperties(defaultSample, retrievedSamples.get(0));
	}
	
//	@Test
//	public void storeConfiguration() throws Exception{
//		Map<String, String> configurationsToSave = new HashMap<String, String>();
//		configurationsToSave.put(FIRST_NAME, FIRST_VALUE);
//		configurationsToSave.put(SECOND_NAME, SECOND_VALUE);
//		configurationsToSave.put(THIRD_NAME, THIRD_VALUE);
//		
//		earthAppService.storeConfiguration(FIRST_CATEGORY, configurationsToSave);
//		
//		Map<String, String> retrievedConfiguration = earthAppService.getConfiguration(FIRST_CATEGORY);
//		Assert.assertTrue(retrievedConfiguration.containsKey(FIRST_NAME));
//		Assert.assertTrue(retrievedConfiguration.containsKey(SECOND_NAME));
//		Assert.assertTrue(retrievedConfiguration.containsKey(THIRD_NAME));
//		Assert.assertTrue(retrievedConfiguration.containsValue(FIRST_VALUE));
//		Assert.assertTrue(retrievedConfiguration.containsValue(SECOND_VALUE));
//		Assert.assertTrue(retrievedConfiguration.containsValue(THIRD_VALUE));
//	}
	
//	@Test
//	public void clearConfig() throws Exception{
//		Map<String, String> configurationsToSave = new HashMap<String, String>();
//		configurationsToSave.put(FIRST_NAME, FIRST_VALUE);
//		configurationsToSave.put(SECOND_NAME, SECOND_VALUE);
//		configurationsToSave.put(THIRD_NAME, THIRD_VALUE);
//		
//		earthAppService.storeConfiguration(FIRST_CATEGORY, configurationsToSave);
//		Assert.assertNotNull(earthAppService.getConfiguration(FIRST_CATEGORY));
//		
//		earthAppService.clearConfig(FIRST_CATEGORY);
//		Assert.assertTrue(earthAppService.getConfiguration(FIRST_CATEGORY).isEmpty());
//	}
	
//	@Test
//	public void store() throws Exception{
//		earthAppService.store(SECOND_CATEGORY, FIRST_NAME, THIRD_VALUE);
//		earthAppService.store(THIRD_CATEGORY, SECOND_NAME, FIRST_VALUE);
//		earthAppService.store(FIRST_CATEGORY, THIRD_NAME, SECOND_VALUE);
//		
//		Map<String, String> firstConfiguration = earthAppService.getConfiguration(SECOND_CATEGORY);
//		Assert.assertTrue(firstConfiguration.containsKey(FIRST_NAME));
//		Assert.assertTrue(firstConfiguration.containsValue(THIRD_VALUE));
//		
//		Map<String, String> secondConfiguration = earthAppService.getConfiguration(THIRD_CATEGORY);
//		Assert.assertTrue(secondConfiguration.containsKey(SECOND_NAME));
//		Assert.assertTrue(secondConfiguration.containsValue(FIRST_VALUE));
//		
//		Map<String, String> thirdConfiguration = earthAppService.getConfiguration(FIRST_CATEGORY);
//		Assert.assertTrue(thirdConfiguration.containsKey(THIRD_NAME));
//		Assert.assertTrue(thirdConfiguration.containsValue(SECOND_VALUE));
//	}
	
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
	
	@Test 
	public void clearConfigValue() throws Exception{
		earthAppService.store(SECOND_CATEGORY, FIRST_NAME, THIRD_VALUE);
		earthAppService.store(THIRD_CATEGORY, SECOND_NAME, FIRST_VALUE);
		earthAppService.store(SECOND_CATEGORY, THIRD_NAME, SECOND_VALUE);
		
		Assert.assertTrue(earthAppService.read(SECOND_CATEGORY, FIRST_NAME).equals(THIRD_VALUE));
		Assert.assertTrue(earthAppService.read(THIRD_CATEGORY, SECOND_NAME).equals(FIRST_VALUE));
		Assert.assertTrue(earthAppService.read(SECOND_CATEGORY, THIRD_NAME).equals(SECOND_VALUE));
		
		earthAppService.clearConfigValue(SECOND_CATEGORY, FIRST_NAME);
		earthAppService.clearConfigValue(THIRD_CATEGORY, SECOND_NAME);
		earthAppService.clearConfigValue(SECOND_CATEGORY, THIRD_NAME);
		
		Assert.assertTrue(earthAppService.read(SECOND_CATEGORY, FIRST_NAME) == null);
		Assert.assertTrue(earthAppService.read(THIRD_CATEGORY, SECOND_NAME) == null);
		Assert.assertTrue(earthAppService.read(SECOND_CATEGORY, THIRD_NAME) == null);
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
	
	private void compareSamplesProperties(Sample storedSample, Sample retrievedSample){
		Assert.assertTrue(retrievedSample.getBarcode().equals(storedSample.getBarcode()));
		Assert.assertTrue(retrievedSample.getBiome().equals(storedSample.getBiome()));
		Assert.assertTrue(retrievedSample.getCollection().equals(storedSample.getCollection()));
		Assert.assertTrue(retrievedSample.getCollectorId().equals(storedSample.getCollectorId()));
		Assert.assertTrue(retrievedSample.getComment().equals(storedSample.getComment()));
		Assert.assertTrue(retrievedSample.getConductivity().equals(storedSample.getConductivity()));
		Assert.assertTrue(retrievedSample.getFeature().equals(storedSample.getFeature()));
		Assert.assertTrue(retrievedSample.getLabel().equals(storedSample.getLabel()));
		Assert.assertTrue(retrievedSample.getNationality().equals(storedSample.getNationality()));
		Assert.assertTrue(retrievedSample.getPermit().equals(storedSample.getPermit()));
		Assert.assertTrue(retrievedSample.getProjectId().equals(storedSample.getProjectId()));
		Assert.assertTrue(retrievedSample.getRawData().equals(storedSample.getRawData()));
		Assert.assertTrue(retrievedSample.getShipName().equals(storedSample.getShipName()));
		Assert.assertTrue(retrievedSample.getUserName().equals(storedSample.getUserName()));
		Assert.assertTrue(retrievedSample.getWeatherCondition().equals(storedSample.getWeatherCondition()));
		Assert.assertTrue(retrievedSample.getAccuracy().doubleValue() == storedSample.getAccuracy().doubleValue());
		Assert.assertTrue(retrievedSample.getAirTemperature().doubleValue() == storedSample.getAirTemperature().doubleValue());
		Assert.assertTrue(retrievedSample.getElevation().doubleValue() == storedSample.getElevation().doubleValue());
		Assert.assertTrue(retrievedSample.getLat().doubleValue() == storedSample.getLat().doubleValue());
		Assert.assertTrue(retrievedSample.getLon().doubleValue() == storedSample.getLon().doubleValue());
		Assert.assertTrue(retrievedSample.getSalinity().doubleValue() == storedSample.getSalinity().doubleValue());
		Assert.assertTrue(retrievedSample.getSampleSize().intValue() == storedSample.getSampleSize().intValue());
		Assert.assertTrue(retrievedSample.getSamplingDepth().doubleValue() == storedSample.getSamplingDepth().doubleValue());
		Assert.assertTrue(retrievedSample.getWaterDepth().doubleValue() == storedSample.getWaterDepth().doubleValue());
		Assert.assertTrue(retrievedSample.getWaterTemperature().doubleValue() == storedSample.getWaterTemperature().doubleValue());
		Assert.assertTrue(retrievedSample.getWindSpeed().doubleValue() == storedSample.getWindSpeed().doubleValue());
		Assert.assertTrue(retrievedSample.getModified().compareTo(storedSample.getModified()) == 0);
		Assert.assertTrue(retrievedSample.getTaken().compareTo(storedSample.getTaken()) == 0);
	}
	
}
