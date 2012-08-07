package net.megx.security.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import net.megx.security.auth.services.UserService;
import net.megx.security.auth.services.WebResourcesService;
import net.megx.security.auth.web.WebUtils;
import net.megx.security.crypto.KeySecretProvider;
import net.megx.security.filter.http.impl.RegistrationExtension;
import net.megx.security.filter.ui.RegistrationManager;
import net.megx.security.filter.ui.ResourcesManager;
import net.megx.security.filter.ui.RolesManager;
import net.megx.security.filter.ui.UsersManager;
import net.megx.utils.OSGIUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.cms.model.ContentModel;
import org.json.JSONObject;
import org.osgi.framework.BundleContext;

public class Activator extends ResTplConfiguredActivator {
	private static final Log log = LogFactory.getLog(Activator.class);
	
	private ContentModel contentModel;
	
	@Override
	public void start(BundleContext context) throws Exception {
		try{
			super.start(context);
			JSONObject cfg = getConfig();
			Map<String, Object> contextParams = new HashMap<String, Object>();
			contextParams.put("JCRApplicationInstance", getJCRApp());
			Filter filter = new  SecurityFilter(context, cfg, contextParams);
			WebUtils.registerFilter(context, filter, "/.*", null, 1, null);
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
	protected void registerExtensions(final JCRApplication app) {
		contentModel = app.createContentModelInstance(getName());
		OSGIUtils.requestServices(getBundleContext(), new OSGIUtils.OnServicesAvailable(){
			
			@Override
			public void servicesAvailable(Map<String, Object> services) {
				UserService userService = (UserService)services.get(UserService.class.getName());
				WebResourcesService wrService = (WebResourcesService)services.get(WebResourcesService.class.getName());
				KeySecretProvider secretProvider = (KeySecretProvider)services.get(KeySecretProvider.class.getName());
				
				ResourcesManager rm = new ResourcesManager(wrService);
				UsersManager um = new UsersManager(userService, contentModel);
				RegistrationManager regm = new RegistrationManager(userService, secretProvider, 
						getRegistrationConfig(), app.getTemplate());
				RolesManager rolesManager = new RolesManager(userService);
				
				getBundleContext().registerService(ResourcesManager.class.getName(), rm, null);
				getBundleContext().registerService(UsersManager.class.getName(), um, null);
				getBundleContext().registerService(RegistrationManager.class.getName(), regm, null);
				getBundleContext().registerService(RolesManager.class.getName(), rolesManager, null);
				
				RegistrationExtension registrationExtension = new RegistrationExtension(userService, 
						getRegistrationConfig().optLong("verificationTTL", 24*60*60*1000));
				app.regExtension("registration", registrationExtension);
			}
		}, 
		WebResourcesService.class.getName(), UserService.class.getName(), KeySecretProvider.class.getName());
		
		
	}
	
	private JSONObject getRegistrationConfig(){
		JSONObject retVal = getConfig().optJSONObject("filter");
		if(retVal == null) retVal = new JSONObject();
		retVal = retVal.optJSONObject("registration");
		if(retVal == null) retVal = new JSONObject();
		return retVal;
	}
}
