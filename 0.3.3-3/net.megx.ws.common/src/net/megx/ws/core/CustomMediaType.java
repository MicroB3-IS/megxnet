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


package net.megx.ws.core;

import javax.ws.rs.core.MediaType;

public abstract class CustomMediaType {
	public static final String APPLICATION_CSV = "application/csv";
	public static final String TEXT_CSV = "text/csv";
	
	public static final MediaType APPLICATION_CSV_TYPE;
	public static final MediaType TEXT_CSV_TYPE;
	
	static{
		APPLICATION_CSV_TYPE = new MediaType("application", "csv");
		TEXT_CSV_TYPE = new MediaType("text","csv");
	}
}
