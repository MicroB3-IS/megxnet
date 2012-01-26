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
package com.iw.megx.ws.rest.mixsws;

/**
 * Container of resources needed by MixWsResources
 * @author Mile.Stefanovski
 *
 */
public interface MixWsResourceConstants {
	/**
	 * Comma separated {@link String} of all gsc_db.clist_item_details column names
	 */
	public static final String clistItemDetailsColumns = "item,label,definition,expected_value,expected_value_details,value_type,syntax,example,help,occurrence,regexp,sample_assoc";
}
