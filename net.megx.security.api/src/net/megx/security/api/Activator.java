package net.megx.security.api;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Properties;

import net.megx.security.auth.AuthenticationManager;
import net.megx.security.auth.impl.OAuth1AuthenticationHandlerImpl;
import net.megx.security.auth.impl.WebAuthenticationHandlerImpl;
import net.megx.security.auth.services.ConsumerService;
import net.megx.security.auth.services.TokenService;
import net.megx.security.auth.services.UserService;
import net.megx.security.auth.services.WebResourcesService;
import net.megx.security.auth.services.db.BaseDBService;
import net.megx.security.auth.services.db.DBConsumerService;
import net.megx.security.auth.services.db.DBTokenService;
import net.megx.security.auth.services.db.DBUserService;
import net.megx.security.auth.services.db.DBWebResourcesService;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.chon.cms.core.JCRAppConfgEnabledActivator;
import org.chon.web.RegUtils;
import org.json.JSONException;
import org.json.JSONObject;
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
		System.out.println("Initializing...");
		try{
			registerServices(context);
		}catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.out);
		}

	}
	
	
	
	private void registerServices(BundleContext context) throws Exception{
		try{
			System.out.println(String.format("Config: %s", getConfig().toString(3)));
			System.out.println("Config resource path="+getString(getConfig(),    "myBatisConfigFile"));
			URL url = context.getBundle().getResource(getString(getConfig(), "myBatisConfigFile"));
			System.out.println("Config resource as:" + url);
			Reader reader = new InputStreamReader(url.openStream());
			 
			System.out.println("Read config resource > " + reader);
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("config/my-batis-config.xml"), getDatabaseProperties());
			System.out.println("Build sql session factory > " + factory);
			UserService userService = (UserService)registerBaseDBService(DBUserService.class, UserService.class, factory);
			ConsumerService consumerService = (ConsumerService)registerBaseDBService(DBConsumerService.class, ConsumerService.class, factory);
			TokenService tokenService = (TokenService)registerBaseDBService(DBTokenService.class, TokenService.class, factory);
			WebResourcesService webResourcesService = (WebResourcesService)registerBaseDBService(DBWebResourcesService.class, WebResourcesService.class, factory);
			
			AuthenticationManager authenticationManager = new WebLoginAuthenticationManager();
			RegUtils.reg(context, AuthenticationManager.class.getName(), 
					authenticationManager, null);
			System.out.println("Registered AuthenticationManager of class: "+ authenticationManager.getClass().getName());
			
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
			
			
			
			// register services
			RegUtils.reg(context, Cache.class.getName(), cache, null);
			RegUtils.reg(context, KeySecretProvider.class.getName(), keySecretProvider, null);
			RegUtils.reg(context, TokenServices.class.getName(), tokenServices, null);
			RegUtils.reg(context, OAuth1Handler.class.getName(), authenticationHandlerImpl, null);
			RegUtils.reg(context, OAuthServices.class.getName(), oAuthServices, null);
			
		}catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	
	
	private Object registerBaseDBService(Class<? extends BaseDBService> cls, Class<?> registerAs, SqlSessionFactory factory){
		try {
			BaseDBService dbService = cls.newInstance();
			dbService.setSqlSessionFactory(factory);
			System.out.println(String.format("Registering service instance %s of class (%s) as (%s)",dbService.toString(),cls.getName(), registerAs.getName()));
			RegUtils.reg(getBundleContext(), registerAs.getName(), dbService, null);
			return dbService;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private Properties getDatabaseProperties(){
		Properties properties = new Properties();
		
		JSONObject config = getConfig();
		JSONObject dbConfig = get(config, "dbConfig");
		
		String [] keys = JSONObject.getNames(dbConfig);
		System.out.println("dbConfig: " + dbConfig);
		System.out.println("names: " + keys);
		if(keys != null){
			for(String key: keys){
				properties.put(key, getString(dbConfig, key));
			}
		}
		System.out.println("Properties: " + properties);
		return properties;
	}
	
	private JSONObject get(JSONObject obj, String key){
		try {
			return obj.getJSONObject(key);
		} catch (JSONException e) {
			return new JSONObject();
		}
	}
	
	private String getString(JSONObject obj, String key){
		try {
			return obj.getString(key);
		} catch (JSONException e) {
			return "";
		}
	}
}
