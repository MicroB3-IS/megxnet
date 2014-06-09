package net.megx.esa.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;

import net.megx.broadcast.proxy.BroadcasterProxy;
import net.megx.esa.rest.util.SampleDeserializer;
import net.megx.esa.util.ImageResizer;
import net.megx.megdb.esa.EarthSamplingAppService;
import net.megx.model.esa.Sample;
import net.megx.model.esa.SampleLocationCount;
import net.megx.model.esa.SampleObservation;
import net.megx.model.esa.SamplePhoto;
import net.megx.model.esa.SampleRow;
import net.megx.ui.table.json.TableDataResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * 
 * @author borce.jadrovski
 * 
 */
@Path("v1/esa/v1.0.0")
public class EarthSamplingAppAPI extends BaseRestService {

    private EarthSamplingAppService service;
    private BroadcasterProxy broadcasterProxy;
    private ImageResizer imageResizer;

    private static final int THUMBNAIL_WIDTH = 240;
    private static final int THUMBNAIL_HEIGHT = 240;

    private static final String CSV_HEADER = "ID,Taken,Modified,Collector_ID,Label,Barcode,Project_ID,Username,Ship_name,Boat_manufacturer,Boat_model,Boat_length,Homeport,Nationality,Elevation,Biome,Feature,Collection,Permit, Material, Secchi_depth, Sampling_depth,Water_depth,Sample_size,Weather_condition,Air_temperature,Water_temperature,Conductivity,Wind_speed,Salinity,Comment,Lat,Lon,Accuracy,Phosphate,Nitrate,Nitrite,pH,Number_photos";
    private static final String CSV_ROW = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s";

    private Properties p = new Properties();
    private InputStream in = null;
    private ConfigurationBuilder cb = null;
    private TwitterFactory tf = null;
    private Twitter twitter = null;
    
    public EarthSamplingAppAPI(EarthSamplingAppService service,
            BroadcasterProxy broadcasterProxy) throws IOException  {
        this.service = service;
        this.broadcasterProxy = broadcasterProxy;
        this.imageResizer = new ImageResizer();
        gson = new GsonBuilder()
                .registerTypeAdapter(SamplePhoto.class,
                        new JsonDeserializer<SamplePhoto>() {

                            @Override
                            public SamplePhoto deserialize(JsonElement el,
                                    Type type,
                                    JsonDeserializationContext context)
                                    throws JsonParseException {
                                SamplePhoto sp = new SamplePhoto();
                                JsonObject jo = el.getAsJsonObject();
                                if (!jo.has("uuid")) {
                                    throw new JsonParseException(
                                            "Photo must contain UUID.");
                                }
                                sp.setUuid(jo.get("uuid").getAsString());

                                if (jo.has("mimeType")) {
                                    sp.setMimeType(jo.get("mimeType")
                                            .getAsString());
                                }
                                if (jo.has("data")) {
                                    sp.setData(Base64.decodeBase64(jo.get(
                                            "data").getAsString()));
                                }

                                return sp;
                            }

                        })
                .registerTypeAdapter(Sample.class, new SampleDeserializer())
                .registerTypeAdapter(Double.class,
                        new JsonSerializer<Double>() {
                            @Override
                            public JsonElement serialize(Double num, Type type,
                                    JsonSerializationContext context) {

                                if (num != null && ! num.isNaN()) {
                                    return new JsonPrimitive(num.toString());
                                }
                                return new JsonPrimitive(""); 

                            }
                        })
                                        .registerTypeAdapter(Double.class,
                        new JsonSerializer<Double>() {
                            @Override
                            public JsonElement serialize(Double num, Type type,
                                    JsonSerializationContext context) {

                                if (num != null && ! num.isNaN() ) {
                                    return new JsonPrimitive(num.toString());
                                }
                                return new JsonPrimitive(""); 

                            }
                        }).serializeNulls().setPrettyPrinting()
                .serializeSpecialFloatingPointValues().create();
        
        in = getClass().getClassLoader().getResourceAsStream("properties/twitter.properties");
		p.load(in);
        this.cb = new ConfigurationBuilder();
        this.cb.setDebugEnabled(true)
		  .setUseSSL(true)
		  .setOAuthConsumerKey(p.getProperty("consumerKey"))
		  .setOAuthConsumerSecret(p.getProperty("consumerSecret"))
		  .setOAuthAccessToken(p.getProperty("accessToken"))
		  .setOAuthAccessTokenSecret(p.getProperty("accessTokenSecret"))
		  .setRestBaseURL(p.getProperty("restBaseURL"));
        this.tf = new TwitterFactory(cb.build());
        this.twitter = tf.getInstance();
    }

