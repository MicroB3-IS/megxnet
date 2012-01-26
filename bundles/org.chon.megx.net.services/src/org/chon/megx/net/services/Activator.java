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
import org.springframework.context.ApplicationContext;

import com.iw.megx.ws.service.mixsws.MixsWsService;
import com.iw.megx.ws.service.mpiws.MpiWsService;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		
		
		ApplicationContext springCtx = new BundleApplicationContext(this,
				context, new String[] { "cfg/applicationContext.xml",
						"cfg/dao-beans.xml", "cfg/service-beans.xml" });
		
		//MixsWsService service = (MixsWsService) springCtx.getBean("mixsWsService");
		
		//context.registerService(MixsWsService.class.getName(), service, null);
		
		MpiWsService service = (MpiWsService) springCtx.getBean("mpiWsService");
		context.registerService(MpiWsService.class.getName(), service, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		//
	}

}
