package net.megx.storage.osgi;

import net.megx.storage.StorageSessionProvider;
import net.megx.storage.impl.BaseSessionProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;
import org.json.JSONObject;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

public class Activator extends ResTplConfiguredActivator {
	
	private Log log = LogFactory.getLog(getClass());
	
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		buildStorageSessionProvider(getJCRApp());
	}
	
	@Override
	protected void registerExtensions(JCRApplication app) {
		
	}

	@Override
	protected String getName() {
		return "net.megx.storage";
	}
	
	
	private void buildStorageSessionProvider(JCRApplication app) throws BundleException{
		log.debug("Building session provider...");
		if(getConfig() == null){
			log.error("The configuration is not available. Storage service will not start!");
			throw new BundleException("The bundle configuration is not available");
		}
		JSONObject spConfig = getConfig().optJSONObject("sessionProvider");
		if(spConfig == null){
			log.error("No configuration for the session provider!");
			throw new BundleException("No configuration for the session provider.");
		}
		String clazz = spConfig.optString("class");
		if(clazz == null){
			log.error("No class specified for the session privider.");
			throw new BundleException("No class specified for the session privider.");
		}
		try {
			log.debug("Loading storage session provider of class: " + clazz);
			Class<?> c = getBundleContext().getBundle().loadClass(clazz);
			if(!StorageSessionProvider.class.isAssignableFrom(c)){
				log.error(String.format("Class %s not a session provider!", clazz));
				throw new BundleException(String.format("Class %s not a session provider!", clazz));
			}
			
			@SuppressWarnings("unchecked")
			Class<StorageSessionProvider> sc = (Class<StorageSessionProvider>)c;
			
			StorageSessionProvider sp = sc.newInstance();
			log.debug("Instantiated StorageSessionProvider: " + sp);
			if(sp instanceof BaseSessionProvider){
				log.debug("StorageSessionProvider is BaseSessionProvider.");
				BaseSessionProvider bsp = (BaseSessionProvider)sp;
				bsp.setApplication(app);
				bsp.setBundleContext(getBundleContext());
				bsp.setConfig(getConfig());
				bsp.initialize();
			}else{
				log.debug("StorageSessionProvider is not a BaseStorageProvider");
			}
			log.debug("Registering in OSGI Context...");
			RegUtils.reg(getBundleContext(), StorageSessionProvider.class.getName(), sp, null);
			log.debug("StorageSessionProvider registered and published.");
		} catch (ClassNotFoundException e) {
			log.error("The class for StorageSessionProvider cannot be loaded: ", e);
			throw new BundleException("The class for StorageSessionProvider cannot be loaded.", BundleException.ACTIVATOR_ERROR, e);
		} catch (InstantiationException e) {
			log.error("Failed to instantiate the StorageSessionProvider: ",e);
			throw new BundleException("Failed to instantiate the StorageSessionProvider.", BundleException.ACTIVATOR_ERROR, e);
		} catch (IllegalAccessException e) {
			log.error("Cannot access the StorageSessionProvider class to create instance: ", e);
			throw new BundleException("Cannot access the StorageSessionProvider class to create instance.", BundleException.ACTIVATOR_ERROR, e);
		} catch (Exception e) {
			log.error("An error has occured while initializing the StorageSessionProvider.", e);
			throw new BundleException("An error has occured while initializing the StorageSessionProvider.", BundleException.ACTIVATOR_ERROR, e);
		}
	}
}