    /**
     * 
     * @return JSON formatted string of the all of the samples stored in DB.
     */
    @GET
    @Path("allSamples")
    public String getAllSamples() {
        List<SampleRow> samples;
        try {
            samples = service.getAllSamples();
            TableDataResponse<SampleRow> resp = new TableDataResponse<SampleRow>();
            resp.setData(samples);
            return toJSON(resp);
        } catch (Exception e) {
            return toJSON(handleException(e));
        }

    }

    @GET
    @Path("sampledOceans")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSampledOceans() {
        try {
            List<SampleLocationCount> sampledOceans = service
                    .getSamplesLocationAndCount();
            return toJSON(new Result<List<SampleLocationCount>>(sampledOceans));
        } catch (Exception e) {
            return toJSON(handleException(e));
        }
    }

    @POST
    @Path("downloadSamples.csv")
    public Response downloadSamples(
            @FormParam("sampleIds") final String sampleIds) {
        ResponseBuilder rb = Response.ok().entity(new StreamingOutput() {

            @Override
            public void write(OutputStream out) throws IOException,
                    WebApplicationException {

                try {
                    List<String> ids = new ArrayList<String>(Arrays
                            .asList(sampleIds.split(",")));
                    List<Sample> samples = service.downloadSamples(ids);
                    PrintWriter writer = new PrintWriter(out);
                    writer.println(CSV_HEADER);
                    for (Sample sample : samples) {
                        writer.println(String.format(CSV_ROW, sample.getId(),
                                sample.getTaken(), sample.getModified(),
                                sample.getCollectorId(), sample.getLabel(),
                                sample.getBarcode(), sample.getProjectId(),
                                sample.getUserName(), sample.getShipName(),
                                sample.getBoatManufacturer(),
                                sample.getBoatModel(), sample.getBoatLength(),
                                sample.getHomeport(), sample.getNationality(),
                                sample.getElevation(), sample.getBiome(),
                                sample.getFeature(), sample.getCollection(),
                                sample.getPermit(), sample.getMaterial(),
                                sample.getSecchiDepth(),
                                sample.getSamplingDepth(),
                                sample.getWaterDepth(), sample.getSampleSize(),
                                sample.getWeatherCondition(),
                                sample.getAirTemperature(),
                                sample.getWaterTemperature(),
                                sample.getConductivity(),
                                sample.getWindSpeed(), sample.getSalinity(),
                                sample.getComment(), sample.getLat(),
                                sample.getLon(), sample.getAccuracy(),
                                sample.getPhosphate(), sample.getNitrate(),
                                sample.getNitrite(), sample.getPh(),
                                sample.getPhotos().length));
                    }
                    writer.flush();
                    out.flush();
                } catch (Exception e) {
                    throw new WebApplicationException(500);
                }
            }
        });

        rb.header("Content-Disposition: attachment",
                "filename=\"samplesData.csv\"");
        rb.type(MediaType.APPLICATION_OCTET_STREAM);
        return rb.build();
    }

    /**
     * 
     * @return JSON formatted string of a sample stored in DB.
     */
    @GET
    @Path("sample")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSample(@QueryParam("sampleId") String sampleId) {
        Sample sample;
        try {
            sample = service.getSample(sampleId);
            return toJSON(new Result<Sample>(sample));
        } catch (Exception e) {
            return toJSON(handleException(e));
        }
    }

    @GET
    @Path("renzo")
    @Produces(MediaType.APPLICATION_JSON)
    public String getRenzo() {

        List<SampleRow> samples;
        try {
            samples = service.getAllSamples();
            return gson.toJson(samples);
        } catch (Exception e) {
            return gson.toJson("error");
        }

    }

    /**
     * @param nbObservations
     * @return JSON formatted string of latest nbObservations (or less if query
     *         returns less) sample retrievals
     */
    @GET
    @Path("observations/{nbObservations}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getObservations(
            @PathParam("nbObservations") int nbObservations) {
        List<SampleObservation> observations = new ArrayList<SampleObservation>();
        try {
            observations = service.getLatestObservations(nbObservations);
            return toJSON(new Result<List<SampleObservation>>(observations));
        } catch (Exception e) {
            return toJSON(handleException(e));
        }
    }

