package net.megx.storage;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class StorageContext implements Context{

	private Object principal;
	private Map<String, Object> properties;
	
	
	
	public StorageContext(Object principal, Map<String, Object> properties) {
		this.principal = principal;
		this.properties = properties;
	}

	@Override
	public Object getUserPrincipal() {
		return principal;
	}

	@Override
	public Object getProperty(String name) {
		return properties.get(name);
	}
	
	
	public static Context fromJSONConfiguration(JSONObject config) throws JSONException{
		String userPincipal = config.getString("userPrincipal");
		Map<String, Object> contextProps = new HashMap<String, Object>();
		JSONObject properties = config.getJSONObject("properties");
		for(String name: JSONObject.getNames(properties)){
			contextProps.put(name, properties.get(name));
		}
		return new StorageContext(userPincipal, contextProps);
	}
	
}
