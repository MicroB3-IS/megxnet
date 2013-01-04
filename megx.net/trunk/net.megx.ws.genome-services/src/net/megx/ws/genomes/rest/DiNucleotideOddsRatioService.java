package net.megx.ws.genomes.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.jcr.ItemNotFoundException;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.security.AccessControlException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import net.megx.storage.ResourceAccessException;
import net.megx.storage.StorageException;
import net.megx.storage.StorageSecuirtyException;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.Result;
import net.megx.ws.genomes.DiNucOddsRatio;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Path("v1/di-nucleotides-odds-ratio/v1.0.0")
public class DiNucleotideOddsRatioService extends BaseRestService{

	private DiNucOddsRatio oddsRatio;
	private Log log = LogFactory.getLog(getClass());
	
	public DiNucleotideOddsRatioService(DiNucOddsRatio oddsRatio) {
		this.oddsRatio = oddsRatio;
	}
	
	@GET
	public Response calculateDiNucOddsRatio(
			@QueryParam("infile") final String inFile,
			@QueryParam("outfile") final String outFile,
			@Context HttpServletRequest request
			){
		final String username = request.getUserPrincipal() != null ? request
				.getUserPrincipal().getName() : null;
		if (username == null) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		
		
		return Response.ok().entity(new StreamingOutput() {
			
			@Override
			public void write(OutputStream out) throws IOException,
					WebApplicationException {
				try{
					if(outFile == null){
						oddsRatio.calculateDiNucleotideOddsRatio(username, inFile, out);
					}else{
						oddsRatio.calculateDiNucleotideOddsRatio(username, inFile, outFile);
						Result<String> result = new Result<String>();
						result.setData(outFile);
						new PrintWriter(out).print(toJSON(result));
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
		}).build();
	}
}
