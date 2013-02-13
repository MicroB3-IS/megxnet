package net.megx.ws.genomes.rest;

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

import net.megx.ws.core.CustomMediaType;
import net.megx.ws.core.Result;
import net.megx.ws.genomes.GCContent;

@Path("v1/gc-content/v1.0.0")
public class GCContentService extends GenomesRestService{
	
	private GCContent gcContent;
	
	public void setGcContent(GCContent gcContent) {
		this.gcContent = gcContent;
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON,
		CustomMediaType.APPLICATION_CSV,
		MediaType.TEXT_PLAIN})
	public Response gcContent(
		@QueryParam("infile") final String infile,
		@QueryParam("outfile") final String outfile,
			@Context HttpServletRequest request){
		final String username = request.getUserPrincipal() != null ?
				request.getUserPrincipal().getName() : null;
		if(username == null){
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		StreamingOutput so = new StreamingOutput() {
			
			@Override
			public void write(OutputStream out) throws IOException,
					WebApplicationException {
				try{
					if(outfile != null){
						gcContent.calculateGCContent(username, infile, outfile, true);
						Result<String> result = new Result<String>(outfile);
						out.write(toJSON(result).getBytes());
					}else{
						gcContent.calculateGCContent(username, infile, out, true);
					}
				} catch (Exception e) {
					handleWorkspaceAccessException(e);
				}
			}
		};
		ResponseBuilder rb = Response.ok(so);
		if(outfile != null && !"".equals(outfile.trim())){
			rb.type(MediaType.APPLICATION_JSON);
		}else{
			rb.type(CustomMediaType.APPLICATION_CSV);
		}
		return rb.build();
	}
}