    /**
     * 
     * @param ID
     *            of the collector of the samples
     * @return JSON formatted string of the samples created by the collector if
     *         any.
     */
    @GET
    @Path("samples/{creator}")
    public String getSamplesForCollector(@PathParam("creator") String creator) {
        List<Sample> samples;
        try {
            samples = service.getSamples(creator);
            return toJSON(new Result<List<Sample>>(samples));
        } catch (Exception e) {
            return toJSON(handleException(e));
        }

    }

    /**
     * Stores samples in the database. These samples are being transferred from
     * the user's device.
     * 
     * @param samplesJson
     *            JSON formatted string representing the samples to be stored in
     *            the database.
     * @return Comma delimited string containing the ID's of the successfully
     *         saved samples.
     */
    @Path("samples")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String storeSamples(@FormParam("samples") String samplesJson,
            @Context HttpServletRequest request) {
        try {
            if (samplesJson == null) {
                return toJSON(new Result<String>(true, "Samples not provided",
                        "bad-request"));
            }
            Sample[] samples = gson.fromJson(samplesJson, Sample[].class);
            List<Sample> samplesToSave = new ArrayList<Sample>();
            Map<String, String> errorMap = new HashMap<String, String>();
            List<String> savedSamples = new ArrayList<String>();
            List<Sample> samplesToBroadcast = new ArrayList<Sample>();
            Map<String, Object> result = new HashMap<String, Object>();
            String sampleCreator = request.getUserPrincipal().getName();
            for (Sample sample : samples) {
                if (validateSample(sample)) {
                	this.twitter.updateStatus("New OSD observation at " + sample.getLat() + ", " + sample.getLon() + " on " + sample.getTaken() + ". See https://mb3is.megx.net/osd-app/samples ");
                    sample.setUserName(sampleCreator);
                    samplesToSave.add(sample);
                } else {
                    if (sample.getLabel() != null) {
                        errorMap.put(sample.getId(),
                                "Sample " + sample.getLabel()
                                        + " is missing latitude or longitude");
                    } else {
                        errorMap.put(sample.getId(), "Sample " + sample.getId()
                                + " is missing latitude, longitude or label.");
                    }
                }
            }
            savedSamples = service.storeSamples(samplesToSave);
            result.put("saved", savedSamples);
            result.put("errors", errorMap);
            Result<Map<String, Object>> resultToReturn = new Result<Map<String, Object>>(
                    result);

            // Broadcast JSON string with saved samples to subscribed clients
            for (Sample sample : samplesToSave) {
                if (savedSamples.contains(sample.getId())) {
                    samplesToBroadcast.add(sample);
                }
            }
            this.broadcasterProxy.broadcastMessage("/topic/notifications/esa",
                    toJSON(samplesToBroadcast));

            return toJSON(resultToReturn);
        } catch (Exception e) {
            return toJSON(handleException(e));
        }
    }

    private boolean validateSample(Sample sample) {
        if (sample.getLat() == null || sample.getLon() == null) {
            return false;
        }
        return true;
    }

    /**
     * Store a single photo that belongs to already saved sample in the
     * database.
     * 
     * @param request
     *            Contains the binary data for the photo to be saved along with
     *            the photos' UUID, MIME type and path properties.
     * @throws IOException
     * @throws Exception
     *             If the photo to be saved doesn't belong to a sample that is
     *             already saved in the database, exception is thrown.
     */
    @Path("photos")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @POST
    public void storePhotos(@Context HttpServletRequest request)
            throws WebApplicationException, IOException {

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            FileItemFactory factory = new DiskFileItemFactory();

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            // Parse the request
            List items;
            try {
                items = upload.parseRequest(request);
            } catch (FileUploadException e) {
                throw new WebApplicationException(e);
            }
            SamplePhoto photoToSave = new SamplePhoto();

            Iterator iter = items.iterator();

            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (!item.isFormField()) {
                    byte[] imageData = item.get();
                    photoToSave.setData(imageData);
                    photoToSave.setThumbnail(this.imageResizer.resizeImage(
                            imageData, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT));
                    photoToSave.setMimeType(item.getContentType());
                } else {
                    if (item.getFieldName().equalsIgnoreCase("uuid")) {
                        photoToSave.setUuid(new String(item.get()));
                    } else if (item.getFieldName().equalsIgnoreCase("path")) {
                        photoToSave.setPath(new String(item.get()));
                    }
                }
            }

            List<String> uuids;
            try {
                uuids = service.storePhotos(Arrays.asList(photoToSave));
            } catch (Exception e) {
                throw new WebApplicationException(e);
            }

