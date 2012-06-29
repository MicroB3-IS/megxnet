package net.megx.security.filter.http.impl;

import javax.jcr.Node;

import org.chon.cms.core.model.renderers.VTplNodeRenderer;
import org.chon.cms.model.ContentModel;
import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.api.ServerInfo;

import net.megx.security.filter.http.TemplatePageNode;

public class LoginPageNode extends TemplatePageNode{

	public LoginPageNode(ContentModel model, Node node, IContentNode typeDesc) {
		super(model, node, typeDesc);
	}

	@Override
	public void process(Request req, Response resp, ServerInfo serverInfo)
			throws Exception {
		VTplNodeRenderer.render("base.html", "security-context/login.html", this, req, resp, serverInfo, null);
	}

}
