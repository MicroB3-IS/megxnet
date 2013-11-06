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
package com.iw.megx.ws.dto.mpiws;

public class Metagenome {

	/*
		Metagenome
		----------------
		-Dataset Name    ---- entity_name
		-Location        ---- site_name (lat_lon)
		-Depth			 ---- depth
		-DateSampled     ---- date_taken
		-EnvO-Lite       ---- hab_lite
	 */

	public String datasetName;
	public String location;
	public String depth;
	public String dateSampled;
	public String envolite;
	public String getDatasetName() {
		return datasetName;
	}
	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public String getDateSampled() {
		return dateSampled;
	}
	public void setDateSampled(String dateSampled) {
		this.dateSampled = dateSampled;
	}
	public String getEnvolite() {
		return envolite;
	}
	public void setEnvolite(String envolite) {
		this.envolite = envolite;
	}
	
	
	
}
