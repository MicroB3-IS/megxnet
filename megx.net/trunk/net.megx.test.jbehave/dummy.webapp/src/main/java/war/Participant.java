
package war;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/participant")
public class Participant {    
    
    @GET 
    @Path("new/{participant}")
    public Response getMessage(@PathParam("participant")String participant) {
        // in real application store new participant and
    	// return message only by successful storage    	
    	String message = "Saving successful";
    	return Response.status(200).entity(message).build();
    }    
}
