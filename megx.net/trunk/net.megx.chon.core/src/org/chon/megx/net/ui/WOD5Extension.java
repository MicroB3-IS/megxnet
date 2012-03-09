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
package org.chon.megx.net.ui;

import org.chon.cms.model.content.IContentNode;
import org.chon.megx.net.services.ui.pub.IGenomes;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.osgi.framework.BundleContext;

import com.iw.megx.ws.service.mpiws.MpiWsService;

public class WOD5Extension extends AbstractMegxUIExtension {

	public WOD5Extension(BundleContext bundleContext) {
		super(bundleContext);
	}

	@Override
	public Object getTplObject(Request req, Response resp, IContentNode node) {
		//IGenomes service = getGenomesService();
		MpiWsService service = getMpiDbService();
		if (service == null) {	
			return null;
		}
		return new WOD5ExtTplObj(service, req, resp, node);
	}


}
