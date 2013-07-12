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


package net.megx.model.ws.mixs;

import javax.xml.bind.annotation.XmlType;


@XmlType(name="EnvPackageType", propOrder={
		"label",
		"description",
		"uTime",
		"cTime",
		"gcdmlName"
})
public class EnvPackage {

	private String label;
	private String description;
	private String uTime;
	private String cTime;
	private String gcdmlName;

	public EnvPackage() {
	}

	public EnvPackage(String label, String description, String uTime,
			String cTime, String gcdmlName) {
		super();
		this.label = label;
		this.description = description;
		this.uTime = uTime;
		this.cTime = cTime;
		this.gcdmlName = gcdmlName;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getuTime() {
		return uTime;
	}

	public void setuTime(String uTime) {
		this.uTime = uTime;
	}

	public String getcTime() {
		return cTime;
	}

	public void setcTime(String cTime) {
		this.cTime = cTime;
	}

	public String getGcdmlName() {
		return gcdmlName;
	}

	public void setGcdmlName(String gcdmlName) {
		this.gcdmlName = gcdmlName;
	}

	@Override
	public String toString() {
		return "EnvironmentalPackage [Name=" + label + ", description="
				+ description + ", uTime=" + uTime + ", cTime=" + cTime
				+ ", gcdmlName=" + gcdmlName + "]";
	}
}
