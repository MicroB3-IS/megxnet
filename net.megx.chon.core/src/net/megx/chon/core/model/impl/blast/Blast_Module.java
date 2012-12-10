package net.megx.chon.core.model.impl.blast;

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

	@Override
	public void process(Request req, Response resp, ServerInfo serverInfo)
			throws Exception {
		BlastInputData blastData = new BlastInputData(req);
		if(blastData.getFile() != null) {
			System.out.println("Blast file selected ... ");
			System.out.println("TODO: read file: " + blastData.getFile().getAbsolutePath());
		} else {
			System.out.println("seq: " + blastData.getSeq());
		}
		
		String blastDb = blastData.getBlastDb();
		System.out.println("blastDb: " + blastDb);
		String evalue_cutoff = blastData.getEvalueCutoff();
		System.out.println("evalue_cutoff: " + evalue_cutoff);
		
		Map<String, Object> params = new HashMap<String, Object>();
		//TODO: run blast job get id, pass to the template ...
		params.put("jobId", 101);
		params.put("blastData", blastData);
		VTplNodeRenderer.render("base.html", "pages/blast-running.html", this, req, resp, serverInfo, params);
	}

}
