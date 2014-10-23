package net.megx.mibig.rest;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.megx.megdb.exceptions.DBGeneralFailureException;
import net.megx.megdb.mibig.MibigService;
import net.megx.model.mibig.MibigSubmission;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.Result;

@Path("v1/mibig/v1.0.0")
public class MibigAPI extends BaseRestService {

	private MibigService service;

	public MibigAPI(MibigService service) {
		this.service = service;
	}

	@Path("bgc-registration")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String storeMibigSubmission(
			@FormParam("json") String mibigJson,
			@Context HttpServletRequest request) {
		try {
			if (mibigJson == null) {
				return toJSON(new Result<String>(true,
						"json not provided", "bad-request"));
			}
			MibigSubmission mibig = new MibigSubmission();

			mibig.setRaw(mibigJson);
			mibig.setSubmitted(Calendar.getInstance().getTime());
			mibig.setModified(Calendar.getInstance().getTime());
			mibig.setVersion(1);

			service.storeMibigSubmission(mibig);

			return toJSON("temp-success");
		} catch (DBGeneralFailureException e) {
			log.error("Could not store Submission:" + e);
			return toJSON(handleException(e));
		} catch (Exception e) {
			log.error("Server error:" + e);
			return toJSON(handleException(e));
		}
	}

}
