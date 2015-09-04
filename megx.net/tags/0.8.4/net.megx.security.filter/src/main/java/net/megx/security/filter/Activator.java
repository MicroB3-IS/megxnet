package net.megx.security.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import net.megx.security.auth.services.UserService;
import net.megx.security.auth.services.WebResourcesService;
import net.megx.security.auth.web.WebUtils;
import net.megx.security.crypto.KeySecretProvider;
import net.megx.security.filter.extensions.SecurityFilterExtension;
import net.megx.security.filter.http.impl.RegistrationExtension;
import net.megx.security.filter.impl.OAuthEchoVerifyCredentials;
import net.megx.security.filter.ui.RegistrationManager;
import net.megx.security.filter.ui.ResourcesManager;
import net.megx.security.filter.ui.RolesManager;
import net.megx.security.filter.ui.UsersManager;
import net.megx.utils.OSGIUtils;

import net.megx.mailer.BaseMailerService;

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
		
		try{
			JSONObject cfg = getConfig();
			Map<String, Object> contextParams = new HashMap<String, Object>();
			contextParams.put("JCRApplicationInstance", getJCRApp());
			Filter filter = new  SecurityFilter(getBundleContext(), cfg, contextParams);
			WebUtils.registerFilter(getBundleContext(), filter, "/.*", null, 1, null);
		}catch (Exception e) {
			log.error("Failed to start security filter",e);
		}
		
		OSGIUtils.requestServices(getBundleContext(), new OSGIUtils.OnServicesAvailable(){
			
			@Override
			public void servicesAvailable(Map<String, Object> services) {
				UserService userService = (UserService)services.get(UserService.class.getName());
				WebResourcesService wrService = (WebResourcesService)services.get(WebResourcesService.class.getName());
				KeySecretProvider secretProvider = (KeySecretProvider)services.get(KeySecretProvider.class.getName());
        BaseMailerService mailerService = (BaseMailerService)services.get(BaseMailerService.class.getName());
				
				ResourcesManager rm = new ResourcesManager(wrService);
				UsersManager um = new UsersManager(userService, contentModel);
				
				RegistrationManager regm = new RegistrationManager(userService, secretProvider, 
						getRegistrationConfig(), app.getTemplate(), mailerService);
				
				RolesManager rolesManager = new RolesManager(userService);
				
				
				
				getBundleContext().registerService(ResourcesManager.class.getName(), rm, null);
				getBundleContext().registerService(UsersManager.class.getName(), um, null);
				getBundleContext().registerService(RegistrationManager.class.getName(), regm, null);
				getBundleContext().registerService(RolesManager.class.getName(), rolesManager, null);
				
				RegistrationExtension registrationExtension = new RegistrationExtension(userService, 
						getRegistrationConfig(), app.getTemplate(), mailerService);
				
				app.regExtension("registration", registrationExtension);
				
				OAuthEchoVerifyCredentials verifyCredentialsService = new OAuthEchoVerifyCredentials();
				getBundleContext().registerService(OAuthEchoVerifyCredentials.class.getName(), verifyCredentialsService, null);
			}
		}, 
		WebResourcesService.class.getName(), UserService.class.getName(), KeySecretProvider.class.getName(), BaseMailerService.class.getName());
		//WebUtils.registerServlet(getBundleContext(), new ErrorProducingServlet(), "/produceError", null, null);
		app.regExtension("securityFilter", new SecurityFilterExtension(getConfig()));
	}
	
	
	private JSONObject getRegistrationConfig(){
		JSONObject retVal = getConfig().optJSONObject("filter");
		if(retVal == null) retVal = new JSONObject();
		retVal = retVal.optJSONObject("registration");
		if(retVal == null) retVal = new JSONObject();
		return retVal;
	}
}
