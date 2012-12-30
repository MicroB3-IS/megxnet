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
package net.megx.chon.core.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;

import com.iw.megx.ws.dto.mpiws.Sample;
import com.iw.megx.ws.service.mpiws.MpiWsService;

public class SamplesExtTplObj {

	private /*IGenomes*/ MpiWsService service;
	private Response resp;
	private Request req;

	private String version = "2_1"; 
	
	public SamplesExtTplObj(/*IGenomes*/ MpiWsService service, Request req, Response resp,
			IContentNode node) {
		this.service = service;
		this.resp = resp;
		this.req = req;
	}

	public String render() {
		Map<String, Object> params = new HashMap<String, Object>();
		int itemsOnPage = 30;
		try {
			String  lon = req.get("lon");
			String  lat = req.get("lat");
			
			List<Sample> s = service.getSamples(lat, lon, 0, itemsOnPage, version);
			
			params.put("samples", s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp.formatTemplate("megx.net/ext/table/samples.html", params);
	}
}

