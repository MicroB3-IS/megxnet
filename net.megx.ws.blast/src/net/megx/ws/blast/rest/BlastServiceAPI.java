package net.megx.ws.blast.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.model.blast.BlastHits;
import net.megx.model.blast.BlastHitsDb;
import net.megx.model.blast.BlastHitsNeighbours;
import net.megx.model.blast.BlastJob;
import net.megx.model.blast.MatchingSequences;
import net.megx.ws.blast.rest.mappers.BlastJobDetailsToClient;
import net.megx.ws.blast.rest.mappers.BlastJobRawToClient;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.providers.csv.ColumnNameFormat;
import net.megx.ws.core.providers.csv.annotations.CSVDocument;

import org.apache.commons.lang.StringUtils;

import com.google.gson.GsonBuilder;

@Path("v1/megx-blast/v1.0.0")
//@Path("intern/megx-blast/")
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
			@FormParam("numNeighbors") final int numNeighbors,
			@FormParam("toolLabel") final String toolLabel,
			@FormParam("toolVer") final String toolVer,
			@FormParam("programName") final String programName,
			@FormParam("biodbLabel") final String biodbLabel,
			@FormParam("biodbVersion") final String biodbVersion,
			@FormParam("rawFasta") final String rawFasta,
			@FormParam("evalue") final double evalue,
			@Context HttpServletRequest request) {

		String customer = "";

		try {
			if (request.getUserPrincipal() != null) {
				customer = request.getUserPrincipal().getName();
			} else {
				customer = "megx";
			}

			final String blastJobId;
			blastJobId = service.insertBlastJob(label, customer, numNeighbors,
					toolLabel, toolVer, programName, biodbLabel, biodbVersion,
					rawFasta, evalue);
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
		} catch (DBNoRecordsException e) {
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		} catch (Exception e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Path("jobs/{blastJobId}")
	@GET
	@Produces("text/csv")
	public Response getBlastJobDetails(@PathParam("blastJobId") int blastJobId,
			@Context HttpServletRequest request) {

		try {
			final BlastJob job = service.getBlastJobDetails(blastJobId);

			ResponseBuilder rb = Response.ok();

			if (job == null) {
				rb = Response.status(Response.Status.NO_CONTENT);
			}

			// job is still running
			if (job.getReturnCode() == -1) {
				rb = Response.status(Response.Status.ACCEPTED);
			}
			// job finished and correct results
			if (job.getReturnCode() == 0) {
				rb = Response.status(Response.Status.SEE_OTHER);
				rb.header(
						"Location",
						request.getScheme() + "://" + request.getServerName()
								+ ":" + request.getServerPort()
								+ request.getContextPath() + "/ws/"
								+ "v1/megx-blast/v1.0.0" + "/" + job.getId());
			}
			// job finished and bad results
			if (job.getReturnCode() > 0) {
				rb = Response.status(Response.Status.OK);
			}

			// rb = rb.entity(toJSON(job));
			// rb.type("application/json");
			rb = rb.entity(new StreamingOutput() {
				@Override
				public void write(OutputStream out) throws IOException {
					PrintWriter writer = new PrintWriter(out);
					writer.println("Id,Label,TimeSubmitted,TimeFinished,ReturnCode");
					writer.println(jobAsCSV(job));
					writer.flush();
					out.flush();
				}
			});
			rb.type("text/csv");
			return rb.build();
		} catch (DBGeneralFailureException e) {
			log.error("Db general error for id=" + blastJobId + "\n" + e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			log.error("No DB no record: for id=" + blastJobId + "\n" + e);
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		} catch (Exception e) {
			log.error("Db exception for id=" + blastJobId + "\n" + e);
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	private String jobAsCSV(BlastJob job) {
		return String.format("%s,%s,%s,%s", job.getId(), job.getLabel(),
				job.getTimeSubmitted(), job.getTimeFinished(),
				job.getReturnCode());
	}

	@Path("{id}")
	@GET
	@Produces("text/csv")
	@CSVDocument(preserveHeaderColumns = true, columnNameFormat = ColumnNameFormat.FROM_CAMEL_CASE)
	public BlastJobDetailsToClient getBlastJobtOverview(
			@PathParam("id") int id, @Context HttpServletRequest request) {
		try {
			return new BlastJobDetailsToClient(service.getSuccesfulBlastJob(id));
		} catch (DBGeneralFailureException e) {
			log.error("Db general error for id=" + id + "\n" + e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			log.error("No DB no record: for id=" + id + "\n" + e);
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		} catch (Exception e) {
			log.error("Db exception for id=" + id + "\n" + e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Path("results/unknown/{jid : \\d+}/neighbours/{hitId : \\d+}")
	@Produces("text/csv")
	@CSVDocument(preserveHeaderColumns = true, columnNameFormat = ColumnNameFormat.FROM_CAMEL_CASE)
	public Response getNeighbours(@PathParam("jid") int jid,
			@PathParam("hitId") String hitId,
			@Context HttpServletRequest request) {
		try {
			List<BlastHitsNeighbours> result = service
					.getNeighbours(jid, hitId);
			final List<String> header = new ArrayList<String>(result.size() + 1);
			final List<String> row = new ArrayList<String>(result.size() + 1);
			header.add("Id");
			row.add(String.valueOf(result.get(0).getHitId()));
			for (BlastHitsNeighbours blNeighbours : result) {
				header.add(blNeighbours.getKey());
				row.add(blNeighbours.getValue());
			}
			ResponseBuilder rb = Response.ok().entity(new StreamingOutput() {
				@Override
				public void write(OutputStream out) throws IOException {
					PrintWriter writer = new PrintWriter(out);
					writer.println(StringUtils.join(header, ','));
					writer.println(StringUtils.join(row, ','));
					writer.flush();
					out.flush();
				}
			});
			rb.type("text/csv");
			return rb.build();
		} catch (DBGeneralFailureException e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		} catch (Exception e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Path("dbs")
	@Produces("text/csv")
	@CSVDocument(preserveHeaderColumns = true, columnNameFormat = ColumnNameFormat.FROM_CAMEL_CASE)
	public List<BlastHitsDb> getDatabases(@Context HttpServletRequest request) {
		try {
			return service.getDatabases();
		} catch (DBGeneralFailureException e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		} catch (Exception e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Path("results/unknown/{id : \\d+}/raw")
	@Produces(MediaType.APPLICATION_XML)
	public String getResultRaw(@PathParam("id") int id,
			@Context HttpServletRequest request) {
		String raw;
		try {
			BlastJob blastJob = service.getResultRaw(id);
			raw = blastJob.getResultRaw();
			return raw;
		} catch (DBGeneralFailureException e) {
			log.error("Db general error for id=" + id + "\n" + e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			log.error("No DB no record: for id=" + id + "\n" + e);
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		} catch (Exception e) {
			log.error("Db exception for id=" + id + "\n" + e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Path("geographic/{id : \\d+}/raw")
	@Produces("text/csv")
	@CSVDocument(preserveHeaderColumns = true, columnNameFormat = ColumnNameFormat.FROM_CAMEL_CASE)
	public BlastJobRawToClient getGeographicRaw(@PathParam("id") int id,
			@Context HttpServletRequest request) {
		try {
			return new BlastJobRawToClient(service.getGeographicRaw(id));
		} catch (DBGeneralFailureException e) {
			log.error("Db general error for id=" + id + "\n" + e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			log.error("No DB no record: for id=" + id + "\n" + e);
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		} catch (Exception e) {
			log.error("Db exception for id=" + id + "\n" + e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/*
	 * Initial version service for displaying matching-sequence table on result
	 * page !
	 */
	
	@GET
	@Path("matching-sequences")
	@Produces("text/csv")
	@CSVDocument(preserveHeaderColumns = true, columnNameFormat = ColumnNameFormat.FROM_CAMEL_CASE)
	public List<MatchingSequences> getMatchingSequences(
			@Context HttpServletRequest request) {
		try {
			return service.getMatchingSequences();
		} catch (DBGeneralFailureException e) {
			log.error("Db general error " + "\n" + e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			log.error("No DB no record:"  + "\n" + e);
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		} catch (Exception e) {
			log.error("Db exception "  + "\n" + e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

}