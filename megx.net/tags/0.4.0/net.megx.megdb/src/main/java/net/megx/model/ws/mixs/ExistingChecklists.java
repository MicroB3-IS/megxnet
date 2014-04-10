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

@XmlType(propOrder={
		"item",
		"label",
		"definition",
		"expected_value",
		"syntax",
		"example",
		"help",
		"occurrence",
		"regexp",
		"section",
		"sample_assoc",
		"eu",
		"ba",
		"pl",
		"vi",
		"org",
		"me",
		"miens_s",
		"miens_c",
		"pos",
		"ctime",
		"utime",
		"value_type",
		"expected_value_details",
		"epicollectable"
},name="ExistingChecklistsType")
public class ExistingChecklists {
	private String item;
	
	private String label;
	private String definition;
	private String expected_value;
	private String syntax;
	private String example;
	private String help;
	private String occurrence;
	private String regexp;
	private String section;
	private String sample_assoc;
	private String eu;
	private String ba;
	private String pl;
	private String vi;
	private String org;
	private String me;
	private String miens_s;
	private String miens_c;
	private String pos;
	private String ctime;
	private String utime;
	private String value_type;
	private String expected_value_details;
	private String epicollectable;
	
	public ExistingChecklists(){};
	
	public ExistingChecklists(String item, String label, String definition,
			String expected_value, String syntax, String example, String help,
			String occurrence, String regexp, String section,
			String sample_assoc, String eu, String ba, String pl, String vi,
			String org, String me, String miens_s, String miens_c, String pos,
			String ctime, String utime, String value_type,
			String expected_value_details, String epicollectable) {
		super();
		this.item = item;
		this.label = label;
		this.definition = definition;
		this.expected_value = expected_value;
		this.syntax = syntax;
		this.example = example;
		this.help = help;
		this.occurrence = occurrence;
		this.regexp = regexp;
		this.section = section;
		this.sample_assoc = sample_assoc;
		this.eu = eu;
		this.ba = ba;
		this.pl = pl;
		this.vi = vi;
		this.org = org;
		this.me = me;
		this.miens_s = miens_s;
		this.miens_c = miens_c;
		this.pos = pos;
		this.ctime = ctime;
		this.utime = utime;
		this.value_type = value_type;
		this.expected_value_details = expected_value_details;
		this.epicollectable = epicollectable;
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

	public String getExpected_value() {
		return expected_value;
	}

	public void setExpected_value(String expected_value) {
		this.expected_value = expected_value;
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

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSample_assoc() {
		return sample_assoc;
	}

	public void setSample_assoc(String sample_assoc) {
		this.sample_assoc = sample_assoc;
	}

	public String getEu() {
		return eu;
	}

	public void setEu(String eu) {
		this.eu = eu;
	}

	public String getBa() {
		return ba;
	}

	public void setBa(String ba) {
		this.ba = ba;
	}

	public String getPl() {
		return pl;
	}

	public void setPl(String pl) {
		this.pl = pl;
	}

	public String getVi() {
		return vi;
	}

	public void setVi(String vi) {
		this.vi = vi;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getMe() {
		return me;
	}

	public void setMe(String me) {
		this.me = me;
	}

	public String getMiens_s() {
		return miens_s;
	}

	public void setMiens_s(String miens_s) {
		this.miens_s = miens_s;
	}

	public String getMiens_c() {
		return miens_c;
	}

	public void setMiens_c(String miens_c) {
		this.miens_c = miens_c;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getUtime() {
		return utime;
	}

	public void setUtime(String utime) {
		this.utime = utime;
	}

	public String getValue_type() {
		return value_type;
	}

	public void setValue_type(String value_type) {
		this.value_type = value_type;
	}

	public String getExpected_value_details() {
		return expected_value_details;
	}

	public void setExpected_value_details(String expected_value_details) {
		this.expected_value_details = expected_value_details;
	}

	public String getEpicollectable() {
		return epicollectable;
	}

	public void setEpicollectable(String epicollectable) {
		this.epicollectable = epicollectable;
	}

	@Override
	public String toString() {
		return "ExistingChecklists [item=" + item + ", label=" + label
				+ ", definition=" + definition + ", expected_value="
				+ expected_value + ", syntax=" + syntax + ", example="
				+ example + ", help=" + help + ", occurrence=" + occurrence
				+ ", regexp=" + regexp + ", section=" + section
				+ ", sample_assoc=" + sample_assoc + ", eu=" + eu + ", ba="
				+ ba + ", pl=" + pl + ", vi=" + vi + ", org=" + org + ", me="
				+ me + ", miens_s=" + miens_s + ", miens_c=" + miens_c
				+ ", pos=" + pos + ", ctime=" + ctime + ", utime=" + utime
				+ ", value_type=" + value_type + ", expected_value_details="
				+ expected_value_details + ", epicollectable=" + epicollectable
				+ "]";
	}
	
	
	
}
