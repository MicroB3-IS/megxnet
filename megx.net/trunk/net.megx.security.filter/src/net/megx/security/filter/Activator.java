package net.megx.security.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRAppConfgEnabledActivator;
import org.json.JSONObject;
import org.osgi.framework.BundleContext;

public class Activator extends JCRAppConfgEnabledActivator {
	private static final Log log = LogFactory.getLog(Activator.class);
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		JSONObject cfg = getConfig();
		log.debug(cfg.optString("exampleProperty", "default value"));
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

	@Override
	protected String getName() {
		return "net.megx.security.filter";
	}
}
