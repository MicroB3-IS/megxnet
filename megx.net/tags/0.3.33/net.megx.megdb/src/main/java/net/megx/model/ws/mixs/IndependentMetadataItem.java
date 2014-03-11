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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

//import net.megx.ws.core.providers.csv.annotations.CSVColumn;

@XmlRootElement(name="item")
@XmlType(name="IndependentMetadataItemType",
propOrder={
		"item",
		"label",
		"definition",
		"expected_value",
		"expected_value_details",
		"value_type",
		"syntax",
		"example",
		"help",
		"occurrence",
		"regexp",
		"sample_assoc"
})
public class IndependentMetadataItem {
	
	private String item;
	private String label;
	private String definition;
	
//	@CSVColumn(name="expected_value")
	private String expectedValue;
	
//	@CSVColumn(name="expected_value_details")
	private String expectedValueDetails;
	
//	@CSVColumn(name="value_type")
	private String valueType;
	private String syntax;
	private String example;
	private String help;
	private String occurrence;
	private String regexp;
	
//	@CSVColumn(name="sample_assoc")
	private String sampleAssoc;
	
	public IndependentMetadataItem(){}

	public IndependentMetadataItem(String item, String label,
			String definition, String expectedValue,
			String expectedValueDetails, String valueType, String syntax,
			String example, String help, String occurrence, String regexp,
			String sampleAssoc) {
		super();
		this.item = item;
		this.label = label;
		this.definition = definition;
		this.expectedValue = expectedValue;
		this.expectedValueDetails = expectedValueDetails;
		this.valueType = valueType;
		this.syntax = syntax;
		this.example = example;
		this.help = help;
		this.occurrence = occurrence;
		this.regexp = regexp;
		this.sampleAssoc = sampleAssoc;
	}

	

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	@XmlTransient
	public String getExpectedValue() {
		return expectedValue;
	}

	public void setExpectedValue(String expectedValue) {
		this.expectedValue = expectedValue;
	}
	
	public String getExpected_value() {
		return expectedValue;
	}

	public void setExpected_value(String expectedValue) {
		this.expectedValue = expectedValue;
	}

	@XmlTransient
	public String getExpectedValueDetails() {
		return expectedValueDetails;
	}

	public void setExpectedValueDetails(String expectedValueDetails) {
		this.expectedValueDetails = expectedValueDetails;
	}
	
	public String getExpected_value_details() {
		return expectedValueDetails;
	}

	public void setExpected_value_details(String expectedValueDetails) {
		this.expectedValueDetails = expectedValueDetails;
	}

	@XmlTransient
	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
	public String getValue_type() {
		return valueType;
	}

	public void setValue_type(String valueType) {
		this.valueType = valueType;
	}
	
	public String getSyntax() {
		return syntax;
	}

	public void setSyntax(String syntax) {
		this.syntax = syntax;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public String getOccurrence() {
		return occurrence;
	}

	public void setOccurrence(String occurrence) {
		this.occurrence = occurrence;
	}

	public String getRegexp() {
		return regexp;
	}

	public void setRegexp(String regexp) {
		this.regexp = regexp;
	}

	@XmlTransient
	public String getSampleAssoc() {
		return sampleAssoc;
	}

	public void setSampleAssoc(String sampleAssoc) {
		this.sampleAssoc = sampleAssoc;
	}
	
	public String getSample_assoc() {
		return sampleAssoc;
	}

	public void setSample_assoc(String sampleAssoc) {
		this.sampleAssoc = sampleAssoc;
	}
	
	@Override
	public String toString() {
		return "IndependentMetadataItem [definition=" + definition
				+ ", example=" + example + ", expectedValue=" + expectedValue
				+ ", expectedValueDetails=" + expectedValueDetails + ", help="
				+ help + ", item=" + item + ", label=" + label
				+ ", occurrence=" + occurrence + ", regexp=" + regexp
				+ ", sampleAssoc=" + sampleAssoc + ", syntax=" + syntax
				+ ", valueType=" + valueType + "]";
	}
	
}
