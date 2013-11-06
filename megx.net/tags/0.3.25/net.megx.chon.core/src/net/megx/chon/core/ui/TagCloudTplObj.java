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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;

import com.iw.megx.ws.dto.mpiws.HabLiteDist;
import com.iw.megx.ws.service.mpiws.MpiWsService;

public class TagCloudTplObj {

	private Response resp;
	private /*IGenomes*/ MpiWsService genomesService;

	private String version = "2_1";
	
	public TagCloudTplObj(/*IGenomes*/ MpiWsService genomesService,  Request req, Response resp, IContentNode node) {
		this.genomesService = genomesService;
		this.resp = resp;
	}
	
	public String render() {
		Map<String, Object> params = new HashMap<String, Object>();
		List<TagCloudItem> tagCloudList = new ArrayList<TagCloudItem>();
		try {
			addDummyItems(tagCloudList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("Error while retrieving tags");
			e.printStackTrace();
		}
		params.put("tagCloudList", tagCloudList);
		return resp.formatTemplate("megx.net/ext/tagCloud/def.html", params); 
	}

	private void addDummyItems(List<TagCloudItem> tagCloudList) throws Exception {
		//List<EnvOLite> envs = genomesService.getGenomesEnvs();
		List<HabLiteDist> envs = genomesService.getHabLiteDist("", 0, 20, version);
		//long total = genomesService.getGenomesSize();
		long total = envs.size();
		
		for(/*EnvOLite*/ HabLiteDist env : envs) {
			long v = env.getQuantity();
			int percent = (int)(Double.valueOf(v)/Double.valueOf(total)*100);
			String p = String.valueOf(80 + 200*percent/100) + "%";
			String title = "Select genomes by this habitat";
			TagCloudItem tci = new TagCloudItem("?habitat="+env.getTag(), p, title , env.getTag(), String.valueOf(v));
			tagCloudList.add(tci);
		}
	}
}
