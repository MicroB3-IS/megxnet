package net.megx.esa.rest;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.commons.codec.binary.Base64;

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
				if(!jo.has("data")){
					throw new JsonParseException("The photo does not contain any data!");
				}
				sp.setData(Base64.decodeBase64(jo.get("data").getAsString()));
				return sp;
			}
			
		}).create();
	}
	
	@GET
	@Path("samples/{creator}")
	public String getSamplesForCollector(@PathParam("creator") String creator){
		List<Sample> samples = service.getSamples(creator);
		return toJSON(new Result<List<Sample>>(samples));
	}
	
	@Path("samples")
	@POST
	public String storeSamples(@FormParam("samples")String samplesJson){
		Sample [] samples = gson.fromJson(samplesJson, Sample [].class);
		try{
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
	
}
