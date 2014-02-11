package net.megx.ws.mg.traits.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;


import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.exceptions.DBNoRecordsException;
import net.megx.megdb.mgtraits.MGTraitsService;
import net.megx.model.mgtraits.MGTraitsAA;
import net.megx.model.mgtraits.MGTraitsDNORatio;
import net.megx.model.mgtraits.MGTraitsJobDetails;
import net.megx.model.mgtraits.MGTraitsPfam;
import net.megx.model.mgtraits.MGTraitsResult;

@Path("v1/mg-traits/v1.0.0")
public class MGTraitsAPI extends BaseRestService {

	private MGTraitsService service;

	private static final String SIMPLE_TRAITS_HEADER = "Sample label,GcContent,GcVariance,NumGenes,TotalMB,NumReads,AbRatio,PercTF,PercClassified,Id";
	private static final String SIMPLE_TRAITS_ROW = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s";

	private static final String FUNCTION_TABLE_HEADER = "Sample label,Pfam,Id";
	private static final String FUNCTION_TABLE_ROW = "%s,%s,%s";

	private static final String AMINO_ACID_HEADER = "Sample label,ala,cys,asp,glu,phe,gly,his,ile,lys,leu,met,asn,pro,gln,arg,ser,thr,val,trp,tyr,id";
	private static final String AMINO_ACID_ROW = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s";

	private static final String DNORatio_HEADER = "Sample label,paa_ptt,pac_pgt,pcc_pgg,pca_ptg,pga_ptc,pag_pct,pat,pcg,pgc,pta,id";
	private static final String DNORatio_ROW = "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s";

	private static final String JOB_DETAILS_HEADER = "Id,sample label,time submitted,time finished,return code,error message";
	private static final String JOB_DETAILS_ROW = "%s,%s,%s,%s,%s,%s";

	public MGTraitsAPI(MGTraitsService service) {
		this.service = service;
	}

