package net.megx.security.filter.http.impl;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.servlet.http.HttpServletRequest;

import org.chon.cms.core.model.renderers.VTplNodeRenderer;
import org.chon.cms.model.ContentModel;
import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.api.ServerInfo;

import net.megx.security.filter.http.TemplatePageNode;

public class RegisterPageNode extends TemplatePageNode{

	public RegisterPageNode(ContentModel model, Node node, IContentNode typeDesc) {
		super(model, node, typeDesc);
	}

	@Override
	public void process(Request req, Response resp, ServerInfo serverInfo)
			throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		HttpServletRequest request = req.getServletRequset();
		
		
		params.put("email", req.getServletRequset().getParameter("email"));
		
		String challenge = request.getParameter("challenge");
		
		
		
		VTplNodeRenderer.render("base.html", "security-filter/confirm-registration.html", this, req, resp, serverInfo, params);
	}

}
