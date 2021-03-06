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


package net.megx.security.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SimpleCache implements Cache{

	private static ConcurrentMap<Object, Object> cache = new ConcurrentHashMap<Object, Object>();
	
	public void storeObject(Object key, Object object) {
		cache.putIfAbsent(key, object);
	}

	public Object getObject(Object key) {
		return cache.get(key);
	}

	public Object removeObject(Object key) {
		Object object = getObject(key); // FIXME
		return cache.remove(key, object);
	}

	@Override
	public List<Object> getCached() {
		List<Object> cached = new ArrayList<Object>(cache.size());
		for(Map.Entry<Object, Object> e: cache.entrySet()){
			cached.add(e.getValue());
		}
		return cached;
	}

}
