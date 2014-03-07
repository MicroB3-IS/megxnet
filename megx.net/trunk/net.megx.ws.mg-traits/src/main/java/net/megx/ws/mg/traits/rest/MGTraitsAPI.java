package net.megx.ws.mg.traits.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.google.gson.GsonBuilder;

import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.megdb.mgtraits.MGTraitsService;
import net.megx.model.mgtraits.MGTraitsAA;
import net.megx.model.mgtraits.MGTraitsCodon;
import net.megx.model.mgtraits.MGTraitsDNORatio;
import net.megx.model.mgtraits.MGTraitsJobDetails;
import net.megx.model.mgtraits.MGTraitsPfam;
import net.megx.model.mgtraits.MGTraitsResult;
import net.megx.model.mgtraits.MGTraitsTaxonomy;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.Result;

@Path("v1/mg-traits/v1.0.0")
public class MGTraitsAPI extends BaseRestService {

	private MGTraitsService service;

	private static final String SAMPLE_LABEL = "sample_label";

	private static final String SIMPLE_TRAITS_HEADER = SAMPLE_LABEL
			+ ",GcContent,GcVariance,NumGenes,TotalMB,NumReads,AbRatio,PercTF,PercClassified,Id";
	private static final String SIMPLE_TRAITS_ROW = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s";

	private static final String FUNCTION_TABLE_HEADER = SAMPLE_LABEL
			+ ",Pfam,Id";
	private static final String FUNCTION_TABLE_ROW = "%s,%s,%s";

	private static final String AMINO_ACID_HEADER = SAMPLE_LABEL
			+ ",ala,cys,asp,glu,phe,gly,his,ile,lys,leu,met,asn,pro,gln,arg,ser,thr,val,trp,tyr,id";
	private static final String AMINO_ACID_ROW = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s";

	private static final String DNORatio_HEADER = SAMPLE_LABEL
			+ ",paa_ptt,pac_pgt,pcc_pgg,pca_ptg,pga_ptc,pag_pct,pat,pcg,pgc,pta,id";
	private static final String DNORatio_ROW = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s";

	private static final String JOB_DETAILS_HEADER = SAMPLE_LABEL
			+ ",environment"
			+ ",time_submitted,time_finished,return_code,error_message";

	private static final String JOB_DETAILS_ROW = "%s,%s,%s,%s,%s,%s";

	private static final String CODON_USAGE_HEADER = "id,"
			+ SAMPLE_LABEL
			+ ",gcc,gcg,gct,tgc,tgt,gac,gat,gaa,gag,ttc,ttt,gga,ggc,ggg,ggt,cac,cat,ata,atc,att,aaa,aag,cta,ctc,ctg,ctt,tta,ttg,atg,aac,aat,cca,ccc,ccg,cct,caa,cag,aga,agg,cga,cgc,cgg,cgt,agc,agt,tca,tcc,tcg,tct,aca,acc,acg,act,gta,gtc,gtg,gtt,tgg,tac,tat";
	private static final String CODON_USAGE_ROW = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s";

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
	
	private Map<String, String> jobAsJSON(MGTraitsJobDetails job){
		Map<String, String> result = new HashMap<String, String>();
		result.put("sample_label", job.getSampleLabel());
		result.put("environment", job.getSampleEnvironment());
		result.put("time_submitted", job.getTimeSubmitted());
		result.put("time_finished", job.getTimeFinished());
		result.put("return_code", String.valueOf(job.getReturnCode()));
		result.put("error_message", job.getErrorMessage());
		return result;
	}
	
