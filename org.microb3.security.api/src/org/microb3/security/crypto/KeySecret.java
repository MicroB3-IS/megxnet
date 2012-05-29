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


package org.microb3.security.crypto;

public class KeySecret {
	private String key;
	private String secret;
	
	
	
	public KeySecret(String key, String secret) {
		super();
		this.key = key;
		this.secret = secret;
	}
	public String getKey() {
		return key;
	}
	public String getSecret() {
		return secret;
	}
	@Override
	public String toString() {
		return "KeySecret [key=" + key + ", secret=" + secret + "]";
	}
}
