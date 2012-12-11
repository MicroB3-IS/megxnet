package net.megx.ws.blast.ui.module;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;

import net.megx.chon.core.model.ModuleContentNode;

import org.chon.cms.core.model.renderers.VTplNodeRenderer;
import org.chon.cms.model.ContentModel;
import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.api.ServerInfo;

public class Blast_Module extends ModuleContentNode {	
	public Blast_Module(ContentModel model, Node node, IContentNode typeDesc) {
		super(model, node, typeDesc);
	}
	
	
	//TODO: implement this method with real sceduler, and return jobId
	private String sceduleRunBlastJob(BlastInputData blastInputData) {
		System.out.println("TODO: sceduleRunBlastJob " + blastInputData);
		if(blastInputData.getFile() != null) {
			System.out.println("Blast file selected ... ");
			System.out.println("TODO: read file: " + blastInputData.getFile().getAbsolutePath());
		} else {
			System.out.println("seq: " + blastInputData.getSeq());
		}
		String blastDb = blastInputData.getBlastDb();
		System.out.println("blastDb: " + blastDb);
		String evalue_cutoff = blastInputData.getEvalueCutoff();
		System.out.println("evalue_cutoff: " + evalue_cutoff);
		return "1234567890";
	}

	/**
	 * Initial submit
	 * @param req
	 * @param resp
	 */
	private void processSubmit(Request req, Response resp) {
		BlastInputData blastData = new BlastInputData(req);
		String jobId = sceduleRunBlastJob(blastData);
		resp.setRedirect("job?id="+jobId);
	}
	
	/**
	 * Render blast results
	 * @param req
	 * @param resp
	 * @param serverInfo
	 */
	private void processBlastJob(Request req, Response resp, ServerInfo serverInfo) {
		String jobId = req.get("id");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jobId", jobId);
		params.put("jobStatus", "done");
		VTplNodeRenderer.render("base.html", "pages/blast/blast-job-result.html", this, req, resp, serverInfo, params);
	}
	
	@Override
	public void process(Request req, Response resp, ServerInfo serverInfo)
			throws Exception {
		if("submit".equals(this.getName())) {
			processSubmit(req, resp);
		} else if("job".equals(this.getName())){
			processBlastJob(req, resp, serverInfo);
		}
	}

}
