package net.megx.mibig.rest;

import java.net.URI;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.mibig.MibigService;
import net.megx.mibig.model.BgcDetailRawSubmission;
import net.megx.model.mibig.BgcDetailSubmission;
import net.megx.model.mibig.MibigSubmission;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.Result;

@Path("v1/mibig/v1.0.0")
public class MibigAPI extends BaseRestService {

	private MibigService service;

	@Context
	private UriInfo uriInfo;
	@Context
	HttpServletRequest request;

	public MibigAPI(MibigService service) {
		this.service = service;
	}

	@Path("bgc-registration")
	@GET
	public String serviceInfo() {
		log.debug("getting a bgc submission service info request");
		return "bgc registration up and running";
	}

	@Path("bgc-registration")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response storeMibigSubmission(@FormParam("json") String mibigJson,
			@FormParam("version") String version) {

		int ver = 0;
		log.debug("getting a bgc submission POST request=" + version);

		try {
			if (mibigJson == null) {
				return Response
						.status(Status.BAD_REQUEST)
						.entity(toJSON(new Result<String>(true,
								"json not provided", "bad-request"))).build();
			}
			if (version == null) {
				return Response
						.status(Status.BAD_REQUEST)
						.entity(toJSON(new Result<String>(
								true,
								"Version parameter not provided. Need a version parameter greater than 0",
								"bad-request"))).build();
			} else {
				ver = Integer.parseInt(version);
			}

			MibigSubmission mibig = new MibigSubmission();

			mibig.setRaw(mibigJson);
			mibig.setSubmitted(Calendar.getInstance().getTime());
			mibig.setModified(Calendar.getInstance().getTime());
			mibig.setVersion(ver);

			service.storeMibigSubmission(mibig);

			// String address = uriInfo.getAbsolutePathBuilder();
			URI u = uriInfo.getAbsolutePath();
			URI location = UriBuilder
					.fromUri(u)
					.replacePath(
							request.getContextPath() + "/mibig/bgc-submission")
					.build();

			log.debug("uri absolute=" + u.toASCIIString());
			log.debug("context path=" + request.getContextPath());
			log.debug("location=" + location.toString());
			return Response.seeOther(location).build();
		} catch (DBGeneralFailureException e) {
			log.error("Could not store Submission:" + e);
			return Response.serverError()
					.entity(toJSON("Could not store Submission")).build();
		} catch (NumberFormatException e) {
			// TODO: handle exception
			return Response
					.status(Status.BAD_REQUEST)
					.entity(toJSON("Version parameter not a valid number: "
							+ version)).build();
		}
	}

	@Path("bgc-detail-registration")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response storeBgcDetailSubmission(
			@FormParam("data") String data,
			@FormParam("version") String version) {

		BgcDetailSubmission bgc = new BgcDetailSubmission();
		BgcDetailRawSubmission bgcRaw = new BgcDetailRawSubmission();
		int ver = 0;
		log.debug("getting a bgc submission POST request=" + version);

		try {
			if (data == null) {
				return Response
						.status(Status.BAD_REQUEST)
						.entity(toJSON(new Result<String>(true,
								"json not provided", "bad-request"))).build();
			}
			if (version == null) {
				ver = 1;
			} else {
				ver = Integer.parseInt(version);
			}

			bgcRaw = gson.fromJson(data,
					BgcDetailRawSubmission.class);

			bgc.setRaw(data);
			bgc.setSubmitted(Calendar.getInstance().getTime());
			bgc.setModified(Calendar.getInstance().getTime());
			bgc.setVersion(ver);

			if (bgcRaw.getTest() == null) {
				return Response
						.status(Status.BAD_REQUEST)
						.entity(toJSON(new Result<String>(true,
								"bgc_id(test) parameter not provided.",
								"bad-request"))).build();

			} else {

				bgc.setBgcId(bgcRaw.getTest());

				if (bgcRaw.getTablename().equals("gene_info") ) {
					// goes to gene_submissions
					service.storeGeneInfo(bgc);

				} else if (bgcRaw.getTablename().equals("nrps_info") ) {
					// goes to nrps_submissions
					service.storeNrpsInfo(bgc);

				} else {
					return Response
							.status(Status.BAD_REQUEST)
							.entity(toJSON(new Result<String>(true,
									"tablename parameter not matching.",
									"bad-request"))).build();
				}
			}
			return Response.ok().build();
		} catch (DBGeneralFailureException e) {
			log.error("Could not store Submission:" + e);
			return Response.serverError()
					.entity(toJSON("Could not store Submission")).build();
		} catch (Exception e) {
			return Response.serverError().entity(toJSON("Server error: " + e))
					.build();
		}
	}

}
