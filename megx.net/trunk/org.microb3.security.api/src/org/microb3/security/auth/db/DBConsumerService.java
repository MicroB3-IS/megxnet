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


package org.microb3.security.auth.db;

import java.util.List;

import org.microb3.security.auth.Consumer;
import org.microb3.security.auth.ConsumerService;



public class DBConsumerService implements ConsumerService{
	
	private ConsumerMapper mapper;
	
	public void setMapper(ConsumerMapper mapper) {
		this.mapper = mapper;
	}

	public Consumer getConsumer(String name) throws Exception {
		return mapper.getConsumerForName(name);
	}

	public Consumer getConsumerForKey(String key) throws Exception {
		return mapper.getConsumerForKey(key);
	}

	
	public Consumer addConsumer(Consumer consumer) throws Exception {
		mapper.addConsumer(consumer);
		return consumer;
	}

	public Consumer getConsumerForKeyAndName(String key, String name)
			throws Exception {
		return mapper.getConsumerForKeyAndName(key, name);
	}

	
	public void removeConsumer(Consumer consumer) throws Exception {
		mapper.removeTokensForConsumer(consumer.getKey());
		mapper.removeConsumer(consumer);
	}

	
	public Consumer updateConsumer(Consumer consumer) throws Exception {
		 mapper.updateConsumer(consumer);
		 return mapper.getConsumerForName(consumer.getName());
	}

	public List<Consumer> getConsumersForUser(String userId) throws Exception {
		return mapper.getConsumersForUserId(userId);
	}

	
	
}
