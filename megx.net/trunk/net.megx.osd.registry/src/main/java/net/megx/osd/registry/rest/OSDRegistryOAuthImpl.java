package net.megx.osd.registry.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.megdb.osdregistry.OSDRegistryService;
import net.megx.model.osdregistry.OSDParticipant;
import net.megx.osd.registry.rest.util.OSDParticipantDeserializer;
import net.megx.ui.table.json.TableDataResponse;
import net.megx.ws.core.BaseRestService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("v1/OSDRegistry/v1.0.0")
public class OSDRegistryOAuthImpl extends BaseRestService {

	private OSDRegistryService osdRegistryService;
	private Gson gson = new Gson();
	private Log log = LogFactory.getLog(getClass());

	public OSDRegistryOAuthImpl(OSDRegistryService osdRegistryService) {
		this.osdRegistryService = osdRegistryService;
		this.gson = new GsonBuilder()
				.registerTypeAdapter(OSDParticipant.class,
						new OSDParticipantDeserializer()).serializeNulls()
				.create();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.megx.osd.registry.rest.OSDRegistryAPI#getAllParticipants()
	 */

	@Path("participants")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllParticipants() {
		List<OSDParticipant> osdParticipants;
		try {
			osdParticipants = osdRegistryService.getOSDParticipants();
			TableDataResponse<OSDParticipant> resp = new TableDataResponse<OSDParticipant>();
			resp.setData(osdParticipants);
			return gson.toJson(resp);
		} catch (DBGeneralFailureException e) {
			log.error("Could not retrieve  all participants" + e);
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		}catch (DBNoRecordsException e) {
			log.error("No participants exists " + e);
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		} 
		catch (Exception e) {
			log.error("Db exception for getting all participants" + e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.megx.osd.registry.rest.OSDRegistryAPI#saveOSDParticipant(java.lang
	 * .String)
	 */

	@Path("addParticipant")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveOSDParticipant(@FormParam("participant") String participant) {
		try {
			OSDParticipant osdParticipant = gson.fromJson(participant,
					OSDParticipant.class);
			osdRegistryService.storeOSDParticipant(osdParticipant);
			return gson.toJson(participant);
		} catch (DBGeneralFailureException e) {
			log.error("Db general error" + e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			log.error("Could not add participant" + e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.megx.osd.registry.rest.OSDRegistryAPI#updateOSDParticipant(java.lang
	 * .String)
	 */

	@Path("updateParticipant")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateOSDParticipant(@FormParam("participant") String participant) {
		try {
			OSDParticipant osdParticipant = gson.fromJson(participant,
					OSDParticipant.class);
			osdRegistryService.updateOSDParticipant(osdParticipant);
			return gson.toJson(osdParticipant);
		} catch (DBGeneralFailureException e) {
			log.error("Db general error" + e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			log.error("Could not update participant" + e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.megx.osd.registry.rest.OSDRegistryAPI#deleteOSDParticipant(java.lang
	 * .String)
	 */

	@Path("deleteParticipant")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteOSDParticipant(@FormParam("id") String id) {
		try {
			if(id == null){
			
			}
			osdRegistryService.deleteOSDParticipant(id);
			return gson.toJson(id);
		} catch (DBGeneralFailureException e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			log.error("Could not delete participant" + e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.megx.osd.registry.rest.OSDRegistryAPI#getParticipant(java.lang.String
	 * )
	 */

	@Path("getParticipant")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getParticipant(@QueryParam("id") String id) {
		try {
			OSDParticipant participant = osdRegistryService.getParticipant(id);
			return gson.toJson(participant);
		} catch (DBGeneralFailureException e) {
			log.error("Db general error for id: " + id + "\n" + e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			log.error("No DB record: \n" + e);
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		} catch (Exception e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Path("sample")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response saveOSDSample(@FormParam("json") String sample) {

		try {
			osdRegistryService.saveSample(sample);
			return Response.status(201).entity("sample saved").build();
		} catch (DBGeneralFailureException e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			log.error("Could not save sample" + e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
}
