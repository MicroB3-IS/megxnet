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

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import net.megx.utils.OSGIUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.web.api.Application;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

@Provider
@Produces(MediaType.TEXT_HTML)
public class VelocityHTMLProvider extends AbstractHTMLProvider {

	private Log log = LogFactory.getLog(getClass());
	private HTMLTemplater<Object> template;

	public VelocityHTMLProvider() {
		Bundle bundle =  FrameworkUtil.getBundle(getClass());
		OSGIUtils.requestService(Application.class.getName(), bundle.getBundleContext(), new OSGIUtils.OnServiceAvailable<Application>() {

			@Override
			public void serviceAvailable(String name, Application service) {
				template = new ChonTemplater(service.getTemplate());
				log.info("Velocity HTML Provider successfully initialized.");
			}
			
		});
	}

	protected HTMLTemplater<Object> getHTMLTemplater() {
		if(template == null){
			log.error("HTMLTemplater is not yet available.");
			throw new RuntimeException("HTMLTemplater unavaliable at this time.");
		}
		return template;
	}

	public void setTemplate(HTMLTemplater<Object> template) {
		this.template = template;
	}

}