            if (uuids.size() == 0) {
                throw new WebApplicationException();
            }
        }
    }

    /**
     * Retrieve the configuration that will be used by the Citizen version of
     * the client application.
     * 
     * @return JSON formatted string of the configuration to be used by the
     *         client application.
     */
    @Path("citizenConfig")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getConfigurationForCitizen() {
        try {
            Map<String, Object> configuration = new HashMap<String, Object>();
            Result<Map<String, Object>> r = new Result<Map<String, Object>>(
                    configuration);
            List<String> exported = new LinkedList<String>();
            Map<String, String> exportedCfg = service
                    .getConfigurationForCitizen("categories");
            for (Map.Entry<String, String> e : exportedCfg.entrySet()) {
                if (e.getValue().contains("exported")) {
                    exported.add(e.getKey());
                }
            }
            for (String exportedCategory : exported) {
                Map<String, String> cat = service
                        .getConfigurationForCitizen(exportedCategory);
                configuration.put(exportedCategory, cat);
            }
            return toJSON(r);
        } catch (Exception e) {
            return toJSON(handleException(e));
        }
    }

    /**
     * Retrieve the configuration that will be used by the Scientist version of
     * the client application.
     * 
     * @return JSON formatted string of the configuration to be used by the
     *         client application.
     */
    @Path("scientistConfig")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getConfigurationForScientist() {
        try {
            Map<String, Object> configuration = new HashMap<String, Object>();

            Result<Map<String, Object>> r = new Result<Map<String, Object>>(
                    configuration);

            List<String> exported = new LinkedList<String>();
            Map<String, String> exportedCfg = service
                    .getConfigurationForScientist("categories");
            for (Map.Entry<String, String> e : exportedCfg.entrySet()) {
                if (e.getValue().contains("exported")) {
                    exported.add(e.getKey());
                }
            }
            for (String exportedCategory : exported) {
                Map<String, String> cat = service
                        .getConfigurationForScientist(exportedCategory);
                configuration.put(exportedCategory, cat);
            }
            return toJSON(r);
        } catch (Exception e) {
            return toJSON(handleException(e));
        }
    }

    public static void main(String[] args) {
        String sampleJSON = "[{\"id\":1,\"collectorId\":\"username\",\"projectId\":\"Micro B3\",\"userName\":\" \","
                + "\"shipName\":\"\",\"nationality\":\"\","
                + "\"photos\":[{\"uuid\":\"random-uuid-photo-identificator\",\"bytes\":\"base64-encoded-string-of-the-image-data-optional\"}]"
                + ",\"label\":\"label\",\"barcode\":\"23897238947923\",\"material\":\"material\",\"biome\":\"biome\",\"feature\":\"feat\",\"collectionMethod\":\"coll\",\"permit\":\"yes\",\"sampleSize\":\"89\",\"conductivity\":\"conduc\",\"samplingDepths\":\"34\",\"comment\":\"Commentos\",\"time\":\"Tue Nov 20 2012 13:05:59 GMT+0100 (CET)\",\"weatherCondition\":\"Clear night\",\"airTemperature\":\"3\",\"waterTemperature\":\"4\",\"windSpeed\":\"56\",\"salinity\":\"6\",\"lat\":\"21.21\",\"lon\":\"41.41\",\"accuracy\":\"30\",\"elevation\":\"3\",\"secchiDepth\":\"3\",\"waterDepth\":\"6\"}]";
        Gson gson = new GsonBuilder().registerTypeAdapter(SamplePhoto.class,
                new JsonDeserializer<SamplePhoto>() {

                    @Override
                    public SamplePhoto deserialize(JsonElement el, Type type,
                            JsonDeserializationContext context)
                            throws JsonParseException {
                        SamplePhoto sp = new SamplePhoto();
                        JsonObject jo = el.getAsJsonObject();
                        if (!jo.has("uuid")) {
                            throw new JsonParseException(
                                    "Photo must contain UUID.");
                        }
                        sp.setUuid(jo.get("uuid").getAsString());

                        if (jo.has("mimeType")) {
                            sp.setMimeType(jo.get("mimeType").getAsString());
                        }
                        if (jo.has("data")) {
                            sp.setData(Base64.decodeBase64(jo.get("data")
                                    .getAsString()));
                        }
                        return sp;
                    }

                }).create();

        Sample[] samples = gson.fromJson(sampleJSON, Sample[].class);
        System.out.println(Arrays.toString(samples));
    }

}
