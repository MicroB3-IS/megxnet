package net.megx.osd.registry.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.megx.megdb.osdregistry.OSDRegistryService;
import net.megx.model.osdregistry.OSDParticipant;
import net.megx.osd.registry.rest.util.OSDParticipantDeserializer;
import net.megx.ui.table.json.TableDataResponse;
import net.megx.ws.core.BaseRestService;

import com.google.gson.GsonBuilder;

@Path("v1/OSDRegistry/v1.0.0")
public class OSDRegistryAPI extends BaseRestService{
	
	private OSDRegistryService osdRegistryService;
	
	public OSDRegistryAPI(OSDRegistryService osdRegistryService){
		this.osdRegistryService = osdRegistryService;
		this.gson = new GsonBuilder().registerTypeAdapter(OSDParticipant.class, new OSDParticipantDeserializer())
		.serializeNulls()
		.create();
	}
	
	@Path("participants")
	@GET
	public String getAllParticipants(){
		List<OSDParticipant> osdParticipants;
		try{
			osdParticipants = osdRegistryService.getOSDParticipants();
			TableDataResponse<OSDParticipant> resp = new TableDataResponse<OSDParticipant>();
			resp.setData(osdParticipants);
			return toJSON(resp);
		} catch(Exception e){
			return toJSON(handleException(e));
		}
	}
	
	@Path("addParticipant")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveOSDParticipant(String participant){
		try{
			OSDParticipant osdParticipant = gson.fromJson(participant, OSDParticipant.class);
			osdRegistryService.storeOSDParticipant(osdParticipant);
			return toJSON(participant);
					
		} catch(Exception e){
			return toJSON(handleException(e));
		}
	}
	
	@Path("updateParticipant")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateOSDParticipant(String participant){
		try{
			OSDParticipant osdParticipant = gson.fromJson(participant, OSDParticipant.class);
			osdRegistryService.updateOSDParticipant(osdParticipant);
			return toJSON(osdParticipant);
		} catch(Exception e){
			return toJSON(handleException(e));
		}
	}
	
	@Path("deleteParticipant")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteOSDParticipant(@FormParam("id") String id){
		try{
			osdRegistryService.deleteOSDParticipant(id);
			return toJSON(id);
		} catch(Exception e){
			return toJSON(handleException(e));
		}
	}
	
	@Path("getParticipant")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getParticipant(@QueryParam("id") String id){
		try{	
			OSDParticipant participant = osdRegistryService.getParticipant(id);
			return toJSON(participant);
		} catch(Exception e){
			return toJSON(handleException(e));
		}
	}
}
