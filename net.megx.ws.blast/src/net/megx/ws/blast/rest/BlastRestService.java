package net.megx.ws.blast.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.megx.ws.core.BaseRestService;

@Path("blast")
public class BlastRestService extends BaseRestService {
	@GET
	@Path("ping")
	@Produces(MediaType.APPLICATION_JSON)
	public String ping() {
		Map<String, String> json = new HashMap<String, String>();
		json.put("ping", "pong");
		return toJSON(json);
	}

	@GET
	@Path("getJobStatus")
	@Produces(MediaType.APPLICATION_JSON)
	public String getJobStatus(@QueryParam("jobId") long jobId) {
		Map<String, String> json = new HashMap<String, String>();
		json.put("jobId", "" + jobId);
		if(Math.random()*100<20) {
			json.put("status", "done");
		} else {
			json.put("status", "running");
		}
		
		return toJSON(json);
	}
}
