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

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;

import net.megx.chon.core.model.ModuleContentNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.model.renderers.VTplNodeRenderer;
import org.chon.cms.model.ContentModel;
import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.api.ServerInfo;

public class WOD_Module extends ModuleContentNode {
	private static final Log log = LogFactory.getLog(WOD_Module.class);
	
	public WOD_Module(ContentModel model, Node node, IContentNode typeDesc) {
		super(model, node, typeDesc);
	}

	
	@Override
	public void process(Request req, Response resp, ServerInfo serverInfo) throws Exception {
		String lat = getParam(req, "latitude", "lat");
		String lon = getParam(req, "longitude", "lon");
		String depth = getParam(req, "depth", "d");
		String buffer = getParam(req, "buffer", "buf");
		
		//TODO: see php code 
		String submit = req.get("submit");
		if(submit != null) {
			String message = validateParams(lat, lon, depth, buffer);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("this", node);
			params.put("message", message);
			
			params.put("lat", lat);
			params.put("lon", lon);
			params.put("depth", depth);
			params.put("buffer", buffer);
			if(message == null && this.getNode().getName().endsWith(".csv")) {
				String p = String.format("lat=%s&lon=%s&depth=%s&buffer=%s", lat, lon, depth, buffer);
				resp.setRedirect("../../ws/wod05/megx_wod.csv?"+p);
			} else {
				VTplNodeRenderer.render("base.html", "pages/wod.html", this, req, resp, serverInfo, params);
			}
			
			/*
			
			if("csv".equals(submit)) {
				resp.getServletResponse().setContentType("text/csv");
				String fileName = String.format("woa05_%s_%s_%s.csv", lat, lon, depth);
				resp.getServletResponse().addHeader("Content-Disposition", "attachment;filename="+fileName);
				WoaService svc = new WoaService(false, lat, lon, depth, parameter, season);
				resp.getServletResponse().getWriter().print(svc.getCSV());
			} else if("Calculate".equals(submit)){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("this", node);
				if(message==null) {
					try {
						WoaService svc = new WoaService(true, lat, lon, depth, parameter, season);
						params.put("downLink", svc.getParams() + "&submit=csv");
						params.put("list", svc.getList());
					} catch (Exception e) {
						log.error(e);
						//TODO: this is just workaround...
						message = "The requested location is either on land or too close to the shore. Therefore, no data is available from WOA05 and no interpolation is possible.";
					}
				}
				params.put("message", message);
				VTplNodeRenderer.render("base.html", "pages/woa.html", this, req, resp, serverInfo, params);
			} else {
				log.error("Invalid submit value: " + submit);
			}
			*/
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("this", node);
			params.put("message", " ");
			VTplNodeRenderer.render("base.html", "pages/wod.html", this, req, resp, serverInfo, params);
		}
	}
	
	
	private String validateParams(String lat, String lon, String depth, String buffer) {
		String message = "";
		if (lon == null) {
			message += "Longitude must be given. URL Query parameter name is \""
					+ lon + "\". <br/>";
		}
		if (lat == null) {
			message += "Latitude must be given. URL Query parameter name is \""
					+ lat + "\". <br/>";
		}
		if (depth == null) {
			message += "Depth must be given. URL Query parameter name is \""
					+ depth + "\". <br/>";
		}
		if (buffer == null) {
			message += "Buffer paramter must be given. URL Query parameter name is \""
					+ buffer + "\". <br/>";
		}
		
		try {
			if (Double.valueOf(lat) > 90 || Double.valueOf(lat) < -90) {
				message += "Latitude out of range. Hint: Range should be [-90,90] <br/>";
			}
		} catch (Exception e) {
			message += "Latitude not valid number. Hint: Range should be [-90,90] <br/>";
		}

		try {
			if (Double.valueOf(lon) > 180 || Double.valueOf(lon) < -180) {
				message += "Longitude out of range. Hint: Range should be [-180,180] <br/>";
			}
		} catch (Exception e) {
			message += "Longitude not valid number. Hint: Range should be [-180,180] <br/>";
		}

		try {
			if (Double.valueOf(depth) > 5500 || Double.valueOf(depth) < 0) {
				message += "Depth out of range. Hint: Range should be [0,5500] <br/>";
			}
		} catch (Exception e) {
			message += "Depth not valid number. Hint: Range should be [0,5500] <br/>";
		}
		try {
			if (Double.valueOf(buffer) > 5500 || Double.valueOf(buffer) < 0) {
				message += "Buffer out of range. Hint: Range should be [0,5500] <br/>";
			}
		} catch (Exception e) {
			message += "Buffer not valid number. Hint: Range should be [0,5500] <br/>";
		}
		
		if(message.length()>0) {
			return message;
		}
		return null;
	}

	private String getParam(Request req, String p1, String p2) {
		String rv = null;
		rv = req.get(p1);
		if(rv == null && p2 != null) {
			rv = req.get(p2);
		}
		return rv;
	}

}