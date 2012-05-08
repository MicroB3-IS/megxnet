/*
 * Copyright 2011 Max Planck Institute for Marine Microbiology 
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.chon.megx.net.model;

import javax.jcr.Node;

import org.chon.cms.model.ContentModel;
import org.chon.cms.model.content.IContentNode;
import org.chon.cms.model.content.base.BaseWWWContentNode;
import org.chon.megx.net.renderers.ModuleNodeRenderer;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.api.ServerInfo;

public abstract class ModuleContentNode extends BaseWWWContentNode {

	public ModuleContentNode(ContentModel model, Node node,
			IContentNode typeDesc) {
		super(model, node, typeDesc);
	}
	
	/**
	 * Override this method and render to response
	 * @see ModuleNodeRenderer
	 * 
	 * @param req
	 * @param resp
	 * @param serverInfo
	 * @throws Exception
	 */
	public abstract void process(Request req, Response resp, ServerInfo serverInfo) throws Exception;
}
