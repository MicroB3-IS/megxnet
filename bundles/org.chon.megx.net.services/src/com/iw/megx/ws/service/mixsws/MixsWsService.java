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
package com.iw.megx.ws.service.mixsws;

import java.util.List;
import java.util.Map;

import com.iw.megx.ws.dto.mixsws.ChecklistItemDetails;
import com.iw.megx.ws.dto.mixsws.IndependentMetadataItem;

public interface MixsWsService {
	
	public List<IndependentMetadataItem> getIndependentMetadataItems(Map<Object, Object> map, String version)
			throws Exception;

	public List<ChecklistItemDetails> getGeneralSpecificationItems(
			String clNameEnvPkg, String version) throws Exception;

	public List<ChecklistItemDetails> getCombinedChecklistSpecificationItems(
			String clName, String envPackage, String version) throws Exception;

	public ChecklistItemDetails getParticularSpecificationItem(String itemName,
			String clNameEnvPkg, String version) throws Exception;
}
