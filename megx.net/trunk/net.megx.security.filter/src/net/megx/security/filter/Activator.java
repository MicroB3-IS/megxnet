package net.megx.security.filter;

import java.util.Map;

import javax.servlet.Filter;

import net.megx.security.auth.services.UserService;
import net.megx.security.auth.services.WebResourcesService;
import net.megx.security.auth.web.WebUtils;
import net.megx.security.filter.ui.ResourcesManager;
import net.megx.security.filter.ui.UsersManager;
import net.megx.utils.OSGIUtils;

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
		try{
			super.start(context);
			System.out.println(super.toString());
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
	protected void registerExtensions(JCRApplication arg0) { 
		OSGIUtils.requestServices(getBundleContext(), new OSGIUtils.OnServicesAvailable(){
			
			@Override
			public void servicesAvailable(Map<String, Object> services) {
				UserService userService = (UserService)services.get(UserService.class.getName());
				WebResourcesService wrService = (WebResourcesService)services.get(WebResourcesService.class.getName());
				
				ResourcesManager rm = new ResourcesManager(wrService);
				UsersManager um = new UsersManager(userService);
				
				getBundleContext().registerService(ResourcesManager.class.getName(), rm, null);
				getBundleContext().registerService(UsersManager.class.getName(), um, null);
				
			}
		}, 
		WebResourcesService.class.getName(), UserService.class.getName());
		
		
	}
}
