package net.megx.storage.impl;

import java.util.HashMap;
import java.util.Map;

import net.megx.storage.AccessMechanism;
import net.megx.storage.Context;
import net.megx.storage.StorageException;
import net.megx.storage.StorageSession;
import net.megx.storage.StorageSessionProvider;
import net.megx.storage.ams.FileAccess;

public class StorageSessionProviderImpl implements StorageSessionProvider{

	@Override
	public StorageSession openSession(Context context) throws StorageException {
		StorageService service = new StorageService();
		Map<String, String> hostConfig = new HashMap<String, String>();
		
		// put this in config - just for illustration here
		hostConfig.put("storage-root", "/home/pavle/storage");
		
		AccessMechanism am = new FileAccess(context, hostConfig);
		
		service.setDefaultAccessMechanism(am);
		service.addAccessMechanism("file", am);
		
		return service;
	}

}
