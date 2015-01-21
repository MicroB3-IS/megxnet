package net.megx.osd.registry.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import net.megx.form.widget.model.FormWidgetResult;
import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.megdb.osdregistry.OSDRegistryService;
import net.megx.model.osdregistry.OSDParticipant;
import net.megx.model.osdregistry.OSDParticipation;
import net.megx.osd.registry.rest.util.OSDParticipantDeserializer;
import net.megx.ui.table.json.TableDataResponse;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.Result;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("v1/OSDRegistry/v1.0.0")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OSDRegistryOAuthImpl extends BaseRestService {

  private OSDRegistryService osdRegistryService;
  private Gson gson = new Gson();
  private Log log = LogFactory.getLog(getClass());

  public OSDRegistryOAuthImpl(OSDRegistryService osdRegistryService) {
    this.osdRegistryService = osdRegistryService;
    this.gson = new GsonBuilder()
        .registerTypeAdapter(OSDParticipant.class,
            new OSDParticipantDeserializer()).serializeNulls().create();
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.megx.osd.registry.rest.OSDRegistryAPI#getAllParticipants()
   */

  @Path("participants")
  @GET
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
    } catch (DBNoRecordsException e) {
      log.error("No participants exists " + e);
      throw new WebApplicationException(e, Response.Status.NO_CONTENT);
    } catch (Exception e) {
      log.error("Db exception for getting all participants" + e);
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.megx.osd.registry.rest.OSDRegistryAPI#saveOSDParticipant(java.lang
   * .String)
   */

  @Path("addParticipant")
  @POST
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
  public String updateOSDParticipant(
      @FormParam("participant") String participant) {
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
  @POST
  public String deleteOSDParticipant(@FormParam("id") String id) {
    try {
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
   * net.megx.osd.registry.rest.OSDRegistryAPI#getParticipant(java.lang.String )
   */

  @Path("getParticipant")
  @GET
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
    
    if (sample == null || sample.isEmpty()) {
      return Response
          .status(Status.BAD_REQUEST)
          .header("Access-Control-Allow-Origin", "*")
          .entity(
              toJSON(new FormWidgetResult(true, "Sample not provided.",
                  null))).build();
    }

    try {
      osdRegistryService.saveSample(sample);
      return Response
          .status(201)
          .header("Access-Control-Allow-Origin", "*")
          .entity(
              toJSON(new FormWidgetResult(false, "Sample saved successfully.",
                  null))).build();
    } catch (DBGeneralFailureException e) {
      log.error("DB error, could not save sample.", e);
      return Response
          .serverError()
          .header("Access-Control-Allow-Origin", "*")
          .entity(
              toJSON(new FormWidgetResult(true,
                  "Database error, could not save sample.", null))).build();
    } catch (Exception e) {
      log.error("Could not save sample." + e);
      return Response
          .serverError()
          .header("Access-Control-Allow-Origin", "*")
          .entity(
              toJSON(new FormWidgetResult(true,
                  "Server error occured, could not save sample.", null)))
          .build();
    }
  }

  /**
   * Save the participation form.
   * 
   * @param contactName
   *          the participant name.
   * @param contactEmail
   *          the participant email.
   * @param contactAddress
   *          the participant address.
   * @param ideas
   *          the participant ideas about OSD.
   * @param contributedSamples
   *          the participant unique samples.
   * @param funding
   *          the participant funding.
   * @param participateDate
   *          the participant date.
   * @param json
   *          the whole participation form json.
   * @throws WebApplicationException
   *           if the given contactName,contactEmail or json are empty or null.
   */
  @Path("participation")
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response saveParticipation(
      @FormParam("contactName") String contactName,
      @FormParam("contactEmail") String contactEmail,
      @FormParam("contactAddress") String contactAddress,
      @FormParam("ideas") String ideas,
      @FormParam("contributedSamples") String contributedSamples,
      @FormParam("funding") String funding,
      @FormParam("participateDate") String participateDate,
      @FormParam("json") String participationJson,
      @Context HttpServletRequest request) {

    String url = "";
    URI uri = null;
    String osdPath = "/osd-registry";
    try {
      if (participationJson == null || participationJson.isEmpty()) {
        return Response
            .status(Status.BAD_REQUEST)
            .header("Access-Control-Allow-Origin", "*")
            .entity(
                toJSON(new FormWidgetResult(true, "Participation not provided.",
                    null))).build();
      }
      if (contactName == null || contactName.isEmpty()) {
        return Response
            .status(Status.BAD_REQUEST)
            .header("Access-Control-Allow-Origin", "*")
            .entity(
                toJSON(new FormWidgetResult(true, "Contact name not provided.",
                    null))).build();
      }
      if (contactEmail == null || contactEmail.isEmpty()) {
        return Response
            .status(Status.BAD_REQUEST)
            .header("Access-Control-Allow-Origin", "*")
            .entity(
                toJSON(new FormWidgetResult(true, "Contact email not provided.",
                    null))).build();
      }

      OSDParticipation participation = new OSDParticipation();
      participation.setContactAddress(contactAddress);
      participation.setContactEmail(contactEmail);
      participation.setContactName(contactName);
      participation.setContributedSamples(contributedSamples);
      participation.setFunding(funding);
      participation.setIdeas(ideas);
      participation.setParticipateDate(participateDate);
      participation.setParticipationJson(participationJson);
      osdRegistryService.saveParticipation(participation);
      url = request.getScheme() + "://" + request.getServerName() + ":"
          + request.getServerPort() + request.getContextPath() + osdPath;
      uri = new URI(url);

    } catch (DBGeneralFailureException e) {
      log.error("DB error, could not save participation", e);
      return Response
          .serverError()
          .header("Access-Control-Allow-Origin", "*")
          .entity(
              toJSON(new FormWidgetResult(true,
                  "Database error, could not save participation", null)))
          .build();
    } catch (URISyntaxException e) {
      log.error("Wrong URI" + url, e);
    } catch (Exception e) {
      log.error("Could not save participation" + e);
      return Response
          .serverError()
          .header("Access-Control-Allow-Origin", "*")
          .entity(
              toJSON(new FormWidgetResult(true,
                  "Server error occured, could not save participation.", null)))
          .build();
    }
    return Response
        .status(201)
        .header("Access-Control-Allow-Origin", "*")
        .entity(
            toJSON(new FormWidgetResult(false,
                "Participation saved successfully.", uri.toString()))).build();
  }
}
