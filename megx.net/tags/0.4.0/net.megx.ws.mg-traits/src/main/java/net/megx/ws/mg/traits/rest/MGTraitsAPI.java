package net.megx.ws.mg.traits.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
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

import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.megdb.mgtraits.MGTraitsService;
import net.megx.model.mgtraits.MGTraitsAA;
import net.megx.model.mgtraits.MGTraitsCodon;
import net.megx.model.mgtraits.MGTraitsDNORatio;
import net.megx.model.mgtraits.MGTraitsDownloadJobs;
import net.megx.model.mgtraits.MGTraitsFunctional;
import net.megx.model.mgtraits.MGTraitsJobDetails;
import net.megx.model.mgtraits.MGTraitsPublicJobDetails;
import net.megx.model.mgtraits.MGTraitsResult;
import net.megx.model.mgtraits.MGTraitsTaxonomy;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.Result;
import net.megx.ws.core.providers.csv.ColumnNameFormat;
import net.megx.ws.core.providers.csv.annotations.CSVDocument;
import net.megx.ws.mg.traits.rest.mappers.DownloadJobsToClient;
import net.megx.ws.mg.traits.rest.mappers.JobDetailsToClient;
import net.megx.ws.mg.traits.rest.mappers.MGTraitsAAToClient;
import net.megx.ws.mg.traits.rest.mappers.MGTraitsCodonToClient;
import net.megx.ws.mg.traits.rest.mappers.MGTraitsDNORatioToClient;
import net.megx.ws.mg.traits.rest.mappers.PublicJobDetailsToClient;
import net.megx.ws.mg.traits.rest.mappers.SimpleTraitsToClient;

import org.apache.commons.lang.StringUtils;

import com.google.gson.GsonBuilder;

@Path("v1/mg-traits/v1.0.0")
public class MGTraitsAPI extends BaseRestService {

	private MGTraitsService service;

	private static final String SAMPLE_LABEL = "sample_label";

	private static final String JOB_DETAILS_HEADER = SAMPLE_LABEL
			+ ",environment"
			+ ",time_submitted,time_finished,return_code,error_message";

	private static final String JOB_DETAILS_ROW = "%s,%s,%s,%s,%s,%s";

	private static final String BASE_CONTEXT_PATH = "v1/mg-traits/v1.0.0";

	private static final String SAMPLE_PATH_MATCHER = "mg{id : \\d+}-{sample_name}";

	public MGTraitsAPI(MGTraitsService service) {
		this.service = service;
		this.gson = new GsonBuilder().serializeNulls().create();
	}

	private String jobAsCSV(MGTraitsJobDetails job) {
		return String.format(JOB_DETAILS_ROW, job.getPublicSampleLabel(),
				job.getSampleEnvironment(), job.getTimeSubmitted(),
				job.getTimeFinished(), job.getReturnCode(),
				job.getErrorMessage());
	}

