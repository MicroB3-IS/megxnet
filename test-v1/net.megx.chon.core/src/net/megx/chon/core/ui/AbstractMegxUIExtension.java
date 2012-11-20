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
package net.megx.chon.core.ui;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.Extension;
import org.chon.megx.net.services.ui.pub.IGenomes;
import org.chon.web.mpac.Action;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.iw.megx.ws.service.mpiws.*;


public abstract class AbstractMegxUIExtension implements Extension {
	private static final Log log = LogFactory.getLog(AbstractMegxUIExtension.class);
	
	private BundleContext bundleContext;
	
	public AbstractMegxUIExtension(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}
	@Override
	public Map<String, Action> getAdminActons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Action> getAjaxActons() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected IGenomes getGenomesService() {
		ServiceReference ref = bundleContext
				.getServiceReference(IGenomes.class.getName());
		if (ref == null) {
			log.error(IGenomes.class.getName() + " service not found!");
			return null;
		}
		return (IGenomes) bundleContext.getService(ref);
	}
	
	protected MpiWsService getMpiDbService() {
		ServiceReference ref = bundleContext
				.getServiceReference(MpiWsService.class.getName());
		if (ref == null) {
			log.error(MpiWsService.class.getName() + " service not found!");
			return null;
		}
		return (MpiWsService) bundleContext.getService(ref);
	}	
}
