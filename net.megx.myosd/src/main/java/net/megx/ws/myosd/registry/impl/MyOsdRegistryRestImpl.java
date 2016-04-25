package net.megx.ws.myosd.registry.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
import net.megx.ws.core.BaseRestService;

@Path("v1/myosd/v1.0.0")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MyOsdRegistryRestImpl extends BaseRestService {

  protected Log log = LogFactory.getLog(getClass());

  private MyOsdDbService db;

  public MyOsdRegistryRestImpl() {
  }

  public MyOsdRegistryRestImpl(MyOsdDbService myOsdDbService) {
    this.db = myOsdDbService;
  }

  @Path("bye")
  @GET
  public String hi() {
    return "hi, warum get das?";
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

    String contactLink = "<a href=\"mailto:myosd-contact@microb3.eu\">myosd-contact@microb3.eu</a>";
    String contactHtml = "<p>Bitte schreib uns dazu an " + contactLink + "!<p>";

    ResponseBuilder b = null;
    if (!p.isValidEmail()) {
      b = failure("Problem mit der email.",
          "<p>Hast Du villeicht nicht die selbe email eingeben?</p>"
              + contactHtml);
      return b.build();
    }

    try {
      db.saveParticipant(p);
      b = success("Vielen Dank!");
      return b.build();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      log.error("Could save particpant", e);
      b = failure("Ein Problem ist aufgetreten :(");
    }

    if (b == null) {
      b = failure("Irgendwas ging auf unserem Server schief.", contactHtml);
    }
    return b.build();
  }

  private ResponseBuilder success(String message) {
    return Response.ok().entity(
        toJSON(new FormWidgetResult(false, message, null)));
  }

  private ResponseBuilder failure(String message) {
    return Response.status(Status.BAD_REQUEST).entity(
        toJSON(new FormWidgetResult(true, message, null)));

  }

  private ResponseBuilder failure(String title, String message) {
    return Response.status(Status.BAD_REQUEST).entity(
        toJSON(new FormWidgetResult(true, message, null, title)));

  }

}
