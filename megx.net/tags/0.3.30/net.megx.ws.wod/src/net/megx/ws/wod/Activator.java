package net.megx.ws.wod;

import net.megx.megdb.wod05.Wod05Service;
import net.megx.utils.OSGIUtils;
import net.megx.utils.OSGIUtils.OnServiceAvailable;
import net.megx.ws.wod.rest.Wod05RestService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;

public class Activator extends ResTplConfiguredActivator {
	private static final Log log = LogFactory.getLog(Activator.class);
	
	@Override
	protected void registerExtensions(JCRApplication app) {
		OSGIUtils.requestService(Wod05Service.class.getName(), getBundleContext(), new OnServiceAvailable<Wod05Service>() {
			@Override
			public void serviceAvailable(String name, Wod05Service service) {
				log.debug("Got " + Wod05Service.class.getName() + " service. Registering REST");
				Wod05RestService wod05RestService = new Wod05RestService(service);
				RegUtils.reg(getBundleContext(), Wod05RestService.class.getName(), wod05RestService, null);
			}
		});
	}

	@Override
	protected String getName() {
		return "net.megx.ws.wod";
	}
	
}
