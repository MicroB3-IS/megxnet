package net.megx.ws.blast.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;

import net.megx.megdb.blast.BlastService;
import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.model.blast.BlastHits;
import net.megx.ws.core.BaseRestService;

import com.google.gson.GsonBuilder;

@Path("v1/megx-blast/v1.0.0")
public class BlastServiceAPI extends BaseRestService {

	private BlastService service;

	public BlastServiceAPI(BlastService service) {
		this.service = service;
		this.gson = new GsonBuilder().serializeNulls().create();
	}

	@Path("jobs")
	@POST
	@Produces("text/csv")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response postBlastJob(@FormParam("label") final String label,
			@FormParam("customer") final String customer,
			@FormParam("numNeighbors") final int numNeighbors,
			@FormParam("toolLabel") final String toolLabel,
			@FormParam("toolVer") final String toolVer,
			@FormParam("programName") final String programName,
			@FormParam("biodbLabel") final String biodbLabel,
			@FormParam("biodbVersion") final String biodbVersion,
			@FormParam("rawFasta") final String rawFasta,
			@Context HttpServletRequest request) {
		try {
			final String blastJobId;
			blastJobId = service.insertBlastJob(label, customer, numNeighbors,
					toolLabel, toolVer, programName, biodbLabel, biodbVersion,
					rawFasta);
			ResponseBuilder rb = Response
					.ok()
					.header("Location",
							request.getRequestURL() + "/" + blastJobId)
					.entity(new StreamingOutput() {
						@Override
						public void write(OutputStream out) throws IOException {
							PrintWriter writer = new PrintWriter(out);
							writer.println("Id,Label");
							writer.println(String.format("%s,%s", blastJobId,
									label));
							writer.flush();
							out.flush();
						}
					}).status(201);
			rb.type("text/csv");
			return rb.build();
		} catch (DBGeneralFailureException e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Path("results/unknown/{jid : \\d+}/network/{hitId : \\d+}")
	@Produces(MediaType.APPLICATION_XML)
	public String getSubnetGraphml(@PathParam("jid") int jid,
			@PathParam("hitId") String hitId,
			@Context HttpServletRequest request) {
		String subnetGraphml;
		try {
			BlastHits blastHits = service.getSubnetGraphml(jid, hitId);
			subnetGraphml = blastHits.getSubnetGraphml();
			return subnetGraphml;
		} catch (DBGeneralFailureException e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

}
