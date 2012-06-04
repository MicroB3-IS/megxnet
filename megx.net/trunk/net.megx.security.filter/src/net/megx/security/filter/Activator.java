package net.megx.security.filter;

import javax.servlet.Filter;

import net.megx.security.auth.web.WebUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRAppConfgEnabledActivator;
import org.json.JSONObject;
import org.osgi.framework.BundleContext;

public class Activator extends JCRAppConfgEnabledActivator {
	private static final Log log = LogFactory.getLog(Activator.class);
	@Override
	public void start(BundleContext context) throws Exception {
		try{
		System.out.println(">>Starting security filter");
		super.start(context);
		JSONObject cfg = getConfig();
		
		
		
		Filter filter = new  SecurityFilter(context, cfg);
		WebUtils.registerFilter(context, filter, "/.*", null, 1, null);
		
		log.debug(cfg.optString("exampleProperty", "default value"));
		}catch (Exception e) {
			e.printStackTrace(System.out);
		}
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
