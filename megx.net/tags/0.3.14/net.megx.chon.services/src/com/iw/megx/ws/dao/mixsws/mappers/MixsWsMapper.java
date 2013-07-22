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
package com.iw.megx.ws.dao.mixsws.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iw.megx.ws.dto.mixsws.ChecklistItemDetails;
import com.iw.megx.ws.dto.mixsws.IndependentMetadataItem;

public interface MixsWsMapper {

	public List<IndependentMetadataItem> getIndependentMetadataItems(Map<Object, Object> map) throws Exception;

	public List<ChecklistItemDetails> getGeneralSpecificationItems(
			String clNameEnvPkg) throws Exception;

	public List<ChecklistItemDetails> getCombinedChecklistSpecificationItems(
			@Param("cl_name") String clName, @Param("env_pkg") String envPackage)
			throws Exception;

	public ChecklistItemDetails getParticularSpecificationItem(
			@Param("item_name") String itemName,
			@Param("cl_name_env_pkg") String clNameEnvPkg) throws Exception;
}
