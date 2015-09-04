package net.megx.pubmap.rest;

import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
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

import net.megx.mailer.BaseMailerService;
import net.megx.mailer.MailMessage;
import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.megdb.pubmap.PubMapService;
import net.megx.model.pubmap.Article;
import net.megx.model.pubmap.Ocean;
import net.megx.pubmap.geonames.GeonamesService;
import net.megx.pubmap.geonames.model.Place;
import net.megx.pubmap.model.GeoLocation;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.Result;

@Path("v1/pubmap/v1.0.0")
public class PubmapAPI extends BaseRestService {

  @Context
  HttpServletRequest request;

  private static final String PROPERTY_KEY_SENDER_MAIL = "net.megx.pubmap.mail.sender";
  private static final String PROPERTY_KEY_RECIPIENT_MAILS = "net.megx.pubmap.mail.recipients";

  private PubMapService service;
  private GeonamesService geonamesService;
  private BaseMailerService mailerService;
  private final String pubmapEmailSender;
  private final String pubmapEmailRecipients;

  public PubmapAPI(PubMapService service, GeonamesService geonamesService,
      BaseMailerService mailerService, final Properties configuration) {

    this.verifyConfiguration(configuration);

    this.service = service;
    this.geonamesService = geonamesService;
    this.mailerService = mailerService;
    this.pubmapEmailSender = configuration
        .getProperty(PROPERTY_KEY_SENDER_MAIL);
    this.pubmapEmailRecipients = configuration
        .getProperty(PROPERTY_KEY_RECIPIENT_MAILS);

  }

  @Path("article")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response storeArticle(@FormParam("article") String articleJson) {
    try {
      if (articleJson == null || articleJson.isEmpty()) {
        return Response
            .status(Status.BAD_REQUEST)
            .entity(
                toJSON(new Result<String>(true, "Article not provided.",
                    "bad-request"))).build();
      }
      Article article = gson.fromJson(articleJson, Article.class);
      String articleCreator;
      String status;

      article.setArticleXML(article.getArticleXML().replaceAll("&lt;", "<")
          .replaceAll("&gt;", ">").replaceAll("&nbsp;", " ")
          .replaceAll("&amp;", "&"));
      article.setCreated(Calendar.getInstance().getTime());

      if (request.getUserPrincipal() != null) {
        articleCreator = request.getUserPrincipal().getName();
      } else {
        articleCreator = "anonymous";
      }
      article.setUserName(articleCreator);

      service.storeArticle(article);
      status = "Article was successfully saved!";

      return Response.status(Status.OK).entity(toJSON(status)).build();

    } catch (DBGeneralFailureException e) {
      log.error("Could not store article in db: " + e);
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      log.error("Server error while saving article: " + e);
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    }
  }

  @Path("all")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getAllArticles() {
    try {
      return toJSON(new Result<List<Article>>(service.getAllArticles()));
    } catch (DBGeneralFailureException e) {
      log.error("Could not retrieve all Articles from db", e);
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    } catch (DBNoRecordsException e) {
      log.error("No Articles exists", e);
      throw new WebApplicationException(e, Response.Status.NO_CONTENT);
    } catch (Exception e) {
      log.error("Server error while getting all articles: " + e);
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    }
  }

  @Path("articles")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getArticlesByPmid(@QueryParam("pmid") int pmid) {
    try {
      return toJSON(new Result<List<Article>>(service.getArticlesByPmid(pmid)));
    } catch (DBGeneralFailureException e) {
      log.error("Could not retrieve all Articles from db", e);
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      log.error("Server error while getting all articles: " + e);
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    }
  }

  @Path("placename")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response findNearby(@QueryParam("lat") String lat,
      @QueryParam("lon") String lon) {

    Place place = new Place();

    try {

      if (lat == null || lat.isEmpty()) {
        log.error("Latitude parameter not provided.");
        return Response
            .status(Status.BAD_REQUEST)
            .entity(
                toJSON(new Result<String>(true,
                    "Latitude parameter not provided.", "bad-request")))
            .build();

      } else if (!lat.matches("-?\\d{1,2}(\\.\\d+)?")) {
        log.error("Latitude parameter is not valid number format.");
        return Response
            .status(Status.BAD_REQUEST)
            .entity(
                toJSON(new Result<String>(true,
                    "Latitude parameter is not valid number format.",
                    "bad-request"))).build();

      } else if (Double.valueOf(lat) < -90 || Double.valueOf(lat) > 90) {
        log.error("Latitude value is out of range.");
        return Response
            .status(Status.BAD_REQUEST)
            .entity(
                toJSON(new Result<String>(true,
                    "Latitude value is out of range.", "bad-request"))).build();

      } else if (lon == null || lon.isEmpty()) {
        log.error("Longitude parameter not provided.");
        return Response
            .status(Status.BAD_REQUEST)
            .entity(
                toJSON(new Result<String>(true,
                    "Longitude parameter not provided.", "bad-request")))
            .build();

      } else if (!lon.matches("-?\\d{1,3}(\\.\\d+)?")) {
        log.error("Longitude parameter is not valid number format.");
        return Response
            .status(Status.BAD_REQUEST)
            .entity(
                toJSON(new Result<String>(true,
                    "Longitude parameter is not valid number format.",
                    "bad-request"))).build();

      } else if (Double.valueOf(lon) < -180 || Double.valueOf(lon) > 180) {
        log.error("Longitude value is out of range.");
        return Response
            .status(Status.BAD_REQUEST)
            .entity(
                toJSON(new Result<String>(true,
                    "Longitude value is out of range.", "bad-request")))
            .build();

      }

      place = geonamesService.getPlaceName(lat, lon);

      return Response.status(Status.OK)
          .entity(toJSON(new Result<Place>(place))).build();

    } catch (Exception e) {
      log.error("Server error: ", e);
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    }
  }

