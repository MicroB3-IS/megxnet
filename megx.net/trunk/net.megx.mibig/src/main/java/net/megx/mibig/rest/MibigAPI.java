package net.megx.mibig.rest;

import java.net.URI;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.mibig.MibigService;
import net.megx.model.mibig.MibigSubmission;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.Result;

@Path("v1/mibig/v1.0.0")
public class MibigAPI extends BaseRestService {

    private MibigService service;

    @Context
    private UriInfo uriInfo;
    
    public MibigAPI( MibigService service ) {
        this.service = service;
    }

    @Path("bgc-registration")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response storeMibigSubmission( @FormParam("json") String mibigJson,
            @Context HttpServletRequest request ) {

        try {
            if (mibigJson == null) {
                return Response
                        .status(Status.BAD_REQUEST)
                        .entity(toJSON(new Result<String>(true,
                                "json not provided", "bad-request"))).build();
            }
            MibigSubmission mibig = new MibigSubmission();

            mibig.setRaw(mibigJson);
            mibig.setSubmitted(Calendar.getInstance().getTime());
            mibig.setModified(Calendar.getInstance().getTime());
            // set to version from json string
            mibig.setVersion(1);

            service.storeMibigSubmission(mibig);
            
            //String address = uriInfo.getAbsolutePathBuilder();
            URI u = uriInfo.getAbsolutePath();
            URI location = UriBuilder.fromUri(u).replacePath(request.getContextPath() + "/mibig/bgc-submission" ).build();
            
            log.debug("uri absolute=" + u.toASCIIString() );
            log.debug("context path=" + request.getContextPath() );
            log.debug("location=" + location.toString());
            return Response.seeOther(location).build();
        } catch (DBGeneralFailureException e) {
            log.error("Could not store Submission:" + e);
            return Response.serverError().entity( toJSON( "Could not store Submission" ) ).build();
        } catch (Exception e) {
            log.error("Server error:" + e);
            return Response.serverError().entity( toJSON("Server error") ).build();
        }
    }

}
