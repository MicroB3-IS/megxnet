package net.megx.chon.core.model.impl;

import javax.jcr.Node;

import net.megx.chon.core.model.IModule;
import net.megx.chon.core.model.ModuleContentNode;
import net.megx.chon.core.model.ModuleUtils;

import org.chon.cms.model.ContentModel;
import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.api.ServerInfo;

public class GenericModuleNode extends ModuleContentNode {
	
	public GenericModuleNode(ContentModel model, Node node,
			IContentNode typeDesc) {
		super(model, node, typeDesc);
	}

	@Override
	public void process(Request req, Response resp, ServerInfo serverInfo)
			throws Exception {
		IModule m = ModuleUtils.findIModule(this);
		m.process(this, req, resp, serverInfo);
	}

}
