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


package net.megx.model.ws.seqcd;


//@CSVDocument(preserveHeaderColumns=true, separator='\t')
public class ContextualDataItem {
	private String id;
	
	// GSC compliant contextual data items
//	@CSVColumn(name="lat_lon")
	private String latLon;
	private String depth;
//	@CSVColumn(name="collection_date")
	private String collectionDate;
	private String biome;
//	@CSVColumn(name="seq_meth")
	private String seqMeth;
	
	// megx specific contextual data items
//	@CSVColumn(name="assembly_status")
	private String assemblyStatus;
//	@CSVColumn(name="megx_long_hurst")
	private String megxLongHurst;
//	@CSVColumn(name="megx_country")
	private String megxCountry; //IHO region
//	@CSVColumn(name="megx_interpolated_values")
	private String megxInterpolatedValues;
	
	
	
	public ContextualDataItem() {	}
	
	public ContextualDataItem(String id, String latLon, String depth,
			String collectionDate, String biome, String seqMeth,
			String assemblyStatus, String megxLongHurst, String megxCountry,
			String megxInterpolatedValues) {
		super();
		this.id = id;
		this.latLon = latLon;
		this.depth = depth;
		this.collectionDate = collectionDate;
		this.biome = biome;
		this.seqMeth = seqMeth;
		this.assemblyStatus = assemblyStatus;
		this.megxLongHurst = megxLongHurst;
		this.megxCountry = megxCountry;
		this.megxInterpolatedValues = megxInterpolatedValues;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLatLon() {
		return latLon;
	}
	public void setLatLon(String latLon) {
		this.latLon = latLon;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public String getCollectionDate() {
		return collectionDate;
	}
	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}
	public String getBiome() {
		return biome;
	}
	public void setBiome(String biome) {
		this.biome = biome;
	}
	public String getSeqMeth() {
		return seqMeth;
	}
	public void setSeqMeth(String seqMeth) {
		this.seqMeth = seqMeth;
	}
	public String getAssemblyStatus() {
		return assemblyStatus;
	}
	public void setAssemblyStatus(String assemblyStatus) {
		this.assemblyStatus = assemblyStatus;
	}
	public String getMegxLongHurst() {
		return megxLongHurst;
	}
	public void setMegxLongHurst(String megxLongHurst) {
		this.megxLongHurst = megxLongHurst;
	}
	public String getMegxCountry() {
		return megxCountry;
	}
	public void setMegxCountry(String megxCountry) {
		this.megxCountry = megxCountry;
	}
	public String getMegxInterpolatedValues() {
		return megxInterpolatedValues;
	}
	public void setMegxInterpolatedValues(String megxInterpolatedValues) {
		this.megxInterpolatedValues = megxInterpolatedValues;
	}
	
	
}
