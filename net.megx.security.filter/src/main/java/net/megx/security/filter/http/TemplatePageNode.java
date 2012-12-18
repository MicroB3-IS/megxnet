package net.megx.security.filter.http;

import javax.jcr.Node;

import org.chon.cms.model.ContentModel;
import org.chon.cms.model.content.IContentNode;
import org.chon.cms.model.content.base.BaseWWWContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.api.ServerInfo;

public abstract class TemplatePageNode extends BaseWWWContentNode{

	protected TemplatePageRenderer renderer;
	
	public TemplatePageNode(ContentModel model, Node node, IContentNode typeDesc) {
		super(model, node, typeDesc);
	}

	public abstract void process(Request req, Response resp, ServerInfo serverInfo) throws Exception;
	
	public void setPageRenderer(TemplatePageRenderer renderer){
		this.renderer = renderer;
	}
}
