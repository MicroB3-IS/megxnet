package net.megx.security.filter.http.impl;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.megx.security.filter.http.TemplatePageNode;

import org.chon.cms.core.model.renderers.VTplNodeRenderer;
import org.chon.cms.model.ContentModel;
import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.api.ServerInfo;

public class ExceptionPageNode extends TemplatePageNode{

	public ExceptionPageNode(ContentModel model, Node node,
			IContentNode typeDesc) {
		super(model, node, typeDesc);
	}

	@Override
	public void process(Request req, Response resp, ServerInfo serverInfo)
			throws Exception {
		HttpServletRequest request = 
				req.getServletRequset();
		Throwable exception = (Throwable)request.getAttribute("javax.servlet.error.exception");
		
		Class<?> type = (Class<?>)request.getAttribute("javax.servlet.error.exception_type");
		String code = request.getAttribute("javax.servlet.error.status_code") + "";
		String message = request.getAttribute("javax.servlet.error.message") + "";
		
		if("".equals(message) && "".equals(code) && type ==null && exception == null){
			resp.getServletResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		
		Map<String, Object> exceptionContext = new HashMap<String, Object>();
		
		exceptionContext.put("message", message);
		exceptionContext.put("exception", exception);
		exceptionContext.put("code", code);
		exceptionContext.put("type", type);
		exceptionContext.put("servlet_name", request.getAttribute("javax.servlet.error.servlet_name"));
		exceptionContext.put("exception", exception);
		exceptionContext.put("request_uri", request.getAttribute("javax.servlet.error.request_uri"));
		
		String template = get("template");
		if(template == null){
			resp.getServletResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		String baseTemplate = get("baseTemplate");
		if(baseTemplate == null){
			baseTemplate = "base.html";
		}
		VTplNodeRenderer.render(baseTemplate, template, this, req, resp, serverInfo, exceptionContext);
	}

}
