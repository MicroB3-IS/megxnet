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


package net.megx.megdb.mixs.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.mixs.MixsWsDao;
import net.megx.megdb.mixs.mappers.MixsWsMapper;
import net.megx.megdb.mixs.mappers.MixsWsMapper_2_1;
import net.megx.model.ws.mixs.ChecklistItemDetails;
import net.megx.model.ws.mixs.EnvPackage;
import net.megx.model.ws.mixs.ExistingChecklists;
import net.megx.model.ws.mixs.IndependentMetadataItem;

public class MixsWsDaoImpl extends BaseMegdbService implements MixsWsDao {

	private static Map<String, Class<? extends MixsWsMapper>> mappers = new HashMap<String, Class<? extends MixsWsMapper>>();
	static{
		mappers.put("2_1", MixsWsMapper_2_1.class);
		mappers.put("current", MixsWsMapper_2_1.class);
	}
	
	public List<ChecklistItemDetails> getCombinedChecklistSpecificationItems(
			final String clName, final String envPackage, String version) throws Exception {
		
		return doInSession(new BaseMegdbService.DBTask<MixsWsMapper, List<ChecklistItemDetails>>() {

			@Override
			public List<ChecklistItemDetails> execute(MixsWsMapper mapper)
					throws Exception {
				return mapper.getCombinedChecklistSpecificationItems(
						clName, envPackage);
			}
			
		}, mappers.get(version));
	}

	public List<ChecklistItemDetails> getGeneralSpecificationItems(
			final String clNameEnvPkg, String version) throws Exception {
		return doInSession(new BaseMegdbService.DBTask<MixsWsMapper, List<ChecklistItemDetails>>() {

			@Override
			public List<ChecklistItemDetails> execute(MixsWsMapper mapper)
					throws Exception {
				return mapper.getGeneralSpecificationItems(clNameEnvPkg);
			}
			
		}, mappers.get(version));
	}

	public List<IndependentMetadataItem> getIndependentMetadataItems(
			final Map<Object, Object> map, String version) throws Exception {
		
		return doInSession(new BaseMegdbService.DBTask<MixsWsMapper, List<IndependentMetadataItem>>() {
			@Override
			public List<IndependentMetadataItem> execute(MixsWsMapper mapper)
					throws Exception {
				return mapper.getIndependentMetadataItems(map);
			}
		}, mappers.get(version));
	}

	public ChecklistItemDetails getParticularSpecificationItem(final String itemName,
			final String clNameEnvPkg, String version) throws Exception {
		return doInSession(new DBTask<MixsWsMapper, ChecklistItemDetails>() {

			@Override
			public ChecklistItemDetails execute(MixsWsMapper mapper)
					throws Exception {
				return mapper.getParticularSpecificationItem(itemName,
								clNameEnvPkg);
			}
			
		}, mappers.get(version) );
	}
	
	public List<EnvPackage> getEnvPackagesList(String version) throws Exception{
		return doInSession(new DBTask<MixsWsMapper, List<EnvPackage>>() {

			@Override
			public List<EnvPackage> execute(MixsWsMapper mapper)
					throws Exception {
				return mapper.getEnvPackagesList();
			}
			
		}, mappers.get(version));
	}
	public List<ExistingChecklists> getExistingChecklists(String version)
			throws Exception {
		return doInSession(new DBTask<MixsWsMapper, List<ExistingChecklists>>() {

			@Override
			public List<ExistingChecklists> execute(MixsWsMapper mapper)
					throws Exception {
				return mapper.getExistingChecklists();
			}
			
		}, mappers.get(version));
	}

}
