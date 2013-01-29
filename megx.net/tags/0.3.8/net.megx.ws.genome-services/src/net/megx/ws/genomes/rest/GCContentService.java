package net.megx.ws.genomes.rest;

import java.io.IOException;
import java.io.OutputStream;

import javax.jcr.ItemNotFoundException;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.security.AccessControlException;
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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import net.megx.storage.ResourceAccessException;
import net.megx.storage.StorageException;
import net.megx.storage.StorageSecuirtyException;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.Result;
import net.megx.ws.genomes.GCContent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Path("v1/gc-content/v1.0.0")
public class GCContentService extends BaseRestService{
	
	private GCContent gcContent;
	
	private Log log = LogFactory.getLog(getClass());
	
	
	public void setGcContent(GCContent gcContent) {
		this.gcContent = gcContent;
	}



	@GET
	@Produces(MediaType.TEXT_PLAIN)
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
				} catch (IOException e) {
					log.error(e);
					throw e;
				} catch (AccessControlException e) {
					log.error(e);
					throw new WebApplicationException(e, Status.UNAUTHORIZED);
				} catch(ItemNotFoundException e){ 
					log.error(e);
					throw new WebApplicationException(e, Status.NOT_FOUND);
				} catch(PathNotFoundException e){ 
					log.error(e);
					throw new WebApplicationException(e, Status.NOT_FOUND);
				} catch (NoSuchNodeTypeException e) {
					log.error(e);
					throw new WebApplicationException(e, Status.NOT_FOUND);
				} catch (RepositoryException e) {
					log.error(e);
					throw new WebApplicationException(e);
				}  catch (ResourceAccessException e) {
					log.error(e);
					throw new WebApplicationException(e, Status.NOT_FOUND);
				} catch (StorageSecuirtyException e) {
					log.error(e);
					throw new WebApplicationException(e, Status.UNAUTHORIZED);
				} catch (StorageException e) {
					log.error(e);
					throw new WebApplicationException(e);
				} catch (Exception e) {
					log.error(e);
					throw new WebApplicationException(e);
				}
			}
		};
		ResponseBuilder rb = Response.ok(so);
		if(outfile != null && !"".equals(outfile.trim())){
			rb.type(MediaType.APPLICATION_JSON);
		}else{
			rb.type("application/csv");
		}
		return rb.build();
	}
}
