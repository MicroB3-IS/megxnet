/*
 * Copyright 2011 Max Planck Institute for Marine Microbiology 

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


package net.megx.ws.core.providers.exceptions;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="exception")
public class ExceptionInfo {
	private int status;
	private String message;
	private String description;
	
	public int getStatus() {
		return status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getDescription() {
		return description;
	}

	public ExceptionInfo(int status, String message, String description) {
		super();
		this.status = status;
		this.message = message;
		this.description = description;
	}

	@Override
	public String toString() {
		return status + " " + message + (description != null? (": " + description) : "");
	}
	
	public ExceptionInfo() {}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
