package net.megx.pubmap;

import net.megx.pubmap.rest.PubMapRest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.osgi.framework.BundleContext;

public class Activator extends ResTplConfiguredActivator {
	
	private static final Log log = LogFactory.getLog(Activator.class);
	
	@Override
	protected void registerExtensions(JCRApplication app) {
		log.debug("Start PubMap bundle");
		BundleContext bundleContext = getBundleContext();
		
		registerRestServices(bundleContext);
		//trackMVCService();
		
		PubMapExtension pubMapExt = new PubMapExtension(bundleContext);
		log.debug("Registering pubmap extension.");
		app.regExtension("pubmap", pubMapExt);
	}

	private void registerRestServices(BundleContext bundleContext) {
		bundleContext.registerService(PubMapRest.class.getName(), new PubMapRest(bundleContext, getConfig()), null);
	}

	@Override
	protected String getName() {
		return "net.megx.pubmap";
	}
	

}