  @Path("coordinates")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response findCoordinates(@QueryParam("q") String placeName,
      @QueryParam("worldRegion") String worldRegion) {

    Place place = new Place();
    Ocean ocean = new Ocean();

    try {

      if (worldRegion == null || worldRegion.isEmpty()) {
        log.error("WorldRegion parameter not provided.");
        return Response
            .status(Status.BAD_REQUEST)
            .entity(
                toJSON(new Result<String>(true,
                    "WorldRegion parameter not provided.", "bad-request")))
            .build();

      }

      if (service.isOcean(worldRegion)) {

        ocean = service.getOceanByName(worldRegion);
        place.setWorldRegion(ocean.getOceanName());
        place.setPlaceName(placeName);
        place.setLat(ocean.getLat().toString());
        place.setLon(ocean.getLon().toString());

      } else {

        if (placeName == null || placeName.isEmpty()) {
          log.error("PlaceName parameter not provided.");
          return Response
              .status(Status.BAD_REQUEST)
              .entity(
                  toJSON(new Result<String>(true,
                      "PlaceName parameter not provided.", "bad-request")))
              .build();

        } else {
          place = geonamesService.getCoordinates(placeName, worldRegion);
        }
      }

      return Response.status(Status.OK)
          .entity(toJSON(new Result<Place>(place))).build();

    } catch (DBGeneralFailureException e) {
      log.error("Could not retrieve Ocean from db", e);
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    } catch (DBNoRecordsException e) {
      log.error("No Ocean exists", e);
      throw new WebApplicationException(e, Response.Status.NO_CONTENT);
    } catch (Exception e) {
      log.error("Server error: ", e);
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    }
  }

  /*
   * mailing service
   */
  @Path("wrong-geo-reference")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response reportWrongGeoReference(@FormParam("pmid") String pmid,
      @FormParam("title") String title, @FormParam("authors") String authors,
      @FormParam("geoLocations") String geoLocations, @FormParam("comment") String comment) {

    try {

      if (pmid == null || pmid.isEmpty()) {
        log.error("pmid parameter not provided.");
        return Response
            .status(Status.BAD_REQUEST)
            .entity(
                toJSON(new Result<String>(true,
                    "pmid parameter not provided.", "bad-request")))
            .build();
      }
      if (title == null || title.isEmpty()) {
        log.error("title parameter not provided.");
        return Response
            .status(Status.BAD_REQUEST)
            .entity(
                toJSON(new Result<String>(true,
                    "title parameter not provided.", "bad-request")))
            .build();
      }
      if (authors == null || authors.isEmpty()) {
        log.error("authors parameter not provided.");
        return Response
            .status(Status.BAD_REQUEST)
            .entity(
                toJSON(new Result<String>(true,
                    "authors parameter not provided.", "bad-request")))
            .build();
      }
      
      if (geoLocations == null || geoLocations.isEmpty()) {
        log.error("geoLocations parameter not provided.");
        return Response
            .status(Status.BAD_REQUEST)
            .entity(
                toJSON(new Result<String>(true,
                    "geoLocations parameter not provided.", "bad-request")))
            .build();
      }
      GeoLocation[] articleGeoLocations = gson.fromJson(geoLocations, GeoLocation[].class);
      String reporterName;
      StringBuilder messageBody = new StringBuilder();

      if (request.getUserPrincipal() != null) {
        reporterName = request.getUserPrincipal().getName();
      } else {
        reporterName = "anonymous";
      }
      messageBody.append("Article pmid:  " + pmid + ".\n");
      messageBody.append("Title:  " + title + "\n");
      messageBody.append("Authors:  " + authors + ".\n");
      messageBody.append("\n");
      messageBody.append("Curated with the following locations:\n");
      messageBody.append("\n");
      
      for (GeoLocation geoLocation : articleGeoLocations) {
        
        messageBody.append("- " + "World Region: " + geoLocation.getWorldRegion() + "\n");
        messageBody.append("- " + "Place: " + geoLocation.getPlace() + "\n");
        messageBody.append("- " + "Latitude: " + geoLocation.getLat() + "\n");
        messageBody.append("- " + "Longitude: " + geoLocation.getLon() + "\n");
        messageBody.append("-------------------------------------------------------");
        messageBody.append("\n");
        
      }

      messageBody.append("Reported by:  " + reporterName + ".\n");
      messageBody.append("Comment:  "+ comment + "\n");
      
      sendMail(reporterName, messageBody.toString());
      return Response.status(Status.OK).entity(toJSON("success")).build();

    } catch (Exception e) {
      log.error("Server error: ", e);
      throw new WebApplicationException(e, Response.status(
          Status.INTERNAL_SERVER_ERROR).build());
    }
  }

  private void sendMail(String name, String messageBody)
      throws AddressException, MessagingException {
    final MailMessage message = new MailMessage(name
        + " (megxbar wrong geo-reference report) <" + this.pubmapEmailSender
        + ">");
    message.addRecipients(this.pubmapEmailRecipients);
    message.setSubject("User feedback");
    message.setMessageBody(messageBody);
    mailerService.send(message);
  }

  private void verifyConfiguration(final Properties configuration) {

    final StringBuilder errorMessage = new StringBuilder(
        "PubmapAPI configuration is incomplete.");
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
