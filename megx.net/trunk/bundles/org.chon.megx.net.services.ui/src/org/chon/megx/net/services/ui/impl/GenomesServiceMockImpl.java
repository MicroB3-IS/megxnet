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
package org.chon.megx.net.services.ui.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.chon.megx.net.services.ui.pub.EnvOLite;
import org.chon.megx.net.services.ui.pub.Genome;
import org.chon.megx.net.services.ui.pub.IGenomes;

public class GenomesServiceMockImpl implements IGenomes {
	private List<Genome> genomes;
	private Map<String, List<Genome>> byEnvMap = new HashMap<String, List<Genome>>();
	public GenomesServiceMockImpl(List<Genome> genomes) {
		this.genomes = genomes;
		for(Genome g : genomes) {
			String env = g.getEnvOLite();
			List<Genome> ls;
			if(byEnvMap.containsKey(env)) {
				ls = byEnvMap.get(env);
			} else {
				ls = new ArrayList<Genome>();
				byEnvMap.put(env, ls);
			}
			ls.add(g);
		}
	}

	@Override
	public List<Genome> getGenomes(Integer start, Integer limit) {
		List<Genome> ls = genomes;
		//invalid start argument
		if(start>ls.size()) return null;
		int end = start+limit;
		if(end > ls.size()) {
			end = ls.size();
		}
		return ls.subList(start, end);
	}

	@Override
	public List<Genome> getGenomes(String env, Integer start, Integer limit) {
		List<Genome> ls = getAllGenomesByEnv(env);
		//invalid start argument
		if(start>ls.size()) return null;
		int end = start+limit;
		if(end > ls.size()) {
			end = ls.size();
		}
		return ls.subList(start, end);
	}


	@Override
	public long getGenomesSize(String env) {
		List<Genome> ls = getAllGenomesByEnv(env);
		return ls.size();
	}

	@Override
	public long getGenomesSize() {
		return genomes.size();
	}
	
	private List<Genome> getAllGenomesByEnv(String env) {
		return byEnvMap.get(env);
	}

	@Override
	public List<EnvOLite> getGenomesEnvs() {
		List<EnvOLite> rv = new ArrayList<EnvOLite>();
		Set<String> keys = byEnvMap.keySet();
		for(String k : keys) {
			EnvOLite envOLite = new EnvOLite();
			envOLite.setTag(k);
			envOLite.setQuantity(getGenomesSize(k));
			byEnvMap.get(envOLite);
			rv.add(envOLite);
		}
		return rv;
	}

//	@Override
//	public Set<String> getGenomesEnvs() {
//		return byEnvMap.keySet();
//	}
}
