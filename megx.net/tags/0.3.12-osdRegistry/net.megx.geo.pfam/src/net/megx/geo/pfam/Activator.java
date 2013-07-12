package net.megx.geo.pfam;

import net.megx.geo.pfam.rest.GeoPfamRestService;
import net.megx.megdb.geopfam.GeoPfamService;
import net.megx.utils.OSGIUtils;
import net.megx.utils.OSGIUtils.OnServiceAvailable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;

public class Activator extends ResTplConfiguredActivator {
	
	private static final Log log = LogFactory.getLog(Activator.class);
	
	@Override
	protected void registerExtensions(JCRApplication app) {
		OSGIUtils.requestService(GeoPfamService.class.getName(), getBundleContext(), new OnServiceAvailable<GeoPfamService>() {
			@Override
			public void serviceAvailable(String name, GeoPfamService service) {
				log.debug("Got " + GeoPfamService.class.getName() + " service. Registering REST");
				GeoPfamRestService geoPfamRestService = new GeoPfamRestService(service);
				RegUtils.reg(getBundleContext(), GeoPfamRestService.class.getName(), geoPfamRestService, null);
			}
		});
	}

	@Override
	protected String getName() {
		return "net.megx.geo.pfam";
	}
	
}
