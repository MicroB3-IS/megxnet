package net.megx.security.apps;

import java.util.Map;

import net.megx.security.auth.services.ConsumerService;
import net.megx.security.auth.services.TokenService;
import net.megx.utils.OSGIUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.osgi.framework.BundleContext;

public class Activator extends ResTplConfiguredActivator {

	private Log log = LogFactory.getLog(getClass());
	
	@Override
	protected void registerExtensions(JCRApplication app) {
		OSGIUtils.requestServices(getBundleContext(),
				new OSGIUtils.OnServicesAvailable() {

					@Override
					public void servicesAvailable(Map<String, Object> services) {
						try{
						TokenService tokenService = (TokenService) services
								.get(TokenService.class.getName());
						ConsumerService consumerService = (ConsumerService) services
								.get(ConsumerService.class.getName());

						AppsManager appsManager = new AppsManager(
								consumerService, tokenService);
						getBundleContext().registerService(
								AppsManager.class.getName(), appsManager, null);
						}catch (Exception e) {
							System.err.println(e);
						}

					}
				}, ConsumerService.class.getName(), TokenService.class
						.getName());
	}
	
	@Override
	public void start(BundleContext context) throws Exception {
		try{
			super.start(context);
		}catch (Exception e) {
			log.error("Apps Manager failed to start...",e);
		}
	}

	@Override
	protected String getName() {
		return "net.megx.apps-manager";
	}

}
