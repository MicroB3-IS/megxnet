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
package com.iw.megx.ws.dto.mixsws;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="item")
public class ChecklistItemDetails {
	private int id; // this will go away :)
	
	private String item;
	private String checklistName;
	private String requirement;
	private String label;
	private String definition;
	private String expectedValue;
	private String expectedValueDetails;
	private String valueType;
	private String syntax;
	private String example;
	private String help;
	private String occurrence;
	private String regexp;
	private String sampleAssoc;
	
	public ChecklistItemDetails() {}

	public ChecklistItemDetails(int id, String item, String checklistName,
			String requirement, String label, String definition,
			String expectedValue, String expectedValueDetails,
			String valueType, String syntax, String example, String help,
			String occurence, String regexp, String sampleAssoc) {
		super();
		this.id = id;
		this.item = item;
		this.checklistName = checklistName;
		this.requirement = requirement;
		this.label = label;
		this.definition = definition;
		this.expectedValue = expectedValue;
		this.expectedValueDetails = expectedValueDetails;
		this.valueType = valueType;
		this.syntax = syntax;
		this.example = example;
		this.help = help;
		this.occurrence = occurence;
		this.regexp = regexp;
		this.sampleAssoc = sampleAssoc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getChecklistName() {
		return checklistName;
	}

	public void setChecklistName(String checklistName) {
		this.checklistName = checklistName;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
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

	public String getExpectedValue() {
		return expectedValue;
	}

	public void setExpectedValue(String expectedValue) {
		this.expectedValue = expectedValue;
	}

	public String getExpectedValueDetails() {
		return expectedValueDetails;
	}

	public void setExpectedValueDetails(String expectedValueDetails) {
		this.expectedValueDetails = expectedValueDetails;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
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

	public String getSampleAssoc() {
		return sampleAssoc;
	}

	public void setSampleAssoc(String sampleAssoc) {
		this.sampleAssoc = sampleAssoc;
	}

	@Override
	public String toString() {
		return "ChecklistItemDetails [checklistName=" + checklistName
				+ ", definition=" + definition + ", example=" + example
				+ ", expectedValue=" + expectedValue
				+ ", expectedValueDetails=" + expectedValueDetails + ", help="
				+ help + ", id=" + id + ", item=" + item + ", label=" + label
				+ ", occurrence=" + occurrence + ", regexp=" + regexp
				+ ", requirement=" + requirement + ", sampleAssoc="
				+ sampleAssoc + ", syntax=" + syntax + ", valueType="
				+ valueType + "]";
	}
}
