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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.BundleContext;

public class Activator extends JCRAppConfgEnabledActivator {

	private static final Log log = LogFactory.getLog(Activator.class);
	private SqlSessionFactory factory = null;

	@Override
	public void start(BundleContext context) throws Exception {
		// super.start will read json config
		super.start(context);
		JSONObject cfg = getConfig();
		log.debug("Megdb Services starting up...");
		try {
			factory = buildSQLSessionFactory(cfg);
			buildDBServices(cfg.getJSONArray("dbServices"), factory);
			log.info("Megdb Services layer bundle startup success.");
		} catch (Exception e) {
			log.error("Megdb Services failed to startup successfuly: ", e);
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

	@Override
	protected String getName() {
		return "net.megx.megdb";
	}

	protected SqlSessionFactory buildSQLSessionFactory(JSONObject config)
			throws JSONException, IOException {
		log.debug("Start building SQLSessionFactory...");
		SqlSessionFactory factory = null;
		String configFile = config.getString("myBatisConfigFile");
		log.debug("MyBatis config file location: " + configFile);
		factory = new SqlSessionFactoryBuilder().build(
				Resources.getResourceAsReader(configFile),
				getDatabaseProperties(config));
		log.info("SQLSessionFactory instance: " + factory);
		return factory;
	}

	private Properties getDatabaseProperties(JSONObject config) {
		Properties properties = new Properties();

		JSONObject dbConfig = config.optJSONObject("dbConfig");

		String[] keys = JSONObject.getNames(dbConfig);

		if (keys != null) {
			for (String key : keys) {
				properties.put(key, dbConfig.optString(key));
			}
		}

		return properties;
	}

	protected BaseMegdbService registerBaseDBService(
			Class<? extends BaseMegdbService> cls, Class<?> registerAs,
			SqlSessionFactory factory) {
		try {
			BaseMegdbService dbService = cls.newInstance();
			dbService.setSqlSessionFactory(factory);
			log.debug((String.format(
					"Registering service instance %s of class (%s) as (%s)",
					dbService.toString(), cls.getName(), registerAs.getName())));
			RegUtils.reg(getBundleContext(), registerAs.getName(), dbService,
					null);
			return dbService;
		} catch (InstantiationException e) {
			log.warn(e);
		} catch (IllegalAccessException e) {
			log.warn(e);
		}
		return null;
	}

	protected void buildDBServices(JSONArray servicesConfig,
			SqlSessionFactory sessionFactory) throws JSONException,
			ClassNotFoundException {
		log.debug(" -----------------------------------------");
		log.debug(" Building DB Services: ");
		for (int i = 0; i < servicesConfig.length(); i++) {
			JSONObject config = servicesConfig.getJSONObject(i);
			log.debug(" > Building Service: " + config.toString());
			BaseMegdbService service = buildBaseDBService(config,
					sessionFactory);
			if (service == null) {
				log.warn("Failed to build service.");
			} else {
				log.debug(" > Serivice build and ready: " + service);
			}
		}
		log.debug(" -----------------------------------------");
	}

	@SuppressWarnings("unchecked")
	protected BaseMegdbService buildBaseDBService(JSONObject config,
			SqlSessionFactory sessionFactory) throws JSONException,
			ClassNotFoundException {
		String serviceName = config.getString("name");
		String implementingClass = config.getString("class");
		return registerBaseDBService(
				(Class<? extends BaseMegdbService>) loadClass(implementingClass),
				loadClass(serviceName), sessionFactory);
	}

	protected Class<?> loadClass(String className)
			throws ClassNotFoundException {
		return Activator.class.getClassLoader().loadClass(className);
	}
}
