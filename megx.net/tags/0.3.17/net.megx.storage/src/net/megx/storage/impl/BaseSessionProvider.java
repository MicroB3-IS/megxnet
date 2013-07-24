package net.megx.storage.impl;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.chon.cms.core.JCRApplication;
import org.json.JSONObject;
import org.osgi.framework.BundleContext;

import net.megx.storage.AccessMechanism;
import net.megx.storage.Context;
import net.megx.storage.StorageSessionProvider;

public abstract class BaseSessionProvider implements StorageSessionProvider{
	protected JCRApplication application;
	protected BundleContext bundleContext;
	protected JSONObject config;
	
	protected Map<String, AccessMechanismDef> accessMechanisms = new HashMap<String, BaseSessionProvider.AccessMechanismDef>();
	protected String defaultAccessMechanism = null;
	
	public void setApplication(JCRApplication application) {
		this.application = application;
	}
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}
	public void setConfig(JSONObject config) {
		this.config = config;
	}
	
	
	
	public abstract void initialize() throws Exception;
	
	
	
	protected AccessMechanismDef createAccessMechanismDef(JSONObject config) throws Exception{
		String className = config.optString("class");
		if(className == null){
			throw new Exception("Unspecified class fro access mechanism");
		}
		Class<?> cls = bundleContext.getBundle().loadClass(className);
		if(!AccessMechanism.class.isAssignableFrom(cls)){
			throw new Exception("The class specified is not an Access Mechanism");
		}
		Class<? extends AccessMechanism> amCls = (Class<AccessMechanism>)cls;
		AccessMechanismDef amd = new AccessMechanismDef();
		amd.amClass = amCls;
		
		
		String scheme = config.optString("scheme");
		if(scheme == null){
			throw new Exception("Must specify the scheme for access.");
		}
		amd.scheme = scheme;
		
		String [] names = JSONObject.getNames(config);
		Map<String, String> cfg= new HashMap<String, String>();
		for(String name: names){
			cfg.put(name, config.opt(name).toString());
		}
		amd.config = cfg;
		return amd;
	}
	
	protected void buildAccessMechanismsDefinitions()throws Exception{
		JSONObject ams = config.optJSONObject("accessMechanisms");
		if(ams == null){
			throw new Exception("No access mechanisms defined!");
		}
		String [] amNames = JSONObject.getNames(ams);
		
		defaultAccessMechanism = config.optString("defaultAccessMechanism");
		
		for(String amName: amNames){
			Object confObj = ams.get(amName);
			JSONObject config = null;
			if( !(confObj instanceof JSONObject) ){
				config = new JSONObject();
			}else{
				config = (JSONObject)confObj;
			}
			accessMechanisms.put(amName, createAccessMechanismDef(config));
		}
		if(defaultAccessMechanism == null){
			defaultAccessMechanism = "default";
		}
		AccessMechanismDef def = accessMechanisms.get(defaultAccessMechanism);
		if(def == null){
			throw new Exception("No default Access Mechanism specified");
		}
	}
	
	protected class AccessMechanismDef{
		public Class<? extends AccessMechanism> amClass;
		public Map<String, String> config;
		public String scheme;
		
		public AccessMechanism getNewInstance(Context context) throws Exception{
			AccessMechanism am = null;
			Constructor<? extends AccessMechanism> amc = null;
			try{
				amc = amClass.getConstructor(Context.class, Map.class);
				am = amc.newInstance(context, config);
			}catch (NoSuchMethodException e) {
				try{
					amc = amClass.getConstructor(Context.class);
					am = amc.newInstance(context);
				}catch (NoSuchMethodException ee) {
					// try the default constructor
					am = amClass.newInstance();
				}
			}
			return am;
		}
	}
}
