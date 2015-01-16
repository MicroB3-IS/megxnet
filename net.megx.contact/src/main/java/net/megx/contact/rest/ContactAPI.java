package net.megx.contact.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import net.megx.form.widget.model.FormWidgetResult;
import net.megx.mailer.BaseMailerService;
import net.megx.mailer.MailMessage;
import net.megx.megdb.contact.ContactService;
import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.model.contact.Contact;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.Result;

@Path("v1/contact/v1.0.0")
public class ContactAPI extends BaseRestService {

  private static final String PROPERTY_KEY_SENDER_MAIL = "net.megx.contact.mail.sender";
  private static final String PROPERTY_KEY_RECIPIENT_MAILS = "net.megx.contact.mail.recipients";

  private ContactService service;
  private BaseMailerService mailerService;
  private final String contactEmailSender;
  private final String contactEmailRecipients;

  public ContactAPI(ContactService service, BaseMailerService mailerService,
      final Properties configuration) {

    if (service == null) {
      throw new IllegalArgumentException("service must not be null.");
    }
    if (mailerService == null) {
      throw new IllegalArgumentException("mailerService must not be null.");
    }

    this.verifyConfiguration(configuration);

    this.service = service;
    this.mailerService = mailerService;
    this.contactEmailSender = configuration
        .getProperty(PROPERTY_KEY_SENDER_MAIL);
    this.contactEmailRecipients = configuration
        .getProperty(PROPERTY_KEY_RECIPIENT_MAILS);
  }

  @Path("store-contact")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response storeContactMail(@FormParam("email") String email,
      @FormParam("name") String name, @FormParam("comment") String comment,
      @Context HttpServletRequest request) {

    if (email == null || email.isEmpty()) {
      return Response
          .status(Status.BAD_REQUEST)
          .header("Access-Control-Allow-Origin", "*")
          .entity(
              toJSON(new Result<String>(true, "email not provided",
                  "bad-request"))).build();
    }
    if (name == null || name.isEmpty()) {
      return Response
          .status(Status.BAD_REQUEST)
          .header("Access-Control-Allow-Origin", "*")
          .entity(
              toJSON(new Result<String>(true, "name not provided",
                  "bad-request"))).build();
    }
    if (comment == null || comment.isEmpty()) {
      return Response
          .status(Status.BAD_REQUEST)
          .header("Access-Control-Allow-Origin", "*")
          .entity(
              toJSON(new Result<String>(true, "comment not provided",
                  "bad-request"))).build();
    }

    Date date = Calendar.getInstance().getTime();
    Contact contact = new Contact();
    contact.setEmail(email);
    contact.setName(name);
    contact.setCreated(date);
    contact.setComment(comment);
    String url = "";
    URI uri = null;

    try {
      sendMail(email, name, comment);
      service.storeContact(contact);
      url = request.getScheme() + "://" + request.getServerName() + ":"
          + request.getServerPort() + request.getContextPath();
      uri = new URI(url);
    } catch (DBGeneralFailureException e) {
      log.error("Could not store mail", e);
      return Response.serverError().header("Access-Control-Allow-Origin", "*")
          .entity(toJSON(new FormWidgetResult(true, "Could not store mail", null ))).build();
    } catch (URISyntaxException e) {
      log.error("Wrong URI" + url, e);
    } catch (Exception e) {
      log.error("Error occured", e);
      return Response.serverError().header("Access-Control-Allow-Origin", "*")
          .entity(toJSON(new FormWidgetResult(true, "Server error occured.", null ))).build();
    }

    return Response.ok().header("Access-Control-Allow-Origin", "*")
        .entity(toJSON(new FormWidgetResult(false, "Contact saved successfully.", uri.toString()))).build();
  }

  private void sendMail(String email, String name, String comment)
      throws AddressException, MessagingException {
    final MailMessage message = new MailMessage(name
        + " (megx.net contact form) <" + this.contactEmailSender + ">");
    message.setReplyToAddress(email);
    message.addRecipients(this.contactEmailRecipients);
    message.setSubject("User feedback");
    message.setMessageBody(comment);
    mailerService.send(message);
  }

  private void verifyConfiguration(final Properties configuration) {

    final StringBuilder errorMessage = new StringBuilder(
        "ContactAPI configuration is incomplete.");
    boolean error = false;

    if (configuration.getProperty(PROPERTY_KEY_SENDER_MAIL) == null
        || configuration.getProperty(PROPERTY_KEY_SENDER_MAIL).trim().isEmpty()) {
      errorMessage.append(" Missing configuration for sender mail address.");
      error = true;
    }
    if (configuration.getProperty(PROPERTY_KEY_RECIPIENT_MAILS) == null
        || configuration.getProperty(PROPERTY_KEY_RECIPIENT_MAILS).trim()
            .isEmpty()) {
      errorMessage
          .append(" Missing configuration for recipient(s) mail address(es).");
      error = true;
    }

    if (error) {
      throw new IllegalArgumentException(errorMessage.toString());
    }
  }

}
