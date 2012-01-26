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
package org.chon.megx.net.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WoaService {
	private static final Log log = LogFactory.getLog(WoaService.class);
	
//http://www.megx.net/gms/tools/woa.html?latitude=33&longitude=23&depth=444&parameter=all&season=0&submit=Calculate
	
	private static String csvUrl = "http://www.megx.net/gms/tools/woa.csv";
	
	public class Woa {
		private String parameter;
		private String value;
		private String unit;
		public Woa(String parameter, String value, String unit) {
			this.parameter = parameter;
			this.value = value;
			this.unit = unit;
		}
		
		public String getParameter() {
			return parameter;
		}
		public String getValue() {
			return value;
		}
		public String getUnit() {
			return unit;
		}
	}


	private List<Woa> list;
	
	private String csv;

	private String params;
	
	public WoaService(boolean parseCSV, String latitude, String longitude, String depth,
			String parameter, String season) {	
		init(parseCSV, latitude, longitude, depth, parameter, season);
	}


	private void init(boolean parseCSV, String latitude, String longitude, String depth, String parameter, String season) {
		try {
			StringBuffer params = new StringBuffer();
			params.append("?");
			params.append("latitude="+latitude);
			params.append("&");
			params.append("longitude="+longitude);
			params.append("&");
			params.append("depth="+depth);
			params.append("&");
			params.append("parameter="+parameter);
			params.append("&");
			params.append("season="+season);
			this.params = params.toString();
			URL url = new URL(csvUrl + this.params);
			InputStream is = url.openStream();
			StringWriter sw = new StringWriter();
			IOUtils.copy(is, sw);
			String data = sw.toString();
			log.debug(data);
			if(parseCSV) {
				this.list = parseCSV(data);
			} else {
				this.csv = data;
			}
		} catch (MalformedURLException e) {
			log.error("Invalid url", e);
		} catch (IOException e) {
			log.error("Error while reading " + csvUrl, e);
		}
	}


	private List<Woa> parseCSV(String string) {
		List<Woa> woaList = new ArrayList<Woa>();
		
		String[] lines = string.split("\n");
		for(int i=0; i<lines.length; i++) {
			String line = lines[i].trim();
			if(line.length() > 0) {
				String[] values = line.split(",");
				woaList.add(new Woa(values[0], values[1], values[2]));
			}
		}
		return woaList;
	}


	public List<Woa> getList() {
		return list;
	}
	
	public String getCSV() {
		return csv;
	}


	public String getParams() {
		return params;
	}
}