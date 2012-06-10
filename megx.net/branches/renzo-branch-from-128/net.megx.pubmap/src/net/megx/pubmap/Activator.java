package net.megx.pubmap;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.cms.light.mvc.AbstractAction;
import org.chon.cms.light.mvc.LightMVCService;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

public class Activator extends ResTplConfiguredActivator {
	
	private static final Log log = LogFactory.getLog(Activator.class);
	
	@Override
	protected void registerExtensions(JCRApplication app) {
		log.debug("Start PubMap bundle");
		
		PubMapExtension pubMapExt = new PubMapExtension();
		log.debug("Registering pubmap extension.");
		app.regExtension("pubmap", pubMapExt);
	}

	@Override
	protected String getName() {
		return "net.megx.pubmap";
	}
	

}
