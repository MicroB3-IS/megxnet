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

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

public abstract class HttpStatusCodes {
	
	public static final int METHOD_NOT_FOUND = 405;
	
	
	
	
	private static Map<Integer, String> phrases = new HashMap<Integer, String>();
	
	static{
		phrases.put(METHOD_NOT_FOUND, "Method not found");
	}
	
	public static String phraseForStatusCode(int code){
		String phrase = phrases.get(code);
		if(phrase == null){
			Response.Status status = Response.Status.fromStatusCode(code);
			if(status != null){
				phrase = status.getReasonPhrase();
			}
		}
		return phrase;
	}
}
