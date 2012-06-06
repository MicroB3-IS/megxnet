package net.megx.megdb;

import java.io.IOException;
import java.util.Properties;

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
	public void start(BundleContext context) throws Exception {
		//super.start will read json config
		super.start(context);
		JSONObject cfg = getConfig();
		log.debug("Started net.megx.megdb; json config: " + cfg.toString(3));
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

	@Override
	protected String getName() {
		return "net.megx.megdb";
	}
	
	
	protected SqlSessionFactory buildSQLSessionFactory(JSONObject config) throws JSONException, IOException{
		log.debug("Start building SQLSessionFactory...");
		SqlSessionFactory factory = null;
		String configFile = config.getString("myBatisConfigFile");
		log.debug("MyBatis config file location: " + configFile);
		factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader(configFile), getDatabaseProperties(config));
		log.info("SQLSessionFactory instance: " + factory);
		return factory;
	}
	
	private Properties getDatabaseProperties(JSONObject config){
		Properties properties = new Properties();
		
		JSONObject dbConfig = config.optJSONObject("dbConfig");
		
		String [] keys = JSONObject.getNames(dbConfig);
		
		if(keys != null){
			for(String key: keys){
				properties.put(key, dbConfig.optString(key));
			}
		}
		
		return properties;
	}
	
	protected Object registerBaseDBService(Class<? extends BaseMegdbService> cls, Class<?> registerAs, SqlSessionFactory factory){
		try {
			BaseMegdbService dbService = cls.newInstance();
			dbService.setSqlSessionFactory(factory);
			log.debug((String.format("Registering service instance %s of class (%s) as (%s)",dbService.toString(),cls.getName(), registerAs.getName())));
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
}
