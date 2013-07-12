package net.megx.osd.registry;

import net.megx.megdb.osdregistry.OSDRegistryService;
import net.megx.osd.registry.rest.OSDRegistryAPI;
import net.megx.utils.OSGIUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;

public class Activator extends ResTplConfiguredActivator {
	
	protected Log log = LogFactory.getLog(getClass());

	@Override
	protected void registerExtensions(JCRApplication app) {
		log.debug("OSD Registry Starting up...");
		OSGIUtils.requestService(OSDRegistryService.class.getName(), 
				getBundleContext(), 
				new OSGIUtils.OnServiceAvailable<OSDRegistryService>() {

			@Override
			public void serviceAvailable(String name,
					OSDRegistryService service) {
				log.debug("OSDRegistryService service received...");
				OSDRegistryAPI api = new OSDRegistryAPI(service);
				RegUtils.reg(getBundleContext(), OSDRegistryAPI.class.getName(), api, null);
				log.debug("OSD Registry app started.");
			}
			
		});
		log.debug("OSD Registry service requested.");
	}

	@Override
	protected String getName() {
		return "net.megx.osd.registry";
	}
	
}
