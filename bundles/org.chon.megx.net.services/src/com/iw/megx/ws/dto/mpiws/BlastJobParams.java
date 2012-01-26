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

public class BlastJobParams {
	public String sid;
	public String who;
	public String tool_label;
	public String tool_ver;
	public String db;
	public String program_name;
	public String seq;
	public String evalue;
	
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	public String getTool_label() {
		return tool_label;
	}
	public void setTool_label(String tool_label) {
		this.tool_label = tool_label;
	}
	public String getTool_ver() {
		return tool_ver;
	}
	public void setTool_ver(String tool_ver) {
		this.tool_ver = tool_ver;
	}
	public String getDb() {
		return db;
	}
	public void setDb(String db) {
		this.db = db;
	}
	public String getProgram_name() {
		return program_name;
	}
	public void setProgram_name(String program_name) {
		this.program_name = program_name;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getEvalue() {
		return evalue;
	}
	public void setEvalue(String evalue) {
		this.evalue = evalue;
	}
}
