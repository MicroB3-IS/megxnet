package net.megx.chon.core.model;

import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.api.ServerInfo;

public interface IModule {
	public void process(ModuleContentNode node, Request req, Response resp,
			ServerInfo serverInfo) throws Exception;
}
