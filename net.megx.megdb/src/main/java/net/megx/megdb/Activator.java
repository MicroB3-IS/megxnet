package net.megx.megdb;

import java.io.IOException;
import java.util.Properties;

import net.megx.utils.PasswordHash;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.chon.cms.core.JCRAppConfgEnabledActivator;
import org.chon.cms.core.JCRApplication;
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
		Class<?> pgDriver = Class.forName("org.postgresql.Driver");
		if(pgDriver == null) {
			throw new ClassNotFoundException("org.postgresql.Driver not found!!!");
		}
		// super.start calls initialization from JCRAppConfgEnabledActivator
		super.start(context);
	}
	

	@Override
	protected void onAppAdded(BundleContext context, JCRApplication app) {
		//must call super here to copyNonExistingConfigsToDistConfigDir
		super.onAppAdded(context, app);
		
		// config available only when JCRApplication available
		// this method will be called when JCRApplication is available
		JSONObject cfg = getConfig();
		log.debug("Megdb Services starting.");
		try {
			factory = buildSQLSessionFactory(cfg);
			buildDBServices(cfg.getJSONArray("dbServices"), factory);

			setupPasswordHash(cfg);

			log.info("Megdb Services start success.");
		} catch (Exception e) {
			log.error("Megdb Services failed to startup: " + e.getMessage(), e);
			//throw e;
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

	protected SqlSessionFactory buildSQLSessionFactory(JSONObject chonConfig)
			throws JSONException, IOException {
		log.debug("Start building SQLSessionFactory...");
		SqlSessionFactory factory = null;
		String myBatisConfigFile = chonConfig.getString("myBatisConfigFile");
		log.debug("MyBatis config file location: " + myBatisConfigFile);
		factory = new SqlSessionFactoryBuilder().build(
				Resources.getResourceAsReader(myBatisConfigFile),
				getDatabaseProperties(chonConfig));
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
			SqlSessionFactory factory ) {
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
			log.error(e);
		} catch (IllegalAccessException e) {
			log.error(e);
		}
		return null;
	}

	protected void buildDBServices(JSONArray servicesConfig,
			SqlSessionFactory sessionFactory) throws JSONException,
			ClassNotFoundException {
		log.debug(" Building DB Services: ");
		for (int i = 0; i < servicesConfig.length(); i++) {
			JSONObject config = servicesConfig.getJSONObject(i);
			log.debug(" > Building Service: " + config.toString());
			BaseMegdbService service = buildBaseDBService(config,
					sessionFactory);
			if (service == null) {
				log.error("Failed to build service.");
			} else {
				log.debug(" > Service built and ready: " + service);
			}
		}
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

	protected void setupPasswordHash(JSONObject config){
		JSONObject passwordHashing = config.optJSONObject("passwordHashing");
		if(passwordHashing != null){
			int saltBytes = config.optInt("saltBytes", PasswordHash.SALT_BYTES);
			int hashBytes = config.optInt("hashBytes", PasswordHash.HASH_BYTES);
			int iterations = config.optInt("PBKDF2iterations", PasswordHash.PBKDF2_ITERATIONS);
			PasswordHash.SALT_BYTES = saltBytes;
			PasswordHash.HASH_BYTES = hashBytes;
			PasswordHash.PBKDF2_ITERATIONS = iterations;
			if(log.isDebugEnabled())
				log.debug(String.format("Password Hashing Options: saltBytes=%d, hashBytes=%d, iterations=%d", saltBytes, hashBytes, iterations));
		}else{
			log.debug("Password Hashing Options are set to default.");
		}
	}
}
