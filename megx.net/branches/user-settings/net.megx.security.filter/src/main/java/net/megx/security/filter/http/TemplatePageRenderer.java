package net.megx.security.filter.http;

import net.megx.security.logging.ErrorLog;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.model.content.IContentNode;
import org.chon.cms.model.content.INodeRenderer;
import org.chon.web.api.Application;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.api.ServerInfo;

public class TemplatePageRenderer implements INodeRenderer{
	
	private Log log = LogFactory.getLog(getClass());
	
	private RequestUtils requestUtils;
	private ErrorLog errorLog;
	
	
	
	public TemplatePageRenderer(RequestUtils requestUtils, ErrorLog errorLog) {
		super();
		this.requestUtils = requestUtils;
		this.errorLog = errorLog;
	}
	
		
	public RequestUtils getRequestUtils() {
		return requestUtils;
	}
	
	public ErrorLog getErrorLog() {
		return errorLog;
	}


	@Override
	public void render(IContentNode contentNode, Request request, Response response,
			Application application, ServerInfo serverInfo) {
		log.debug("Ok, got to render.");
		if(contentNode instanceof TemplatePageNode){
			log.debug("Trying to render...");
			try {
				((TemplatePageNode)contentNode).setPageRenderer(this);
				((TemplatePageNode)contentNode).process(request, response, serverInfo);
				log.debug("Rendered, no problem.");
			} catch (Exception e) {
				log.error("Rendering of node failed",e);
			}
		}
	}

}
