package net.megx.osd.registry.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.megx.megdb.osdregistry.OSDRegistryService;
import net.megx.model.osdregistry.OSDParticipant;
import net.megx.osd.registry.rest.util.OSDParticipantDeserializer;
import net.megx.ui.table.json.TableDataResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("v1/OSDRegistry/v1.0.0")
public class OSDRegistryOAuthImpl implements OSDRegistryAPI {

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
    @Override
    @Path("participants")
    @GET
    public String getAllParticipants() {
        List<OSDParticipant> osdParticipants;
        try {
            osdParticipants = osdRegistryService.getOSDParticipants();
            TableDataResponse<OSDParticipant> resp = new TableDataResponse<OSDParticipant>();
            resp.setData(osdParticipants);
            return gson.toJson(resp);
        } catch (Exception e) {
            throw new WebApplicationException(500);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.megx.osd.registry.rest.OSDRegistryAPI#saveOSDParticipant(java.lang
     * .String)
     */
    @Override
    @Path("addParticipant")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String saveOSDParticipant(String participant) {
        try {
            OSDParticipant osdParticipant = gson.fromJson(participant,
                    OSDParticipant.class);
            osdRegistryService.storeOSDParticipant(osdParticipant);
            return gson.toJson(participant);

        } catch (Exception e) {
            throw new WebApplicationException(500);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.megx.osd.registry.rest.OSDRegistryAPI#updateOSDParticipant(java.lang
     * .String)
     */
    @Override
    @Path("updateParticipant")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateOSDParticipant(String participant) {
        try {
            OSDParticipant osdParticipant = gson.fromJson(participant,
                    OSDParticipant.class);
            osdRegistryService.updateOSDParticipant(osdParticipant);
            return gson.toJson(osdParticipant);
        } catch (Exception e) {
            throw new WebApplicationException(500);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.megx.osd.registry.rest.OSDRegistryAPI#deleteOSDParticipant(java.lang
     * .String)
     */
    // TODO change http method to delete
    @Override
    @Path("deleteParticipant")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteOSDParticipant(@FormParam("id") String id) {
        try {
            osdRegistryService.deleteOSDParticipant(id);
            return gson.toJson(id);
        } catch (Exception e) {
            throw new WebApplicationException(500);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.megx.osd.registry.rest.OSDRegistryAPI#getParticipant(java.lang.String
     * )
     */
    @Override
    @Path("getParticipant")
    @GET
    // TODO change queryparam to pathparam
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getParticipant(@QueryParam("id") String id) {
        try {
            // TODO check what if no partipcant, what if DB error
            OSDParticipant participant = osdRegistryService.getParticipant(id);
            return gson.toJson(participant);
        } catch (Exception e) {
            throw new WebApplicationException(500);
        }
    }

    @Override
    @Path("sample")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response saveOSDSample(@FormParam("json") String sample) {
        
        try {
            osdRegistryService.saveSample(sample);
            return Response.status(201).entity("sample saved").build();
        } catch (Exception e) {
            log.error("Could not save sample");
            throw new WebApplicationException(500);
        }
    }
}
