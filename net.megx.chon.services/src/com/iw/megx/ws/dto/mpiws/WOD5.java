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

public class WOD5 {
	/*
	WOD05
	------------------------
	-Year
	-Month	
	-Temperature	
	-Salinity	
	-Oxygen	
	-Phosphate	
	-Silicate	
	-Nitrate	
	-Nitrite	
	-pH	
	-chlorophyll	
	-alkalinity	
	-plankton_biomass
	 */
	
	public String year;
	public String month;
	public String temperature;
	public String salinity;
	public String oxygen;
	public String phosphate;
	public String silicate;
	public String nitrate;
	public String nitrite;
	public String ph;
	public String chlorophyll;
	public String alkalinity;
	public String plankton_biomass;
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getSalinity() {
		return salinity;
	}
	public void setSalinity(String salinity) {
		this.salinity = salinity;
	}
	public String getOxygen() {
		return oxygen;
	}
	public void setOxygen(String oxygen) {
		this.oxygen = oxygen;
	}
	public String getPhosphate() {
		return phosphate;
	}
	public void setPhosphate(String phosphate) {
		this.phosphate = phosphate;
	}
	public String getSilicate() {
		return silicate;
	}
	public void setSilicate(String silicate) {
		this.silicate = silicate;
	}
	public String getNitrate() {
		return nitrate;
	}
	public void setNitrate(String nitrate) {
		this.nitrate = nitrate;
	}
	public String getNitrite() {
		return nitrite;
	}
	public void setNitrite(String nitrite) {
		this.nitrite = nitrite;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getChlorophyll() {
		return chlorophyll;
	}
	public void setChlorophyll(String chlorophyll) {
		this.chlorophyll = chlorophyll;
	}
	public String getAlkalinity() {
		return alkalinity;
	}
	public void setAlkalinity(String alkalinity) {
		this.alkalinity = alkalinity;
	}
	public String getPlankton_biomass() {
		return plankton_biomass;
	}
	public void setPlankton_biomass(String plankton_biomass) {
		this.plankton_biomass = plankton_biomass;
	}

	
	
}
