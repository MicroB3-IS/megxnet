package net.megx.security.filter.http.impl;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.model.renderers.VTplNodeRenderer;
import org.chon.cms.model.ContentModel;
import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.api.ServerInfo;

import net.megx.security.filter.http.TemplatePageNode;

public class AuthorizePageNode extends TemplatePageNode{

	
	Log log = LogFactory.getLog(getClass());
	
	public AuthorizePageNode(ContentModel model, Node node,
			IContentNode typeDesc) {
		super(model, node, typeDesc);
	}

	@Override
	public void process(Request req, Response resp, ServerInfo serverInfo)
			throws Exception {
		
		log.debug("AuthorizePageNode > Will render the authorize page template...");
		Map<String, Object> params = new HashMap<String, Object>();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(req.getServletRequset().getAttribute("CALLBACK"));
		System.out.println(req.getServletRequset().getAttribute("TOKEN"));
		System.out.println(req.getServletRequset().getAttribute("CONS_DESC"));
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		VTplNodeRenderer.render("base.html", "security-filter/oauth/authorize.html", this, req, resp, serverInfo, params);
	}

}
