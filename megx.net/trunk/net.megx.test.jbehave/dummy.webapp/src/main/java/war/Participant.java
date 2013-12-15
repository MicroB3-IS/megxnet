
package war;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/participant")
public class Participant {    
    
    @POST 
    @Path("new/{participant}")
    @Produces("text/plain")
    public Response getMessage(@PathParam("participant")String participant) {
        // in real application:
    	// data from form, not as parameter
    	// store new participant and
    	// return message only by successful storage    	
    	String message = "Saving successful";    	
    	return Response.status(201).entity(message).build();
    }    
}
