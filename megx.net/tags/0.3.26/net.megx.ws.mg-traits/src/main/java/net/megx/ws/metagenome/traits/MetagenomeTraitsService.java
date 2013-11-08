package net.megx.ws.metagenome.traits;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;

@Path("v1/mg-traits/v1.0.0")
public class MetagenomeTraitsService {

    private MetagenomeTraitsAnalysis traitsAnalysis;
    private static final int[] ALL_FRAMES = { 1, 2, 3, -1, -2, -3 };
    private static final int LINE_SIZE = 60;

    public void setMetagenomeTraitsAnalysis(
            MetagenomeTraitsAnalysis traitsAnalysis) {
        this.traitsAnalysis = traitsAnalysis;
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
    public Response doAnalysis(@QueryParam("url") final String url,
            @Context HttpServletRequest request) {

        // TODO this should be put into on method
        final String username = request.getUserPrincipal() != null ? request
                .getUserPrincipal().getName() : null;
        if (username == null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        StreamingOutput so = new StreamingOutput() {

            @Override
            public void write(OutputStream output) throws IOException,
                    WebApplicationException {

                try {
                    
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        };
        
        
        ResponseBuilder rb = Response.ok(so);
       
        return rb.build();
    }
}
