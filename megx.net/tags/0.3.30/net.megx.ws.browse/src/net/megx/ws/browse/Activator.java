package net.megx.ws.browse;

import net.megx.megdb.browse.BrowseService;
import net.megx.utils.OSGIUtils;
import net.megx.utils.OSGIUtils.OnServiceAvailable;
import net.megx.ws.browse.rest.BrowseRestService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;

public class Activator extends ResTplConfiguredActivator {
	private static final Log log = LogFactory.getLog(Activator.class);
	
	@Override
	protected void registerExtensions(JCRApplication app) {
		OSGIUtils.requestService(BrowseService.class.getName(), getBundleContext(), new OnServiceAvailable<BrowseService>() {
			@Override
			public void serviceAvailable(String name, BrowseService service) {
				log.debug("Got " + BrowseService.class.getName() + " service. Registering REST");
				BrowseRestService browseRestService = new BrowseRestService(service);
				RegUtils.reg(getBundleContext(), BrowseRestService.class.getName(), browseRestService, null);
			}
		});
	}

	@Override
	protected String getName() {
		return "net.megx.ws.browse";
	}
	
}
