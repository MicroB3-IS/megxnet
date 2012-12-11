package net.megx.ws.blast.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.megx.ui.table.json.TableDataResponse;
import net.megx.ws.blast.BlastService;
import net.megx.ws.blast.uidomain.BlastMatchingSequence;
import net.megx.ws.core.BaseRestService;

@Path("blast")
public class BlastRestService extends BaseRestService {
	
	private BlastService blastService;

	public BlastRestService(BlastService blastService) {
		this.blastService = blastService;
	}

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
	public String getJobStatus(@QueryParam("jobId") String jobId) {
		Map<String, String> json = new HashMap<String, String>();
		json.put("jobId", "" + jobId);
		json.put("status", blastService.getBlastJobStatus(jobId));
		return toJSON(json);
	}
	
	@GET
	@Path("getMatchingSequencesTable")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMatchingSequencesTable(@QueryParam("jobId") String jobId) {
		List<BlastMatchingSequence> ls = blastService.getMatchingSequence(jobId);
		TableDataResponse<BlastMatchingSequence> resp = new TableDataResponse<BlastMatchingSequence>();
		resp.setData(ls);
		return toJSON(resp);
	}
	
}
