package net.megx.osd.registry.rest.util;

import java.lang.reflect.Type;
import java.util.UUID;

import net.megx.model.osdregistry.OSDParticipant;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class OSDParticipantDeserializer implements JsonDeserializer<OSDParticipant>{

	@Override
	public OSDParticipant deserialize(JsonElement el, Type type,
			JsonDeserializationContext ctx) throws JsonParseException {
		
		OSDParticipant participant = new OSDParticipant();
		if(!el.isJsonObject()){
			throw new JsonParseException("Expected JSON Object. Got: " + (el != null ? el.getAsString() : "null") + " instead.");
		}
		
		JsonObject jo = el.getAsJsonObject();
		
		if(getString("id", jo).compareTo("") == 0){
			participant.setId(UUID.randomUUID().toString());
		} else{
			participant.setId(getString("id", jo));
		}
		
		participant.setSiteName(getString("siteName", jo));
		participant.setSiteLat(parseDouble(getString("siteLat", jo)));
		participant.setSiteLong(parseDouble(getString("siteLong", jo)));
		participant.setInstitution(getString("institution", jo));
		participant.setInstitutionLat(parseDouble(getString("institutionLat", jo)));
		participant.setInstitutionLong(parseDouble(getString("institutionLong", jo)));
		participant.setInstitutionAddress(getString("institutionAddress", jo));
		participant.setInstitutionWebAddress(getString("institutionWebAddress", jo));
		participant.setSiteCoordinator(getString("siteCoordinator", jo));
		participant.setCoordinatorEmail(getString("coordinatorEmail", jo));
		participant.setCountry(getString("country", jo));
		
		return participant;
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