	@Path(SAMPLE_PATH_MATCHER)
	@GET
	@Produces("text/csv")
	@CSVDocument(preserveHeaderColumns = true, columnNameFormat = ColumnNameFormat.FROM_CAMEL_CASE)
	public JobDetailsToClient getTraitOverview(@PathParam("id") int id,
			@Context HttpServletRequest request) {
		try {
			return new JobDetailsToClient(service.getSuccesfulJob(id));
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

	@Path(SAMPLE_PATH_MATCHER)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getTraitOverviewAsJSON(@PathParam("id") int id,
			@Context HttpServletRequest request) {
		try {
			return toJSON(new Result<JobDetailsToClient>(
					new JobDetailsToClient(service.getSuccesfulJob(id))));
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

	@Path("all")
	@GET
	@Produces("text/csv")
	@CSVDocument(preserveHeaderColumns = true, columnNameFormat = ColumnNameFormat.FROM_CAMEL_CASE)
	public List<PublicJobDetailsToClient> getAllPublicJobs(
			@Context HttpServletRequest request) {
		try {
			List<MGTraitsPublicJobDetails> result = service.getAllPublicJobs();
			List<PublicJobDetailsToClient> jobsToClient = new ArrayList<PublicJobDetailsToClient>();

			for (MGTraitsPublicJobDetails j : result) {
				jobsToClient.add(new PublicJobDetailsToClient(j));
			}

			return jobsToClient;
		} catch (DBGeneralFailureException e) {
			log.error("Could not retrieve all finished jobs");
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			log.error("No finished job exists");
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		} catch (IllegalStateException e) {
			log.error("Could not generate public Id");
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllPublicJobsAsJSON(@Context HttpServletRequest request) {
		try {
			List<MGTraitsPublicJobDetails> result = service.getAllPublicJobs();

			return toJSON(new Result<List<MGTraitsPublicJobDetails>>(result));

		} catch (DBGeneralFailureException e) {
			log.error("Could not retrieve all finished jobs");
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			log.error("No finished job exists");
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		} catch (IllegalStateException e) {
			log.error("Could not generate public Id");
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Path("all")
	@POST
	@Produces("text/csv")
	@CSVDocument(preserveHeaderColumns = true, columnNameFormat = ColumnNameFormat.FROM_CAMEL_CASE)
	public List<DownloadJobsToClient> downloadJobs(
			@Context HttpServletRequest request,
			@FormParam("traitIds") final String traitIds) {
		try {
			List<String> ids = new ArrayList<String>(Arrays.asList(traitIds
					.split(",")));
			List<Integer> intIds = new ArrayList<Integer>();
			for (String strId : ids) {
				intIds.add(Integer.valueOf(strId));
			}
			List<MGTraitsDownloadJobs> jobs = service.downloadJobs(intIds);
			List<DownloadJobsToClient> jobsToClient = new ArrayList<DownloadJobsToClient>();

			for (MGTraitsDownloadJobs j : jobs) {
				jobsToClient.add(new DownloadJobsToClient(j));
			}
			return jobsToClient;

		} catch (DBGeneralFailureException e) {
			log.error("Could not retrieve all finished jobs");
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			log.error("No finished job exists");
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		} catch (IllegalStateException e) {
			log.error("Could not generate public Id");
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Path(SAMPLE_PATH_MATCHER + "/simple-traits")
	@GET
	@Produces("text/csv")
	@CSVDocument(preserveHeaderColumns = true, columnNameFormat = ColumnNameFormat.FROM_CAMEL_CASE)
	public SimpleTraitsToClient getSimpleTraits(@PathParam("id") int id,
			@PathParam("sample_name") String sampleName,
			@Context HttpServletRequest request) {
		try {
			return new SimpleTraitsToClient(service.getSimpleTraits(id));
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

	@Path(SAMPLE_PATH_MATCHER + "/simple-traits")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getSimpleTraitsAsJSON(@PathParam("id") int id,
			@PathParam("sample_name") String sampleName,
			@Context HttpServletRequest request) {
		try {
			return (toJSON(new Result<MGTraitsResult>(
					service.getSimpleTraits(id))));
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

	@Path(SAMPLE_PATH_MATCHER + "/functional-content")
	@GET
	@Produces("text/csv")
	public Response getFunctionTable(@PathParam("id") int id,
			@PathParam("sample_name") String sampleName,
			@Context HttpServletRequest request) {
		try {
			List<MGTraitsFunctional> result = service.getFunctionTable(id);
			final List<String> header = new ArrayList<String>(result.size() + 1);
			final List<String> row = new ArrayList<String>(result.size() + 1);
			header.add("Id");
			row.add("mg" + result.get(0).getId() + "-" + result.get(0).getSampleLabel());
			for (MGTraitsFunctional currTax : result) {
				header.add(currTax.getKey());
				row.add(currTax.getValue());
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

	@Path(SAMPLE_PATH_MATCHER + "/functional-content")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getFunctionTableAsJSON(@PathParam("id") int id,
			@PathParam("sample_name") String sampleName,
			@Context HttpServletRequest request) {
		try {
			return (toJSON(new Result<List<MGTraitsFunctional>>(
					service.getFunctionTable(id))));
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

	@Path(SAMPLE_PATH_MATCHER + "/amino-acid-content")
	@GET
	@Produces("text/csv")
	@CSVDocument(preserveHeaderColumns = true, columnNameFormat = ColumnNameFormat.FROM_CAMEL_CASE)
	public MGTraitsAAToClient getAminoAcidContent(@PathParam("id") int id,
			@PathParam("sample_name") String sampleName,
			@Context HttpServletRequest request) {
		try {
			return new MGTraitsAAToClient(service.getAminoAcidContent(id));
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

	@Path(SAMPLE_PATH_MATCHER + "/amino-acid-content")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAminoAcidContentAsJSON(@PathParam("id") int id,
			@PathParam("sample_name") String sampleName,
			@Context HttpServletRequest request) {
		try {
			return toJSON(new Result<MGTraitsAA>(
					service.getAminoAcidContent(id)));
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

	@Path(SAMPLE_PATH_MATCHER + "/di-nucleotide-odds-ratio")
	@GET
	@Produces("text/csv")
	@CSVDocument(preserveHeaderColumns = true, columnNameFormat = ColumnNameFormat.FROM_CAMEL_CASE)
	public MGTraitsDNORatioToClient getDiNucleotideOddsRatio(
			@PathParam("id") int id,
			@PathParam("sample_name") String sampleName,
			@Context HttpServletRequest request) {
		try {
			return new MGTraitsDNORatioToClient(
					service.getDiNucleotideOddsRatio(id));
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

	@Path(SAMPLE_PATH_MATCHER + "/di-nucleotide-odds-ratio")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getDiNucleotideOddsRatioAsJSON(@PathParam("id") int id,
			@PathParam("sample_name") String sampleName,
			@Context HttpServletRequest request) {
		try {
			return toJSON(new Result<MGTraitsDNORatio>(
					service.getDiNucleotideOddsRatio(id)));
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

	private int getInternalId(String publicSampleLabel)
			throws NumberFormatException {
		String sampleId = publicSampleLabel.substring(2,
				publicSampleLabel.indexOf('-', 3));
		int id = Integer.parseInt(sampleId);

		return id;
	}

	// TODO: change this maybe to use SAMPLE_ATH_MATCHER mg{id}-{sample_name}
	@Path("jobs/{sampleLabel : .*}")
	@GET
	@Produces("text/csv")
	public Response getJobDetails(@PathParam("sampleLabel") String sampleLabel,
			@Context HttpServletRequest request) {

		int id = -10000;

		try {
			// skipping mg prefix and searching for eventuallay. 2nd - in case
			// id is a minus value

			id = this.getInternalId(sampleLabel);

			final MGTraitsJobDetails job = service.getJobDetails(id);

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
								+ BASE_CONTEXT_PATH + "/"
								+ job.getPublicSampleLabel());
			}
			// job finished and bad results
			if (job.getReturnCode() > 0) {
				rb = Response.status(Response.Status.OK);
			}

			rb = rb.entity(new StreamingOutput() {
				@Override
				public void write(OutputStream out) throws IOException {
					PrintWriter writer = new PrintWriter(out);
					writer.println(JOB_DETAILS_HEADER);
					writer.println(jobAsCSV(job));
					writer.flush();
					out.flush();
				}
			});
			rb.type("text/csv");
			return rb.build();
		} catch (DBGeneralFailureException e) {
			log.error("Db general error for id=" + id + "\n" + e);
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			log.error("No DB no record: for id=" + id + "\n" + e);
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		} catch (NumberFormatException e) {
			log.error("Could not parse internal id from " + sampleLabel);
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		}

		catch (Exception e) {
			log.error("Db exception for id=" + id + "\n" + e);
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Path("jobs")
	@POST
	@Produces("text/csv")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response postJob(@FormParam("customer") String customer,
			@FormParam("mg_url") final String mgUrl,
			@FormParam("sample_label") final String sampleLabel,
			@FormParam("sample_environment") String sampleEnvironment,
			@FormParam("sample_latitude") double sampleLatitude,
			@FormParam("sample_longitude") double sampleLongitude,
			@FormParam("sample_name") String sampleName,
			@FormParam("sample_description") String sampleDescription,
			@FormParam("sample_env_ontology") String sampleEnvOntology,
			@Context HttpServletRequest request) {
		try {
			final String publicSampleLabel;
			publicSampleLabel = service.insertJob(customer, mgUrl, sampleLabel,
					sampleEnvironment, sampleLatitude, sampleLongitude,
					sampleName, sampleDescription, sampleEnvOntology);
			ResponseBuilder rb = Response
					.ok()
					.header("Location",
							request.getRequestURL() + "/" + publicSampleLabel)
					.entity(new StreamingOutput() {
						@Override
						public void write(OutputStream out) throws IOException {
							PrintWriter writer = new PrintWriter(out);
							writer.println("Sample label,URL");
							writer.println(String.format("%s,%s",
									publicSampleLabel, mgUrl));
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

	@Path(SAMPLE_PATH_MATCHER + "/codon-usage")
	@GET
	@Produces("text/csv")
	@CSVDocument(preserveHeaderColumns = true, columnNameFormat = ColumnNameFormat.FROM_CAMEL_CASE)
	public MGTraitsCodonToClient getCodonUsage(@PathParam("id") int id,
			@PathParam("sample_name") String sampleName,
			@Context HttpServletRequest request) {
		try {
			return new MGTraitsCodonToClient(service.getCodonUsage(id));
		} catch (DBGeneralFailureException e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			log.error("No DB no record: \n" + e);
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		} catch (Exception e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Path(SAMPLE_PATH_MATCHER + "/codon-usage")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getCodonUsageAsJSON(@PathParam("id") int id,
			@PathParam("sample_name") String sampleName,
			@Context HttpServletRequest request) {
		try {
			return toJSON(new Result<MGTraitsCodon>(service.getCodonUsage(id)));
		} catch (DBGeneralFailureException e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			log.error("No DB no record: \n" + e);
			throw new WebApplicationException(e, Response.Status.NO_CONTENT);
		} catch (Exception e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Path(SAMPLE_PATH_MATCHER + "/taxonomic-content")
	@GET
	@Produces("text/csv")
	public Response getTaxonomicContent(@PathParam("id") int id,
			@PathParam("sample_name") String sampleName,
			@Context HttpServletRequest request) {
		try {
			List<MGTraitsTaxonomy> result = service.getTaxonomyContent(id);
			final List<String> header = new ArrayList<String>(result.size() + 1);
			final List<String> row = new ArrayList<String>(result.size() + 1);
			header.add("Id");
			row.add("mg" + result.get(0).getId() + "-" + result.get(0).getSampleLabel());
			for (MGTraitsTaxonomy currTax : result) {
				header.add(currTax.getKey());
				row.add(currTax.getValue());
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

	@Path(SAMPLE_PATH_MATCHER + "/taxonomic-content")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getTaxonomicContentAsJSON(@PathParam("id") int id,
			@PathParam("sample_name") String sampleName,
			@Context HttpServletRequest request) {
		try {
			return toJSON(new Result<List<MGTraitsTaxonomy>>(
					service.getTaxonomyContent(id)));
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
}
