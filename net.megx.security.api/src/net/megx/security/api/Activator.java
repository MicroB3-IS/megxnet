package net.megx.security.api;

import java.util.Map;

import net.megx.security.auth.AuthenticationManager;
import net.megx.security.auth.impl.ExternalLoginHandlerImpl;
import net.megx.security.auth.impl.OAuth1AuthenticationHandlerImpl;
import net.megx.security.auth.impl.WebAuthenticationHandlerImpl;
import net.megx.security.auth.services.ConsumerService;
import net.megx.security.auth.services.TokenService;
import net.megx.security.auth.services.UserService;
import net.megx.security.auth.services.WebResourcesService;
import net.megx.security.auth.web.ExternalLoginHandler;
import net.megx.security.auth.web.OAuth1Handler;
import net.megx.security.auth.web.WebLoginHandler;
import net.megx.security.auth.web.impl.WebLoginAuthenticationManager;
import net.megx.security.crypto.KeySecretProvider;
import net.megx.security.crypto.impl.DefaultKeySecretProvider;
import net.megx.security.oauth.OAuthServices;
import net.megx.security.oauth.TokenServices;
import net.megx.security.oauth.impl.OAuth1Services;
import net.megx.security.oauth.impl.OAuthTokenServices;
import net.megx.security.utils.Cache;
import net.megx.security.utils.SimpleCache;
import net.megx.utils.OSGIUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRAppConfgEnabledActivator;
import org.chon.web.RegUtils;
import org.osgi.framework.BundleContext;

public class Activator extends JCRAppConfgEnabledActivator {
	private static final Log log = LogFactory.getLog(Activator.class);

	@Override
	protected String getName() {
		return "net.megx.security.api";
	}
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		try{
			registerServices(context);
		}catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.out);
		}

	}
	
	
	
	private void registerServices(final BundleContext context) throws Exception{
		
		OSGIUtils.requestServices(context, new OSGIUtils.OnServicesAvailable() {
			
			@Override
			public void servicesAvailable(Map<String, Object> services) {
				log.debug("Services available. Initializing...");
				try{
					UserService userService = (UserService)services.get(UserService.class.getName());
					ConsumerService consumerService = (ConsumerService)services.get(ConsumerService.class.getName());
					TokenService tokenService = (TokenService)services.get(TokenService.class.getName());
					WebResourcesService webResourcesService = (WebResourcesService)services.get(WebResourcesService.class.getName());
					
					AuthenticationManager authenticationManager = new WebLoginAuthenticationManager();
					RegUtils.reg(context, AuthenticationManager.class.getName(), 
							authenticationManager, null);
					if(log.isDebugEnabled())
						log.debug("Registered AuthenticationManager of class: "+ authenticationManager.getClass().getName());
					
					WebAuthenticationHandlerImpl webLoginHandler = new WebAuthenticationHandlerImpl(null);
					webLoginHandler.setUserService(userService);
					RegUtils.reg(context, WebLoginHandler.class.getName(), webLoginHandler, null);
					
					OAuth1Services oAuthServices = new OAuth1Services();
					oAuthServices.setConsumerService(consumerService);
					
					OAuthTokenServices tokenServices = new OAuthTokenServices();
					Cache cache = new SimpleCache();
					KeySecretProvider keySecretProvider = new DefaultKeySecretProvider();
					tokenServices.setCache(cache);
					tokenServices.setTokenService(tokenService);
					tokenServices.setKeySecretProvider(keySecretProvider);
					oAuthServices.setTokenServices(tokenServices);
					
					OAuth1AuthenticationHandlerImpl authenticationHandlerImpl = new OAuth1AuthenticationHandlerImpl(null);
					authenticationHandlerImpl.setTokenServices(tokenServices);
					
					oAuthServices.setoAuthHandler(authenticationHandlerImpl);
					
					ExternalLoginHandlerImpl externalLoginHandler = new ExternalLoginHandlerImpl(null);
					externalLoginHandler.setUserService(userService);
					
					RegUtils.reg(context, ExternalLoginHandler.class.getName(), externalLoginHandler, null);
					
					// register services
					RegUtils.reg(context, Cache.class.getName(), cache, null);
					RegUtils.reg(context, KeySecretProvider.class.getName(), keySecretProvider, null);
					RegUtils.reg(context, TokenServices.class.getName(), tokenServices, null);
					RegUtils.reg(context, OAuth1Handler.class.getName(), authenticationHandlerImpl, null);
					RegUtils.reg(context, OAuthServices.class.getName(), oAuthServices, null);
				}catch(Exception e){
					log.error("An error occured while initializng the security API.",e);
				}
				log.info("Security API initialized. All services exposed.");
			}
		}, UserService.class.getName(),
				ConsumerService.class.getName(),
				TokenService.class.getName(),
				WebResourcesService.class.getName());
	}
}
