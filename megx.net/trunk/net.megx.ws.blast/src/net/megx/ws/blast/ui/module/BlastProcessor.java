package net.megx.ws.blast.ui.module;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.megx.chon.core.model.IModule;
import net.megx.chon.core.model.ModuleContentNode;
import net.megx.ws.blast.BlastService;
import net.megx.ws.blast.utils.BlastUtils;

import org.chon.cms.core.model.renderers.VTplNodeRenderer;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.api.ServerInfo;

public class BlastProcessor implements IModule {	
	
	private BlastService blastService;

	public BlastProcessor(BlastService blastService) {
		this.blastService = blastService;
	}
	
	private String sceduleRunBlastJob(String username, BlastInputData blastInputData) throws IOException {
		InputStream is = null;
		try {
			if (blastInputData.getFile() != null) {
				is = new FileInputStream(blastInputData.getFile());
			} else {
				is = new ByteArrayInputStream(blastInputData.getSeq().getBytes());
			}
			String blastDb = blastInputData.getBlastDb();
			String evalue_cutoff = blastInputData.getEvalueCutoff();
			String jobId = blastService.runBlastJob(username, is, blastDb, evalue_cutoff);
			return jobId;
		} finally {
			is.close();
		}
	}

	/**
	 * Initial submit
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	private void processSubmit(Request req, Response resp) throws IOException {
		BlastInputData blastData = new BlastInputData(req);
		String username = BlastUtils.getUsernameFromRequest(req.getServletRequset());
		String jobId = sceduleRunBlastJob(username, blastData);
		resp.setRedirect("job?id="+jobId);
	}

	/**
	 * Render blast results
	 * @param req
	 * @param resp
	 * @param serverInfo
	 */
	private void processBlastJob(ModuleContentNode node, Request req, Response resp, ServerInfo serverInfo) {
		String jobId = req.get("id");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jobId", jobId);
		VTplNodeRenderer.render("base.html", "pages/blast/blast-job-result.html", node, req, resp, serverInfo, params);
	}

	@Override
	public void process(ModuleContentNode node, Request req, Response resp,
			ServerInfo serverInfo) throws Exception {
		if("submit".equals(node.getName())) {
			processSubmit(req, resp);
		} else if("job".equals(node.getName())){
			processBlastJob(node, req, resp, serverInfo);
		}
	}

}
