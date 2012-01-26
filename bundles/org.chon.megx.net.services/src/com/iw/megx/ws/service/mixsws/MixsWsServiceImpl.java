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

import com.iw.megx.ws.dao.mixsws.MixsWsDao;
import com.iw.megx.ws.dto.mixsws.ChecklistItemDetails;
import com.iw.megx.ws.dto.mixsws.IndependentMetadataItem;

public class MixsWsServiceImpl implements MixsWsService{
	
	private MixsWsDao mixsWsDao;

	public void setMixsWsDao(MixsWsDao mixsWsDao) {
		this.mixsWsDao = mixsWsDao;
	}

	
	public List<ChecklistItemDetails> getCombinedChecklistSpecificationItems(
			String clName, String envPackage, String version) throws Exception {
		return mixsWsDao.getCombinedChecklistSpecificationItems(clName, envPackage, version);
	}

	
	public List<ChecklistItemDetails> getGeneralSpecificationItems(
			String clNameEnvPkg, String version) throws Exception {
		return mixsWsDao.getGeneralSpecificationItems(clNameEnvPkg, version);
	}

	
	public List<IndependentMetadataItem> getIndependentMetadataItems(Map<Object, Object> map, String version)
			throws Exception {
		return mixsWsDao.getIndependentMetadataItems(map, version);
	}

	
	public ChecklistItemDetails getParticularSpecificationItem(String itemName,
			String clNameEnvPkg, String version) throws Exception {
		return mixsWsDao.getParticularSpecificationItem(itemName, clNameEnvPkg, version);
	}

}
