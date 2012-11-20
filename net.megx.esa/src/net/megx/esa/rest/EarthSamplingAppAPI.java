package net.megx.esa.rest;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.megx.megdb.esa.EarthSamplingAppService;
import net.megx.model.esa.Sample;
import net.megx.model.esa.SamplePhoto;

@Path("esa")
public class EarthSamplingAppAPI extends BaseRestService{
	private EarthSamplingAppService service;
	
	public EarthSamplingAppAPI(EarthSamplingAppService service) {
		this.service = service;
		gson = new GsonBuilder().registerTypeAdapter(SamplePhoto.class, new JsonDeserializer<SamplePhoto>() {

			@Override
			public SamplePhoto deserialize(JsonElement el, Type type,
					JsonDeserializationContext context) throws JsonParseException {
				SamplePhoto sp = new SamplePhoto();
				JsonObject jo = el.getAsJsonObject();
				if(!jo.has("uuid")){
					throw new JsonParseException("Photo must contain UUID.");
				}
				sp.setUuid(jo.get("uuid").getAsString());
				
				if(jo.has("mimeType")){
					sp.setMimeType(jo.get("mimeType").getAsString());
				}
				if(jo.has("data")){
					sp.setData(Base64.decodeBase64(jo.get("data").getAsString()));
				}
				
				return sp;
			}
			
		})
		/*.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			
			
			
			
			@Override
			public Date deserialize(JsonElement el, Type type,
					JsonDeserializationContext ctx) throws JsonParseException {
				
				return null;
			}
		})*/
		
		
		.create();
	}
	
	@GET
	@Path("samples/{creator}")
	public String getSamplesForCollector(@PathParam("creator") String creator){
		List<Sample> samples;
		try {
			samples = service.getSamples(creator);
			return toJSON(new Result<List<Sample>>(samples));
		} catch (Exception e) {
			return toJSON(handleException(e));
		}
		
	}
	
	@Path("samples")
	@POST
	public String storeSamples(@FormParam("samples")String samplesJson){
		
		if(samplesJson == null){
			return toJSON(new Result<String>(true, "Samples not provided", "bad-request"));
		}
		Sample [] samples = gson.fromJson(samplesJson, Sample [].class);
		try{
			JSONArray jo = new JSONArray(samplesJson);
			Map<String, String> rawData = new HashMap<String, String>();
			for(int i = 0; i < jo.length(); i++){
				rawData.put(jo.optJSONObject(i).optString("id"), jo.optJSONObject(i).toString());
			}
			for(Sample sample: samples){
				sample.setRawData(rawData.get(sample.getId()));
			}
			
			List<String> stored = service.storeSamples(Arrays.asList(samples));
			Result<List<String>> result = new Result<List<String>>(stored);
			return toJSON(result);
		}catch (Exception e) {
			return toJSON(handleException(e));
		}
	}
	
	@Path("photos")
	@POST
	public String storePhotos(@FormParam("photos")String photosJson){
		try{
			SamplePhoto [] photos = gson.fromJson(photosJson, SamplePhoto [].class);
			List<String> uuids = service.storePhotos(Arrays.asList(photos));
			Result<List<String>> result = new Result<List<String>>(uuids);
			return toJSON(result);
		}catch (Exception e) {
			return toJSON(handleException(e));
		}
	}
	
	@Path("config")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getConfiguration(){
		try{
			Map<String, Object> configuration = new HashMap<String, Object>();
			
			Result<Map<String, Object>> r = new Result<Map<String,Object>>(configuration);
			
			List<String> exported = new LinkedList<String>();
			Map<String, String> exportedCfg = service.getConfiguration("categories");
			for(Map.Entry<String, String> e: exportedCfg.entrySet()){
				if(e.getValue().contains("exported")){
					exported.add(e.getKey());
				}
			}
			for(String exportedCategory: exported){
				Map<String, String> cat = service.getConfiguration(exportedCategory);
				configuration.put(exportedCategory, cat);
			}
			return toJSON(r);
		}catch (Exception e) {
			return toJSON(handleException(e));
		}
	}
	
	
	public static void main(String[] args) {
		String sampleJSON = "[{\"id\":1,\"collectorId\":\"username\",\"projectId\":\"Micro B3\",\"userName\":\" \"," +
				"\"shipName\":\"\",\"nationality\":\"\"," +
				"\"photos\":[{\"uuid\":\"random-uuid-photo-identificator\",\"bytes\":\"base64-encoded-string-of-the-image-data-optional\"}]" +
				",\"label\":\"label\",\"barcode\":\"23897238947923\",\"material\":\"material\",\"biome\":\"biome\",\"feature\":\"feat\",\"collectionMethod\":\"coll\",\"permit\":\"yes\",\"sampleSize\":\"89\",\"conductivity\":\"conduc\",\"samplingDepths\":\"34\",\"comment\":\"Commentos\",\"time\":\"Tue Nov 20 2012 13:05:59 GMT+0100 (CET)\",\"weatherCondition\":\"Clear night\",\"airTemperature\":\"3\",\"waterTemperature\":\"4\",\"windSpeed\":\"56\",\"salinity\":\"6\",\"lat\":\"21.21\",\"lon\":\"41.41\",\"accuracy\":\"30\",\"elevation\":\"3\",\"secchiDepth\":\"3\",\"waterDepth\":\"6\"}]";
		Gson gson = new GsonBuilder().registerTypeAdapter(SamplePhoto.class, new JsonDeserializer<SamplePhoto>() {

			@Override
			public SamplePhoto deserialize(JsonElement el, Type type,
					JsonDeserializationContext context) throws JsonParseException {
				SamplePhoto sp = new SamplePhoto();
				JsonObject jo = el.getAsJsonObject();
				if(!jo.has("uuid")){
					throw new JsonParseException("Photo must contain UUID.");
				}
				sp.setUuid(jo.get("uuid").getAsString());
				
				if(jo.has("mimeType")){
					sp.setMimeType(jo.get("mimeType").getAsString());
				}
				if(jo.has("data")){
					sp.setData(Base64.decodeBase64(jo.get("data").getAsString()));
				}
				return sp;
			}
			
		}).create();
		
		Sample [] samples = gson.fromJson(sampleJSON, Sample[].class);
		System.out.println(Arrays.toString(samples));
	}
	
}
