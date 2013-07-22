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


package net.megx.ws.core.providers.html;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;

//@Component
//@Scope("singleton")
public class VelocityTemplater implements HTMLTemplater<Object>{
	private VelocityEngine engine;

	public void setEngine(VelocityEngine engine) {
		this.engine = engine;
	}

	
	public void merge(String template, Object data, Writer writer)
			throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("data", data);
		VelocityContext context = new VelocityContext(dataMap);
		Template vtemplate = engine.getTemplate(template);
		vtemplate.merge(context, writer);
		writer.flush();
		writer.close();
	}
	
	
	
}
