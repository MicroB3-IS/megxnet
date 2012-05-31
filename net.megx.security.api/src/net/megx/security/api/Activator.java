package net.megx.security.api;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import net.megx.security.auth.Role;
import net.megx.security.auth.services.ConsumerService;
import net.megx.security.auth.services.TokenService;
import net.megx.security.auth.services.UserService;
import net.megx.security.auth.services.WebResourcesService;
import net.megx.security.auth.services.db.BaseDBService;
import net.megx.security.auth.services.db.DBConsumerService;
import net.megx.security.auth.services.db.DBTokenService;
import net.megx.security.auth.services.db.DBUserService;
import net.megx.security.auth.services.db.DBWebResourcesService;

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
			System.out.println("LATEST-VERSION!");
		System.out.println(String.format("Config: %s", getConfig().toString(3)));
		System.out.println("Config resource path="+getString(getConfig(),    "myBatisConfigFile"));
		URL url = context.getBundle().getResource(getString(getConfig(), "myBatisConfigFile"));
		System.out.println("Config resource as:" + url);
		Reader reader = new InputStreamReader(url.openStream());
		 
		System.out.println("Read config resource > " + reader);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("config/my-batis-config.xml"), getDatabaseProperties());
		System.out.println("Build sql session factory > " + factory);
		registerBaseDBService(DBUserService.class, UserService.class, factory);
		registerBaseDBService(DBConsumerService.class, ConsumerService.class, factory);
		registerBaseDBService(DBTokenService.class, TokenService.class, factory);
		registerBaseDBService(DBWebResourcesService.class, WebResourcesService.class, factory);
		
		DBUserService us = new DBUserService();
		us.setSqlSessionFactory(factory);
		try{
		List<Role> roles = us.getAvailableRoles();
		if(roles == null){
			System.out.println("ROLES=NULL");
			return;
		}
		for(Role role: roles){
			System.out.println("ROLE: " + role.getLabel());
		}
		}catch (Exception e) {
			e.printStackTrace(System.out);
		}
		}catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	
	
	private void registerBaseDBService(Class<? extends BaseDBService> cls, Class<?> registerAs, SqlSessionFactory factory){
		try {
			BaseDBService dbService = cls.newInstance();
			dbService.setSqlSessionFactory(factory);
			System.out.println(String.format("Registering service instance %s of class (%s) as (%s)",dbService.toString(),cls.getName(), registerAs.getName()));
			RegUtils.reg(getBundleContext(), registerAs.getName(), dbService, null);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