	@Path("mg{id}-{sample_name}/simple-traits")
	@GET
	@Produces("text/csv")
	public Response getSimpleTraits(@PathParam("id") int id,
			@PathParam("sample_name") String sampleName) {
		try {
			final List<MGTraitsResult> result = service.getSimpleTraits(id);
			ResponseBuilder rb = Response.ok().entity(new StreamingOutput() {
				@Override
				public void write(OutputStream out) throws IOException {
					PrintWriter writer = new PrintWriter(out);
					writer.println(SIMPLE_TRAITS_HEADER);
					for (MGTraitsResult mgTrait : result) {
						writer.println(String.format(SIMPLE_TRAITS_ROW,
								mgTrait.getSampleLabel(),
								mgTrait.getGcContent(),
								mgTrait.getGcVariance(), mgTrait.getNumGenes(),
								mgTrait.getTotalMB(), mgTrait.getNumReads(),
								mgTrait.getABRatio(), mgTrait.getPercTf(),
								mgTrait.getPercClassified(), mgTrait.getId()));
					}
					writer.flush();
					out.flush();
				}
			});

			rb.type("text/csv");
			return rb.build();
		} catch (DBGeneralFailureException e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			throw new WebApplicationException(Response.Status.NO_CONTENT);
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Path("mg{id}-{sample_name}/function-table")
	@GET
	@Produces("text/csv")
	public Response getFunctionTable(@PathParam("id") int id,
			@PathParam("sample_name") String sampleName) {
		try {
			final List<MGTraitsPfam> result = service.getFunctionTable(id);
			ResponseBuilder rb = Response.ok().entity(new StreamingOutput() {
				@Override
				public void write(OutputStream out) throws IOException {
					PrintWriter writer = new PrintWriter(out);
					writer.println(FUNCTION_TABLE_HEADER);
					for (MGTraitsPfam functionTable : result) {
						writer.println(String.format(FUNCTION_TABLE_ROW,
								functionTable.getSampleLabel(),
								Arrays.toString(functionTable.getPfam()), functionTable.getId()));
					}
					writer.flush();
					out.flush();
				}
			});

			rb.type("text/csv");
			return rb.build();
		} catch (DBGeneralFailureException e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			throw new WebApplicationException(Response.Status.NO_CONTENT);
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Path("mg{id}-{sample_name}/amino-acid-content")
	@GET
	@Produces("text/csv")
	public Response getAminoAcidContent(@PathParam("id") int id,
			@PathParam("sample_name") String sampleName) {
		try {
			final List<MGTraitsAA> result = service.getAminoAcidContent(id);
			ResponseBuilder rb = Response.ok().entity(new StreamingOutput() {
				@Override
				public void write(OutputStream out) throws IOException {
					PrintWriter writer = new PrintWriter(out);
					writer.println(AMINO_ACID_HEADER);
					for (MGTraitsAA aminoAcid : result) {
						writer.println(String.format(AMINO_ACID_ROW,
								aminoAcid.getSampleLabel(), aminoAcid.getAla(),
								aminoAcid.getCys(), aminoAcid.getAsp(),
								aminoAcid.getGlu(), aminoAcid.getPhe(),
								aminoAcid.getGly(), aminoAcid.getHis(),
								aminoAcid.getIle(), aminoAcid.getLys(),
								aminoAcid.getLeu(), aminoAcid.getMet(),
								aminoAcid.getAsn(), aminoAcid.getPro(),
								aminoAcid.getGln(), aminoAcid.getArg(),
								aminoAcid.getSer(), aminoAcid.getThr(),
								aminoAcid.getVal(), aminoAcid.getTrp(),
								aminoAcid.getTyr(), aminoAcid.getId()));
					}
					writer.flush();
					out.flush();
				}
			});

			rb.type("text/csv");
			return rb.build();
		} catch (DBGeneralFailureException e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			throw new WebApplicationException(Response.Status.NO_CONTENT);
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Path("mg{id}-{sample_name}/di-nucleotide-odds-ratio")
	@GET
	@Produces("text/csv")
	public Response getDiNucleotideOddsRatio(@PathParam("id") int id,
			@PathParam("sample_name") String sampleName) {
		try {
			final List<MGTraitsDNORatio> result = service
					.getDiNucleotideOddsRatio(id);
			ResponseBuilder rb = Response.ok().entity(new StreamingOutput() {
				@Override
				public void write(OutputStream out) throws IOException {
					PrintWriter writer = new PrintWriter(out);
					writer.println(DNORatio_HEADER);
					for (MGTraitsDNORatio dnoRatio : result) {
						writer.println(String.format(DNORatio_ROW,
								dnoRatio.getSampleLabel(),
								dnoRatio.getPaa_ptt(), dnoRatio.getPac_pgt(),
								dnoRatio.getPcc_pgg(), dnoRatio.getPca_ptg(),
								dnoRatio.getPga_ptc(), dnoRatio.getPag_pct(),
								dnoRatio.getPat(), dnoRatio.getPcg(),
								dnoRatio.getPgc(), dnoRatio.getPta(),
								dnoRatio.getId()));
					}
					writer.flush();
					out.flush();
				}
			});

			rb.type("text/csv");
			return rb.build();
		} catch (DBGeneralFailureException e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			throw new WebApplicationException(Response.Status.NO_CONTENT);
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Path("jobs/{sampleLabel}")
	@GET
	@Produces("text/csv")
	public Response getJobDetails(@PathParam("sampleLabel") String sampleLabel, @Context HttpServletRequest request) {
		try {
			final List<MGTraitsJobDetails> result = service
					.getJobDetails(sampleLabel);
			ResponseBuilder rb = Response.ok().entity(new StreamingOutput() {
				@Override
				public void write(OutputStream out) throws IOException {
					PrintWriter writer = new PrintWriter(out);
					writer.println(JOB_DETAILS_HEADER);
					for (MGTraitsJobDetails currJobDetail : result) {
						writer.println(String.format(JOB_DETAILS_ROW,
								currJobDetail.getId(),
								currJobDetail.getSampleLabel(),
								currJobDetail.getTimeSubmitted(),
								currJobDetail.getTimeFinished(),
								currJobDetail.getReturnCode(),
								currJobDetail.getErrorMessage()));
					}
					writer.flush();
					out.flush();
				}
			});
			
			//Check if job finished
			for (MGTraitsJobDetails currJobDetail : result){
				if(currJobDetail.getTimeFinished() != null){
					rb.header("Location", request.getRequestURL());
					break;
				}
			}
			
			rb.type("text/csv");
			return rb.build();
		} catch (DBGeneralFailureException e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			throw new WebApplicationException(Response.Status.NO_CONTENT);
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Path("jobs")
	@POST
	@Produces("text/csv")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	
	public Response postJob(
			@FormParam("customer") String customer,
			@FormParam("mg_url") final String mgUrl,
			@FormParam("sample_label") final String sampleLabel,
			@FormParam("sample_environment") String sampleEnvironment,
			@Context HttpServletRequest request) {
		try {
			String jobUrl = service.insertJob(customer, mgUrl, sampleLabel,
					sampleEnvironment);
			ResponseBuilder rb = Response.ok()
					.header("Location", request.getRequestURL() + "/" + jobUrl)
					.entity(new StreamingOutput() {
						@Override
						public void write(OutputStream out) throws IOException {
							PrintWriter writer = new PrintWriter(out);
							writer.println("Sample label,URL");
							writer.println(String.format("%s,%s", sampleLabel, mgUrl));
							writer.flush();
							out.flush();
						}
					}).status(201);
			rb.type("text/csv");
			return rb.build();
		} catch (DBGeneralFailureException e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Path("all")
	@GET
	@Produces("text/csv")
	public Response getAllFinishedJobs(@Context HttpServletRequest request) {
		try {
			final List<MGTraitsJobDetails> result = service
					.getAllFinishedJobs();
			ResponseBuilder rb = Response.ok().entity(new StreamingOutput() {
				@Override
				public void write(OutputStream out) throws IOException {
					PrintWriter writer = new PrintWriter(out);
					writer.println("sample_label");
					for (MGTraitsJobDetails currJobDetail : result) {
						writer.println(String.format("%s",		
								currJobDetail.getSampleLabel()));			
					}
					writer.flush();
					out.flush();
				}
			});
			
			rb.type("text/csv");
			return rb.build();
		} catch (DBGeneralFailureException e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		} catch (DBNoRecordsException e) {
			throw new WebApplicationException(Response.Status.NO_CONTENT);
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

}
