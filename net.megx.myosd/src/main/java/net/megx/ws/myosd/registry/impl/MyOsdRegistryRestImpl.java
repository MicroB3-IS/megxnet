package net.megx.ws.myosd.registry.impl;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.megx.form.widget.model.FormWidgetResult;
import net.megx.megdb.myosd.MyOsdDbService;
import net.megx.megdb.myosd.MyOsdParticipantRegistration;
import net.megx.megdb.myosd.dto.MyOsdParticipantDTOImpl;
import net.megx.megdb.myosd.MyOsdSample;
import net.megx.megdb.myosd.dto.MyOsdSampleImpl;
import net.megx.ws.core.BaseRestService;

@Path("v1/myosd/v1.0.0")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MyOsdRegistryRestImpl extends BaseRestService {

  protected Log log = LogFactory.getLog(getClass());

  private MyOsdDbService db;

  private String contactLink = "<a href=\"mailto:myosd-contact@microb3.eu\">myosd-contact@microb3.eu</a>";
  private String contactHtml = "<p>Bitte schreib uns dazu an " + contactLink
      + "!<p>";

  public MyOsdRegistryRestImpl() {
  }

  public MyOsdRegistryRestImpl(MyOsdDbService myOsdDbService) {
    this.db = myOsdDbService;
  }

  @Path("participants")
  @GET
  public String getAllParticipants() {
    System.out.println("get list of all myosd participants");
    log.debug("get list of all myosd participants");
    // db.saveParticipant("{}");
    throw new WebApplicationException(new UnsupportedOperationException("jo"),
        Response.Status.INTERNAL_SERVER_ERROR);
    // return null;
  }

  @Path("participants")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response saveParticipant(@FormParam("json") String json,
      @FormParam("kit_num") int myosdId, @FormParam("version") int version,
      @FormParam("email") String email,
      @FormParam("email_repeat") String emailRepeat,
      @FormParam("username") String userName) {

    log.debug("save participant: " + json);

    MyOsdParticipantRegistration p = new MyOsdParticipantDTOImpl();
    p.setRawJson(json);
    p.setMyOsdId(myosdId);
    p.setVersion(version);
    p.setUserName(userName);
    p.setBothEmails(email, emailRepeat);

    ResponseBuilder b = null;

    if (!p.isValidEmail()) {
      b = failure("Ungleiche email Angaben.",
          "<p>Hast Du vielleicht nicht die selbe email eingegeben?</p>");
      return b.build();
    }

    try {

      MyOsdParticipantRegistration duplicate = null;
      String dupMsg = "Doppelter Eintrag";
      String prefix = "Wir haben schon einen Eintrag mit ";

      duplicate = db.participantByName(p.getUserName());
      if (duplicate != null) {
        b = failure(dupMsg, prefix + "diesem Benutzernamen: " + p.getUserName(),
            "dup_username");
        return b.build();
      }

      duplicate = db.participantByMyOsdId(p.getMyOsdId());
      if (duplicate != null) {
        b = failure(dupMsg,
            prefix + "dieser MyOSD Sampling Kit Nummer: " + p.getMyOsdId(),
            "dup_myosd_id");
        return b.build();
      }

      db.saveParticipant(p);
      b = success("Vielen Dank!");
      return b.build();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      log.error("Could not save particpant", e);
      b = failure("Irgendwas ging auf unserem Server schief.", contactHtml);
    }

    if (b == null) {
      b = failure("Irgendwas ging auf unserem Server schief.", contactHtml);
    }
    return b.build();
  }

  @Path("sample/{kitNum}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getSample(@PathParam("kitNum") int myOsdId) {


    ResponseBuilder b = null;
    MyOsdSample sam = new MyOsdSampleImpl();
    sam.setMyOsdId(myOsdId);

    try {
      sam = db.sampleByMyOsdId(sam);
      b = Response.ok().entity( sam.getRawJson() );
    } catch (Exception e) {
      log.error("Could not get sample=" + myOsdId, e);
      b = failure("Irgendwas ging auf unserem Server schief.", contactHtml);
    }

    return b.build();
  }

  @Path("sample")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response saveSample(@FormParam("myosd_id") int myOsdId,
      @FormParam("json") String json) {

    log.debug("save sample myosd= " + myOsdId + " json=" + json);

    MyOsdSample sample = new MyOsdSampleImpl();
    sample.setRawJson(json);
    sample.setMyOsdId(myOsdId);

    ResponseBuilder b = null;

    try {

      MyOsdSample duplicate = null;
      String dupMsg = "Doppelter Eintrag";
      String prefix = "We already curated  ";

      duplicate = db.sampleByMyOsdId(sample);

      if (duplicate != null && sample.getMyOsdId().equals(duplicate)) {
        b = failure(dupMsg, prefix + sample.getMyOsdId(),
            "dup_myosd_id");
        return b.build();
      }
      db.saveSample(sample);
      b = success("Sample " + myOsdId + " saved succesfully");
      return b.build();

    } catch (Exception e) {
      log.error("Could not save sample", e);
      b = failure("DB ERROR: Could NOT save sample data!");
    }

    if (b == null) {
      b = failure("Could NOT save sample data!");
      log.error("result = null: Could not save sample");
    }
    return b.build();

  }

  private ResponseBuilder success(String message) {
    return Response.ok().entity(toJSON(
        new FormWidgetResult(false, message, FormWidgetResult.NO_REDIRECT)));
  }

  private ResponseBuilder failure(String message) {
    return failure(null, message, null);

  }

  private ResponseBuilder failure(String title, String message) {
    return failure(title, message, null);
  }

  private ResponseBuilder failure(String title, String message,
      String dataErrorCode) {
    return Response.status(Status.BAD_REQUEST)
        .entity(toJSON(new FormWidgetResult(true, message,
            FormWidgetResult.NO_REDIRECT, title, dataErrorCode)));
  }

}
