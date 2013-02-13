package net.megx.ws.genomes.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

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
import net.megx.ws.genomes.DiNucOddsRatio;

@Path("v1/di-nucleotides-odds-ratio/v1.0.0")
public class DiNucleotideOddsRatioService extends GenomesRestService{

	private DiNucOddsRatio oddsRatio;
	
	public DiNucleotideOddsRatioService(DiNucOddsRatio oddsRatio) {
		
		this.oddsRatio = oddsRatio;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON,
		CustomMediaType.APPLICATION_CSV,
		MediaType.TEXT_PLAIN})
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
		
		
		ResponseBuilder rb =  Response.ok().
				entity(new StreamingOutput() {
			
			@Override
			public void write(OutputStream out) throws IOException,
					WebApplicationException {
				try{
					if(outFile == null){
						oddsRatio.calculateDiNucleotideOddsRatio(username, inFile, out);
						out.flush();
					}else{
						oddsRatio.calculateDiNucleotideOddsRatio(username, inFile, outFile);
						Result<String> result = new Result<String>();
						result.setData(outFile);
						PrintWriter pw = new PrintWriter(out);
						pw.print(toJSON(result));
						pw.flush();
					}
					out.close();
				} catch (Exception e) {
					handleWorkspaceAccessException(e);
				}
			}
		});
		
		if(outFile == null){
			rb.type(CustomMediaType.APPLICATION_CSV);
		}else{
			rb.type(MediaType.APPLICATION_JSON);
		}
		
		return rb.build();
	}
}
