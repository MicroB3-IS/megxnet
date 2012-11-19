package net.megx.storage.impl;

import java.util.HashMap;
import java.util.Map;

import net.megx.storage.AccessMechanism;
import net.megx.storage.Context;
import net.megx.storage.StorageException;
import net.megx.storage.StorageSession;

public class StorageSessionProviderImpl extends BaseSessionProvider{

	@Override
	public StorageSession openSession(Context context) throws StorageException {
		StorageService service = new StorageService();
		
		Map<String, AccessMechanism> mechanisms = new HashMap<String, AccessMechanism>();
		
		for(Map.Entry<String, AccessMechanismDef> e: accessMechanisms.entrySet()){
			try {
				AccessMechanism am =  e.getValue().getNewInstance(context);
				service.addAccessMechanism(e.getValue().scheme,am);
				mechanisms.put(e.getKey(), am);
			} catch (Exception e1) {
				throw new StorageException(e1);
			}
		}
		service.setDefaultAccessMechanism(mechanisms.get(defaultAccessMechanism));
		return service;
	}

	@Override
	public void initialize() throws Exception {
		buildAccessMechanismsDefinitions();
	}

}
