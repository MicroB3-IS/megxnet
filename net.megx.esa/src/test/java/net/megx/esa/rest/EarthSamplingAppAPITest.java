package net.megx.esa.rest;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import net.megx.model.esa.Sample;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.scribe.model.Response;

public class EarthSamplingAppAPITest {
	
	Properties p = new Properties();
	InputStream in = null;
	OAuthService service;
	Token accessToken;
	/*Date date = Calendar.getInstance().getTime();
	
	private Sample defaultSample;
	
	private Sample createNewSample(String id){
		Sample sample = new Sample();
		sample.setAccuracy(Double.valueOf(p.getProperty("POST_GPS_ACCURACY")));
		sample.setAirTemperature(Double.valueOf(p.getProperty("POST_AIR_TEMPERATURE")));
		sample.setAppVersion(p.getProperty("POST_VERSION"));
		//sample.setBarcode(BARCODE);
		//sample.setBiome(BIOME);
		//sample.setCollection(COLLECTION);
		//sample.setCollectorId(COLLECTOR_ID);
		sample.setComment(p.getProperty("POST_COMMENT"));
		//sample.setConductivity(CONDUCTIVITY);
		//sample.setElevation(ELEVATION);
		//sample.setFeature(FEATURE);
		//sample.setLabel(LABEL);
		sample.setLat(Double.valueOf(p.getProperty("POST_LATITUDE")));
		sample.setLon(Double.valueOf(p.getProperty("POST_LONGITUDE")));
		sample.setModified(date);
		//sample.setNationality(NATIONALITY);
		//sample.setPermit(PERMIT);
		//sample.setProjectId(PROJECT_ID);
		//sample.setRawData(RAW_DATA);
		sample.setSalinity(Double.valueOf(p.getProperty("POST_SALINITY")));
		//sample.setSampleSize(SAMPLE_SIZE);
		sample.setSamplingDepth(Double.valueOf(p.getProperty("POST_DEPTH ")));
		//sample.setShipName(SHIPNAME);
		sample.setTaken(date);
		//sample.setUserName(USERNAME);
		sample.setWaterDepth(Double.valueOf(p.getProperty("POST_SECCHI_DEPTH ")));
		sample.setWaterTemperature(Double.valueOf(p.getProperty("POST_WATER_TEMPERATURE")));
		sample.setWeatherCondition(p.getProperty("POST_WEATHER_CONDITION"));	
		sample.setWindSpeed(Double.valueOf(p.getProperty("POST_WIND_SPEED")));
		sample.setAccuracy(Double.valueOf(p.getProperty("POST_GPS_ACCURACY")));
		return sample;
	}*/
	
	@Before
	public void setUp() throws Exception{
		
//	    defaultSample = createNewSample("1");
		in = new FileInputStream("src/test/resources/accessToken.properties");
		p.load(in);
		service = new ServiceBuilder().provider(MegxApi.class)
				.apiKey(p.getProperty("apiKey"))
				.apiSecret(p.getProperty("apiSecret"))
				.callback(p.getProperty("callback")).build();

		accessToken = new Token(p.getProperty("accessTokenApiKey"),
				p.getProperty("accessTokenApiSecret"));
	}
	
	@Test
	public void testGetAllSamples(){
		OAuthRequest request = new OAuthRequest(Verb.GET, p.getProperty("GET_ALL_SAMPLES_URL"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(200, response.getCode());
		
	}
	
	@Test
	public void testGetAllSampledOceans(){
		OAuthRequest request = new OAuthRequest(Verb.GET, p.getProperty("GET_ALL_SAMPLE_OCEANS_URL"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(200, response.getCode());
	}
	
	@Test
	public void testGetSample(){
		OAuthRequest request = new OAuthRequest(Verb.GET, p.getProperty("GET_SAMPLE_URL"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(200, response.getCode());
	}
	
	@Test
	public void testGetNumberOfObservations(){
		OAuthRequest request = new OAuthRequest(Verb.GET, p.getProperty("GET_NUMBER_OF_OBSERVATIONS_URL"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(200, response.getCode());
	}
	
	@Test
	public void testGetSamplesCreator(){
		OAuthRequest request = new OAuthRequest(Verb.GET, p.getProperty("GET_SAMPLE_CREATOR_URL"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(200, response.getCode());
	}
	
	@Test
	public void testGetCitizenConfig(){
		OAuthRequest request = new OAuthRequest(Verb.GET, p.getProperty("GET_CITIZEN_CONFIG_URL"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(200, response.getCode());
	}
	
	@Test
	public void testPostObservation(){
		OAuthRequest request = new OAuthRequest(Verb.POST,
		p.getProperty("POST_OBSERVATION_URL"));
		request.addBodyParameter("air_temperature", p.getProperty("POST_AIR_TEMPERATURE"));
		request.addBodyParameter("biome", p.getProperty("POST_BIOME"));
		request.addBodyParameter("comment", p.getProperty("POST_COMMENT"));
		request.addBodyParameter("date_taken", p.getProperty("POST_DATE_TAKEN"));
		request.addBodyParameter("depth", p.getProperty("POST_DEPTH"));
		request.addBodyParameter("gps_accuracy", p.getProperty("POST_GPS_ACCURACY"));
		request.addBodyParameter("json", p.getProperty("POST_JSON"));
		request.addBodyParameter("latitude", p.getProperty("POST_LATITUDE"));
		request.addBodyParameter("longitude", p.getProperty("POST_LONGITUDE"));
		request.addBodyParameter("nitrate", p.getProperty("POST_NITRATE"));
		request.addBodyParameter("nitrite",p.getProperty("POST_NITRITE"));
		request.addBodyParameter("origin", p.getProperty("POST_ORIGIN"));
		request.addBodyParameter("ph", p.getProperty("POST_PH"));
		request.addBodyParameter("fun", p.getProperty("POST_FUN"));
		request.addBodyParameter("phosphate", p.getProperty("POST_PHOSPHATE"));
		request.addBodyParameter("salinity", p.getProperty("POST_SALINITY"));
		request.addBodyParameter("sample_name", p.getProperty("POST_SAMPLE_NAME"));
		request.addBodyParameter("secchi_depth", p.getProperty("POST_SECCHI_DEPTH"));
		request.addBodyParameter("submit", p.getProperty("POST_SUBMIT"));
		request.addBodyParameter("time_taken", p.getProperty("POST_TIME_TAKEN"));
		request.addBodyParameter("version", p.getProperty("POST_VERSION"));
		request.addBodyParameter("water_temperature", p.getProperty("POST_WATER_TEMPERATURE"));
		request.addBodyParameter("weather_condition", p.getProperty("POST_WEATHER_CONDITION"));
		request.addBodyParameter("wind_speed", p.getProperty("POST_WIND_SPEED"));
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(200, response.getCode());
	}
	
	/*@Test
	public void testPostSamples(){
		OAuthRequest request = new OAuthRequest(Verb.POST, p.getProperty("POST_SAMPLES_URL"));
		request.addBodyParameter("samples", defaultSample.toString());
		service.signRequest(accessToken, request);
		Response response = request.send();
		Assert.assertEquals(200, response.getCode());
	}*/
	
	
}
