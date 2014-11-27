package net.megx.mibig.rest;

import java.net.URI;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.mibig.MibigService;
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
	public Response serviceInfo() {
		log.debug("getting a bgc submission service info request");

		return Response.ok().header("Access-Control-Allow-Origin", "*")
				.entity("bgc registration up and running").build();
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
						.header("Access-Control-Allow-Origin", "*")
						.entity(toJSON(new Result<String>(true,
								"json not provided", "bad-request"))).build();
			}
			if (version == null) {
				return Response
						.status(Status.BAD_REQUEST)
						.header("Access-Control-Allow-Origin", "*")
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
			URI location = UriBuilder.fromUri(
					"http://www.marnixmedema.nl/mibig/thankyou.html").build();

			log.debug("uri absolute=" + u.toASCIIString());
			log.debug("context path=" + request.getContextPath());
			log.debug("location=" + location.toString());
			return Response.seeOther(location)
					.header("Access-Control-Allow-Origin", "*").build();
		} catch (DBGeneralFailureException e) {
			log.error("Could not store Submission:" + e);
			return Response.serverError()
					.header("Access-Control-Allow-Origin", "*")
					.entity(toJSON("Could not store Submission")).build();
		} catch (NumberFormatException e) {
			// TODO: handle exception
			return Response
					.status(Status.BAD_REQUEST)
					.header("Access-Control-Allow-Origin", "*")
					.entity(toJSON("Version parameter not a valid number: "
							+ version)).build();
		}
	}

	@Path("bgc-detail-registration")
	@OPTIONS
	@Produces(MediaType.APPLICATION_JSON)
	public Response allowCORS() {
		return Response
				.ok()
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods",
						"GET,POST,PUT,HEAD,DELETE,OPTIONS")
				.header("ccess-Control-Allow-Headers",
						"content-Type,x-requested-with").build();
	}

	@Path("bgc-detail-registration")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response storeBgcDetailSubmission(@FormParam("data") String data,
			@FormParam("target") String target,
			@DefaultValue("1") @FormParam("version") int version,
			@DefaultValue("BGC00000") @FormParam("bgc_id") String bgcId) {

		/*
		 * res.header('Access-Control-Allow-Origin', req.headers.origin || "*");
		 * res.header('Access-Control-Allow-Methods',
		 * 'GET,POST,PUT,HEAD,DELETE,OPTIONS');
		 * res.header('Access-Control-Allow-Headers',
		 * 'content-Type,x-requested-with'); //
		 */

		ResponseBuilder r = Response
				.ok()
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods",
						"GET,POST,PUT,HEAD,DELETE,OPTIONS")
				.header("Access-Control-Allow-Headers",
						"content-Type,x-requested-with");

		BgcDetailSubmission bgc = new BgcDetailSubmission();

		log.debug("getting a bgc submission POST version=" + version
				+ "; bgc_id= " + bgcId + "; target=" + target + "; data=" + data);

		if (data == null) {
			log.error("no json in @formparam data");
			return r.status(Status.BAD_REQUEST)
					.entity(toJSON(new Result<String>(true,
							"json not provided", "bad-request"))).build();
		}

		if (target == null) {
			log.error("no target defined in @formparam target");
			return r.status(Status.BAD_REQUEST)
					.entity(toJSON(new Result<String>(true,
							"not target param defined", "bad-request")))
					.build();
		}

		bgc.setBgcId(bgcId);
		bgc.setRaw(data);
		// bgc.setSubmitted(Calendar.getInstance().getTime());
		// bgc.setModified(Calendar.getInstance().getTime());
		bgc.setVersion(version);
		
		try {
			if ("gene_info".equals(target)) {
				// goes to gene_submissions
				service.storeGeneInfo(bgc);

			} else if ("nrps_info".equals(target)) {
				// goes to nrps_submissions
				service.storeNrpsInfo(bgc);

			} else {
				log.error("target parameter=" + target
						+ " must be 'gene_info' or 'nrps_info'");
				return r.status(Status.BAD_REQUEST)
						.entity(toJSON(new Result<String>(
								true,
								"target parameter not matching. Must be one of 'gene_info' or 'nrps_info' ",
								"bad-request"))).build();
			}

			return r.status(Status.NO_CONTENT).build();
		} catch (DBGeneralFailureException e) {
			log.error("Could not store Submission:" + bgc + "\n DB error:" + e);
			return r.status(Status.INTERNAL_SERVER_ERROR)
					.entity(toJSON("Could not store Submission")).build();
		}
	}
}
