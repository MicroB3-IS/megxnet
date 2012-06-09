package net.megx.security.filter;

import javax.servlet.Filter;

import net.megx.security.auth.web.WebUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.json.JSONObject;
import org.osgi.framework.BundleContext;

public class Activator extends ResTplConfiguredActivator {
	private static final Log log = LogFactory.getLog(Activator.class);
	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("=============================================");
		try{
			super.start(context);
			System.out.println("=============================================");
			System.out.println(super.toString());
			System.out.println("=============================================");
			
			System.out.println(">>Starting security filter");
			
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

	@Override
	protected void registerExtensions(JCRApplication arg0) { }
}
