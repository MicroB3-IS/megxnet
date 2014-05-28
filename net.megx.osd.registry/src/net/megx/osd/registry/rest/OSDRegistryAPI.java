package net.megx.osd.registry.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface OSDRegistryAPI {

    @Path("participants")
    @GET
    public abstract String getAllParticipants();

    @Path("addParticipant")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public abstract String saveOSDParticipant(String participant);

    @Path("updateParticipant")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public abstract String updateOSDParticipant(String participant);

    @Path("deleteParticipant")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public abstract String deleteOSDParticipant(@FormParam("id") String id);

    @Path("getParticipant")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public abstract String getParticipant(@QueryParam("id") String id);
 
    @Path("sample")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public abstract Response saveOSDSample(@FormParam("json") String sample);

}