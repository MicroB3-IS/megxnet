package net.megx.storage;

import java.util.Map;

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

}
