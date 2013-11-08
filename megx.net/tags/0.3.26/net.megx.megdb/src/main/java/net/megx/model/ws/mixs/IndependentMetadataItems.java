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

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name="items")
@XmlType(name="IndependentMetadataItemsType")
public class IndependentMetadataItems {

	private List<IndependentMetadataItem> items;
	@XmlTransient
	private String[] cols;
	@XmlTransient
	public String[] getCols() {
		return cols;
	}

	public void setCols(String[] cols) {
		this.cols = cols;
	}

	public IndependentMetadataItems(List<IndependentMetadataItem> items) {
		super();
		this.items = items;
	}
	
	public IndependentMetadataItems() {}

	public List<IndependentMetadataItem> getItems() {
		return items;
	}

	public void setItems(List<IndependentMetadataItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "IndependentMetadataItems [items=" + items + "]";
	}
}
