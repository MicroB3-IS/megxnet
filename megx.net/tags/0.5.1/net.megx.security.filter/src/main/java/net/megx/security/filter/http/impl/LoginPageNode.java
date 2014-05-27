package net.megx.security.filter.http.impl;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;

import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.SecurityException;
import net.megx.security.auth.web.WebContextUtils;
import net.megx.security.filter.http.TemplatePageNode;

import org.chon.cms.core.model.renderers.VTplNodeRenderer;
import org.chon.cms.model.ContentModel;
import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.api.ServerInfo;

public class LoginPageNode extends TemplatePageNode{

	public LoginPageNode(ContentModel model, Node node, IContentNode typeDesc) {
		super(model, node, typeDesc);
	}

	@Override
	public void process(Request req, Response resp, ServerInfo serverInfo)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		SecurityContext context = WebContextUtils.getSecurityContext(req.getServletRequset());
		params.put("error", false);
		if(context != null){
			Exception exception = context.getLastException();
			if(exception != null){
				context.storeLastException(null);// clean the exception
				params.put("error", true);
				params.put("securityError", exception instanceof SecurityException);
				params.put("exception", exception);
			}
		}
		VTplNodeRenderer.render("base.html", "security-context/login.html", this, req, resp, serverInfo, params);
	}

}
