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
package net.megx.chon.core.model.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;

import net.megx.chon.core.model.ModuleContentNode;

import org.apache.commons.io.IOUtils;
import org.chon.cms.model.ContentModel;
import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.api.ServerInfo;

public class WMS_Module extends ModuleContentNode {

	public WMS_Module(ContentModel model, Node node, IContentNode typeDesc) {
		super(model, node, typeDesc);
	}

	private String megx_net = "http://www.megx.net";
	private String megx_wms = megx_net + "/wms/gms";
	
	private static Map<URL, File> cacheIndex = new HashMap<URL, File>();
	private static int cacheCnt = 0;
	
	@Override
	public void process(Request req, Response resp, ServerInfo serverInfo) throws Exception {
		String q = req.getServletRequset().getQueryString();
		URL u = new URL(megx_wms + "?" + q);
		printInfo(u, req);
		InputStream is = fromCache(u);
		if(is == null) {
			is = u.openStream();
			addToCache(cacheCnt++, u, is);
			is = fromCache(u);
		}
		
		resp.getServletResponse().setContentType("image/png");
		
		if("LEGEND".equals(req.get("MODE"))) {
			StringWriter sw = new StringWriter();
			IOUtils.copy(is, sw);
			String s = sw.toString();
			s = s.replaceAll("/gms/tmp", megx_net + "/gms/tmp");
			resp.getOut().print(s);
		} else {
			IOUtils.copy(is, resp.getServletResponse().getOutputStream());
		}
	}

	private void printInfo(URL u, Request req) {
		System.out.println("----------------------------------------------------");
		System.out.println("  WMS Service params: ");
		String [] arr = new String [] {"LAYERS", "FORMAT", "TRANSPARENT", "SERVICE", "VERSION", "REQUEST", "STYLES", "EXCEPTIONS", "SRS", "BBOX", "WIDTH", "HEIGHT"};
		for(String k : arr) {
			System.out.println("\t" + k + " = " + req.get(k));
		}
		System.out.println(" TODO: process above params, for now send to: " + u);
		System.out.println();
	}

	private void addToCache(int i, URL u, InputStream is) throws IOException {
		String cacheDir = System.getProperty("megx.net.cache.dir", System.getProperty("java.io.tmpdir"));
		File file = new File(cacheDir, i + ".png");
		FileOutputStream fos = new FileOutputStream(file);
		IOUtils.copy(is, fos);
		fos.flush();
		fos.close();
		cacheIndex.put(u, file);
	}

	private InputStream fromCache(URL u) throws FileNotFoundException {
		File file = cacheIndex.get(u);
		if(file != null) {
			return new FileInputStream(file);
		}
		return null;
	}

}
