package net.megx.ws.genomes.rest;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import net.megx.ws.core.BaseRestService;
import net.megx.ws.genomes.GCContent;

@Path("v1/gc-content/v1.0.0")
public class GCContentService extends BaseRestService{
	
	private GCContent gcContent;
	
	
	
	public void setGcContent(GCContent gcContent) {
		this.gcContent = gcContent;
	}



	@GET
	public Response gcContent(
		@QueryParam("infile") final String infile,
		@QueryParam("outfile") final String outfile,
			@Context HttpServletRequest request){
		final String username = request.getUserPrincipal() != null ?
				request.getUserPrincipal().getName() : null;
		if(username == null){
			return Response.status(Response.Status.FORBIDDEN).build();
		}
		StreamingOutput so = new StreamingOutput() {
			
			@Override
			public void write(OutputStream out) throws IOException,
					WebApplicationException {
				try{
					if(outfile != null){
						gcContent.calculateGCContent(username, infile, outfile, true);
					}else{
						gcContent.calculateGCContent(username, infile, out, true);
					}
				}catch (IOException e) {
					throw e;
				}catch (Exception e) {
					throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
				}
			}
		};
		return Response.ok(so).build();
	}
}
