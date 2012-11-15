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
package org.chon.megx.net.services.ui.pub;


public class Genome {
	private String organismName;
	private String location;
	private String depth;
	private String dateSampled;
	private String envOLite;
	private String texId;
	private String genomePID;
	private String datasetName;
	
	public String getDatasetName() {
		return datasetName;
	}
	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}
	public String getOrganismName() {
		return organismName;
	}
	public String getEnvOLite() {
		return envOLite;
	}
	public void setEnvOLite(String envOLite) {
		this.envOLite = envOLite;
	}
	public void setOrganismName(String organismName) {
		this.organismName = organismName;
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
	public String getTexId() {
		return texId;
	}
	public void setTexId(String texId) {
		this.texId = texId;
	}
	public String getGenomePID() {
		return genomePID;
	}
	public void setGenomePID(String genomePID) {
		this.genomePID = genomePID;
	}
}
