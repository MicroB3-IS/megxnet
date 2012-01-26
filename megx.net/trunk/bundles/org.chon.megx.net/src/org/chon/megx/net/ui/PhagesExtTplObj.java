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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;

import com.iw.megx.ws.dto.mpiws.GenomeOverview;
import com.iw.megx.ws.service.mpiws.MpiWsService;

public class PhagesExtTplObj {

	private /*IGenomes*/ MpiWsService service;
	private Response resp;
	private Request req;

	private String version = "2_1"; 
	
	public PhagesExtTplObj(/*IGenomes*/ MpiWsService service, Request req, Response resp,
			IContentNode node) {
		this.service = service;
		this.resp = resp;
		this.req = req;
	}

	public String render() {
		Map<String, Object> params = new HashMap<String, Object>();
		String env = req.get("habitat");
		int itemsOnPage = 30;
		Paging p = Paging.getPaging(req);
		
		try {
			//env
			p.count = service.getPhagesCount("", version);
			System.out.println("-------c:"+p.count);
			if(env == null) {
				//params.put("genomes", service.getGenomes(0, itemsOnPage));
				List<GenomeOverview> s = service.getPhages("", "", p.offset, p.limit, version);
				params.put("genomes", s);
			} else {
				//params.put("genomes", service.getGenomes(env, 0, itemsOnPage));
				// TODO: fix with env and make where arguments convention
				List<GenomeOverview> s = service.getPhages("", "", p.offset, p.limit, version);
				params.put("genomes", s);			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		params.put("paging", p);
		return resp.formatTemplate("megx.net/ext/table/table.html", params);
	}
}

