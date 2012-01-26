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
package org.chon.megx.net.services;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;


public class BundleApplicationContext extends AbstractXmlApplicationContext {
	
	private Resource[] resources;
	
	public BundleApplicationContext(BundleActivator activator, BundleContext ctx, String [] ctxLocations) {
		if(ctxLocations==null) {
			ctxLocations = new String[] {"applicationContext.xml"};
		}
		
		resources = new Resource[ctxLocations.length];
		for(int i=0; i<ctxLocations.length; i++) {
			String res = ctxLocations[i];
			resources[i] = new UrlResource(ctx.getBundle().getResource(res));
		}
		this.setClassLoader(activator.getClass().getClassLoader());
		refresh();
	}

	@Override
	protected Resource[] getConfigResources() {
		return resources;
	}
}
