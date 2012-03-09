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
package org.chon.megx.net.renderers;

import org.chon.cms.model.content.IContentNode;
import org.chon.cms.model.content.INodeRenderer;
import org.chon.megx.net.model.ModuleContentNode;
import org.chon.web.api.Application;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.api.ServerInfo;

/**
 * Render node that extends class {@link ModuleContentNode}
 * 
 * @author Jovica.Veljanovski
 *
 */
public class ModuleNodeRenderer implements INodeRenderer {

	@Override
	public void render(IContentNode contentNode, Request req, Response resp,
			Application app, ServerInfo serverInfo) {
		if(contentNode instanceof ModuleContentNode) {
			ModuleContentNode mcn = (ModuleContentNode) contentNode;
			try {
				mcn.process(req, resp, serverInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("Invalid node type");
		}
	}
}