	@Path(SAMPLE_PATH_MATCHER)
	@GET
	@Produces({ "text/csv", MediaType.APPLICATION_JSON })
	public Response getTraitOverview(@PathParam("id") int id,
			@Context HttpServletRequest request) {
		try {
			final MGTraitsJobDetails jobDetail = service.getSuccesfulJob(id);
			if (request.getHeader("Accept")
					.contains(MediaType.APPLICATION_JSON)) {
				ResponseBuilder rb = Response.ok().entity(
						new StreamingOutput() {
							@Override
							public void write(OutputStream out)
									throws IOException {
								PrintWriter writer = new PrintWriter(out);
								writer.println(toJSON(new Result<Map<String, String>>(
										jobAsJSON(jobDetail))));
								writer.flush();
								out.flush();
							}
						});
				rb.type(MediaType.APPLICATION_JSON);
				return rb.build();
			} else {
				ResponseBuilder rb = Response.ok().entity(
						new StreamingOutput() {
							@Override
							public void write(OutputStream out)
									throws IOException {
								PrintWriter writer = new PrintWriter(out);
								writer.println(JOB_DETAILS_HEADER);
								writer.println(jobAsCSV(jobDetail));
								writer.flush();
								out.flush();
							}
						});
				rb.type("text/csv");
				return rb.build();
			}
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
	@Produces({ "text/csv", MediaType.APPLICATION_JSON })
	public Response getAllFinishedJobs(@Context HttpServletRequest request) {
		try {
			final List<MGTraitsJobDetails> result = service
					.getAllFinishedJobs();
			if (request.getHeader("Accept")
					.contains(MediaType.APPLICATION_JSON)) {
				ResponseBuilder rb = Response.ok().entity(
						new StreamingOutput() {
							@Override
							public void write(OutputStream out)
									throws IOException {
								PrintWriter writer = new PrintWriter(out);
								List<Map<String, String>> allJobsMap = new ArrayList<Map<String,String>>();
								for (MGTraitsJobDetails mgTraitsJobDetails : result) {
									allJobsMap.add(jobAsJSON(mgTraitsJobDetails));
								}
								writer.println(toJSON(new Result<List<Map<String, String>>>(
										allJobsMap)));
								writer.flush();
								out.flush();
							}
						});
				rb.type(MediaType.APPLICATION_JSON);
				return rb.build();
			} else {
				ResponseBuilder rb = Response.ok().entity(
						new StreamingOutput() {
							@Override
							public void write(OutputStream out)
									throws IOException {
								PrintWriter writer = new PrintWriter(out);
								writer.println(JOB_DETAILS_HEADER);
								for (MGTraitsJobDetails currJobDetail : result) {
									writer.println(jobAsCSV(currJobDetail));
								}
								writer.flush();
								out.flush();
							}
						});

				rb.type("text/csv");
				return rb.build();
			}
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
	@Produces({ "text/csv", MediaType.APPLICATION_JSON })
	public Response getSimpleTraits(@PathParam("id") int id,
			@PathParam("sample_name") String sampleName,
			@Context HttpServletRequest request) {
		try {
			final MGTraitsResult mgTrait = service.getSimpleTraits(id);
			if (request.getHeader("Accept")
					.contains(MediaType.APPLICATION_JSON)) {
				ResponseBuilder rb = Response.ok().entity(
						new StreamingOutput() {
							@Override
							public void write(OutputStream out)
									throws IOException {
								PrintWriter writer = new PrintWriter(out);
								writer.println(toJSON(new Result<MGTraitsResult>(
										mgTrait)));
								writer.flush();
								out.flush();
							}
						});
				rb.type(MediaType.APPLICATION_JSON);
				return rb.build();
			} else {
				ResponseBuilder rb = Response.ok().entity(
						new StreamingOutput() {
							@Override
							public void write(OutputStream out)
									throws IOException {
								PrintWriter writer = new PrintWriter(out);
								writer.println(SIMPLE_TRAITS_HEADER);
								writer.println(String.format(SIMPLE_TRAITS_ROW,
										mgTrait.getSampleLabel(),
										mgTrait.getGcContent(),
										mgTrait.getGcVariance(),
										mgTrait.getNumGenes(),
										mgTrait.getTotalMB(),
										mgTrait.getNumReads(),
										mgTrait.getABRatio(),
										mgTrait.getPercTf(),
										mgTrait.getPercClassified(),
										mgTrait.getId()));
								writer.flush();
								out.flush();
							}
						});

				rb.type("text/csv");
				return rb.build();
			}
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

	@Path(SAMPLE_PATH_MATCHER + "/function-table")
	@GET
	@Produces({ "text/csv", MediaType.APPLICATION_JSON })
	public Response getFunctionTable(@PathParam("id") int id,
			@PathParam("sample_name") String sampleName,
			@Context HttpServletRequest request) {
		try {
			final MGTraitsPfam functionTable = service.getFunctionTable(id);
			final String pfams = Arrays.toString(functionTable.getPfam());
			final String pfamsString = pfams.substring(pfams.indexOf('['),
					pfams.indexOf(']'));
			if (request.getHeader("Accept")
					.contains(MediaType.APPLICATION_JSON)) {
				ResponseBuilder rb = Response.ok().entity(
						new StreamingOutput() {
							@Override
							public void write(OutputStream out)
									throws IOException {
								PrintWriter writer = new PrintWriter(out);
								writer.println(toJSON(new Result<MGTraitsPfam>(
										functionTable)));
								writer.flush();
								out.flush();
							}
						});
				rb.type(MediaType.APPLICATION_JSON);
				return rb.build();
			} else {
				ResponseBuilder rb = Response.ok().entity(
						new StreamingOutput() {
							@Override
							public void write(OutputStream out)
									throws IOException {
								PrintWriter writer = new PrintWriter(out);
								writer.println(FUNCTION_TABLE_HEADER);
								writer.println(String.format(
										FUNCTION_TABLE_ROW,
										functionTable.getSampleLabel(),
										pfamsString, functionTable.getId()));
								writer.flush();
								out.flush();
							}
						});

				rb.type("text/csv");
				return rb.build();
			}
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
	@Produces({ "text/csv", MediaType.APPLICATION_JSON })
	public Response getAminoAcidContent(@PathParam("id") int id,
			@PathParam("sample_name") String sampleName,
			@Context HttpServletRequest request) {
		try {
			final MGTraitsAA aminoAcid = service.getAminoAcidContent(id);
			if (request.getHeader("Accept")
					.contains(MediaType.APPLICATION_JSON)) {
				ResponseBuilder rb = Response.ok().entity(
						new StreamingOutput() {
							@Override
							public void write(OutputStream out)
									throws IOException {
								PrintWriter writer = new PrintWriter(out);
								writer.println(toJSON(new Result<MGTraitsAA>(
										aminoAcid)));
								writer.flush();
								out.flush();
							}
						});
				rb.type(MediaType.APPLICATION_JSON);
				return rb.build();
			} else {
				ResponseBuilder rb = Response.ok().entity(
						new StreamingOutput() {
							@Override
							public void write(OutputStream out)
									throws IOException {
								PrintWriter writer = new PrintWriter(out);
								writer.println(AMINO_ACID_HEADER);
								writer.println(String.format(AMINO_ACID_ROW,
										aminoAcid.getSampleLabel(),
										aminoAcid.getAla(), aminoAcid.getCys(),
										aminoAcid.getAsp(), aminoAcid.getGlu(),
										aminoAcid.getPhe(), aminoAcid.getGly(),
										aminoAcid.getHis(), aminoAcid.getIle(),
										aminoAcid.getLys(), aminoAcid.getLeu(),
										aminoAcid.getMet(), aminoAcid.getAsn(),
										aminoAcid.getPro(), aminoAcid.getGln(),
										aminoAcid.getArg(), aminoAcid.getSer(),
										aminoAcid.getThr(), aminoAcid.getVal(),
										aminoAcid.getTrp(), aminoAcid.getTyr(),
										aminoAcid.getId()));
								writer.flush();
								out.flush();
							}
						});

				rb.type("text/csv");
				return rb.build();
			}
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
	@Produces({ "text/csv", MediaType.APPLICATION_JSON })
	public Response getDiNucleotideOddsRatio(@PathParam("id") int id,
			@PathParam("sample_name") String sampleName,
			@Context HttpServletRequest request) {
		try {
			final MGTraitsDNORatio dnoRatio = service
					.getDiNucleotideOddsRatio(id);
			if (request.getHeader("Accept")
					.contains(MediaType.APPLICATION_JSON)) {
				ResponseBuilder rb = Response.ok().entity(
						new StreamingOutput() {
							@Override
							public void write(OutputStream out)
									throws IOException {
								PrintWriter writer = new PrintWriter(out);
								writer.println(toJSON(new Result<MGTraitsDNORatio>(
										dnoRatio)));
								writer.flush();
								out.flush();
							}
						});
				rb.type(MediaType.APPLICATION_JSON);
				return rb.build();
			} else {
				ResponseBuilder rb = Response.ok().entity(
						new StreamingOutput() {
							@Override
							public void write(OutputStream out)
									throws IOException {
								PrintWriter writer = new PrintWriter(out);
								writer.println(DNORatio_HEADER);
								writer.println(String.format(DNORatio_ROW,
										dnoRatio.getSampleLabel(),
										dnoRatio.getPaa_ptt(),
										dnoRatio.getPac_pgt(),
										dnoRatio.getPcc_pgg(),
										dnoRatio.getPca_ptg(),
										dnoRatio.getPga_ptc(),
										dnoRatio.getPag_pct(),
										dnoRatio.getPat(), dnoRatio.getPcg(),
										dnoRatio.getPgc(), dnoRatio.getPta(),
										dnoRatio.getId()));
								writer.flush();
								out.flush();
							}
						});

				rb.type("text/csv");
				return rb.build();
			}
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
			@Context HttpServletRequest request) {
		try {
			final String publicSampleLabel;
			publicSampleLabel = service.insertJob(customer, mgUrl, sampleLabel,
					sampleEnvironment);
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

	@Path("codon-usage")
	@GET
	@Produces({ "text/csv", MediaType.APPLICATION_JSON })
	public Response getCodonUsage(@Context HttpServletRequest request) {
		try {
			final List<MGTraitsCodon> result = service.getCodonUsage();
			if (request.getHeader("Accept")
					.contains(MediaType.APPLICATION_JSON)) {
				ResponseBuilder rb = Response.ok().entity(
						new StreamingOutput() {
							@Override
							public void write(OutputStream out)
									throws IOException {
								PrintWriter writer = new PrintWriter(out);
								writer.println(toJSON(new Result<List<MGTraitsCodon>>(
										result)));
								writer.flush();
								out.flush();
							}
						});
				rb.type(MediaType.APPLICATION_JSON);
				return rb.build();
			} else {
				ResponseBuilder rb = Response.ok().entity(
						new StreamingOutput() {
							@Override
							public void write(OutputStream out)
									throws IOException {
								PrintWriter writer = new PrintWriter(out);
								writer.println(CODON_USAGE_HEADER);
								for (MGTraitsCodon codon : result) {
									writer.println(String.format(
											CODON_USAGE_ROW, codon.getId(),
											codon.getSample_label(),
											codon.getGcc(), codon.getGcg(),
											codon.getGct(), codon.getTgc(),
											codon.getTgt(), codon.getGac(),
											codon.getGat(), codon.getGaa(),
											codon.getGag(), codon.getTtc(),
											codon.getTtt(), codon.getGga(),
											codon.getGgc(), codon.getGgg(),
											codon.getGgt(), codon.getCac(),
											codon.getCat(), codon.getAta(),
											codon.getAtc(), codon.getAtt(),
											codon.getAaa(), codon.getAag(),
											codon.getCta(), codon.getCtc(),
											codon.getCtg(), codon.getCtt(),
											codon.getTta(), codon.getTtg(),
											codon.getAtg(), codon.getAac(),
											codon.getAat(), codon.getCca(),
											codon.getCcc(), codon.getCcg(),
											codon.getCct(), codon.getCaa(),
											codon.getCag(), codon.getAga(),
											codon.getAgg(), codon.getCga(),
											codon.getCgc(), codon.getCgg(),
											codon.getCgt(), codon.getAgc(),
											codon.getAgt(), codon.getTca(),
											codon.getTcc(), codon.getTcg(),
											codon.getTct(), codon.getAcc(),
											codon.getAcg(), codon.getAct(),
											codon.getGta(), codon.getAca(),
											codon.getGtc(), codon.getGtg(),
											codon.getGtt(), codon.getTgg(),
											codon.getTac(), codon.getTat()));
								}
								writer.flush();
								out.flush();
							}
						});
				rb.type("text/csv");
				return rb.build();
			}
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

	@Path("taxonomic-content")
	@GET
	@Produces({ "text/csv", MediaType.APPLICATION_JSON })
	public Response getTaxonomicContent(@Context HttpServletRequest request) {
		try {
			final List<MGTraitsTaxonomy> taxonomyContent = service
					.getTaxonomyContent();
			if (request.getHeader("Accept")
					.contains(MediaType.APPLICATION_JSON)) {
				ResponseBuilder rb = Response.ok().entity(
						new StreamingOutput() {
							@Override
							public void write(OutputStream out)
									throws IOException {
								PrintWriter writer = new PrintWriter(out);
								writer.println(toJSON(new Result<List<MGTraitsTaxonomy>>(
										taxonomyContent)));
								writer.flush();
								out.flush();
							}
						});
				rb.type(MediaType.APPLICATION_JSON);
				return rb.build();
			} else {
				ResponseBuilder rb = Response.ok().entity(
						new StreamingOutput() {
							@Override
							public void write(OutputStream out)
									throws IOException {
								PrintWriter writer = new PrintWriter(out);
								Map<String, List<Map<String, String>>> rows = getRowStructure(taxonomyContent);
								printTaxonomyHeaderRow(rows, writer);
								printTaxonomyDataRows(rows, writer);
								writer.flush();
								out.flush();
							}
						});

				rb.type("text/csv");
				return rb.build();
			}
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

	private void printTaxonomyHeaderRow(
			Map<String, List<Map<String, String>>> rows, PrintWriter writer) {
		writer.print("Site ");
		// We take a random key here under the assumption that every key in the
		// rows map has the same values
		String sampleKeyRow = rows.keySet().iterator().next();
		List<Map<String, String>> namesList = rows.get(sampleKeyRow);
		for (Map<String, String> nameValMap : namesList) {
			Set<String> keySet = nameValMap.keySet();
			for (String key : keySet) {
				writer.print(" " + key + " ");
			}

		}
		writer.println();
	}

	private void printTaxonomyDataRows(
			Map<String, List<Map<String, String>>> rows, PrintWriter writer) {
		for (String row : rows.keySet()) {
			writer.print(row);
			List<Map<String, String>> namesList = rows.get(row);
			for (Map<String, String> nameValMap : namesList) {
				Set<String> keySet = nameValMap.keySet();
				for (String key : keySet) {
					writer.print(" " + nameValMap.get(key) + " ");
				}
			}
			writer.println();
		}
	}

	private Map<String, List<Map<String, String>>> getRowStructure(
			List<MGTraitsTaxonomy> taxonomyContent) {
		Map<String, List<Map<String, String>>> rows = new HashMap<String, List<Map<String, String>>>();
		for (MGTraitsTaxonomy currContent : taxonomyContent) {
			Map<String, String> currNameVal = new HashMap<String, String>();
			currNameVal.put(currContent.getKey(), currContent.getValue());
			List<Map<String, String>> rowData;
			if (!rows.containsKey(currContent.getSampleLabel())) {
				rowData = new ArrayList<Map<String, String>>();
			} else {
				rowData = rows.get(currContent.getSampleLabel());
			}
			rowData.add(currNameVal);
			rows.put(currContent.getSampleLabel(), rowData);
		}

		return rows;
	}

}
