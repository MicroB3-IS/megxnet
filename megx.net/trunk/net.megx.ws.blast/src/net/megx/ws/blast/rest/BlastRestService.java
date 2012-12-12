package net.megx.ws.blast.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.megx.ui.table.json.TableDataResponse;
import net.megx.ws.blast.BlastService;
import net.megx.ws.blast.uidomain.BlastJob;
import net.megx.ws.blast.uidomain.BlastMatchingSequence;
import net.megx.ws.blast.uidomain.SequenceAlignment;
import net.megx.ws.blast.utils.BlastUtils;
import net.megx.ws.core.BaseRestService;

@Path("blast")
public class BlastRestService extends BaseRestService {
	
	private BlastService blastService;

	public BlastRestService(BlastService blastService) {
		this.blastService = blastService;
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
	
	@GET
	@Path("getSequenceAlignment")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSequenceAlignment(
			@QueryParam("jobId") String jobId,
			@QueryParam("seqIndex") int seqIndex) {
		SequenceAlignment sa = blastService.getSequenceAlignment(jobId, seqIndex);
		return toJSON(sa);
	}
	
	@GET
	@Path("getBlastJobsTable")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBlastJobsTable(@Context HttpServletRequest request) {
		String username = BlastUtils.getUsernameFromRequest(request);
		List<BlastJob> ls = blastService.getBlastJobs(username);
		TableDataResponse<BlastJob> resp = new TableDataResponse<BlastJob>();
		resp.setData(ls);
		return toJSON(resp);
	}
}
