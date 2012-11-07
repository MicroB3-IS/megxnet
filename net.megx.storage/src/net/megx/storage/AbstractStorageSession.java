package net.megx.storage;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class AbstractStorageSession implements StorageSession{

	protected static final String DEFAULT_MECHANISM = "default";
	
	private Map<String, AccessMechanism> accessMechanisms = new HashMap<String, AccessMechanism>();
	
	protected AccessMechanism getAccessMechanism(String forScheme) throws ResourceAccessException{
		AccessMechanism ac = accessMechanisms.get(forScheme);
		if(ac == null){
			ac = accessMechanisms.get(DEFAULT_MECHANISM);
		}
		if(ac == null){
			throw new ResourceAccessException(); // TODO: Invalid Protocol Exception - no mechanism available for the requested scheme
		}
		return ac;
	}
	
	
	public void addAccessMechanism(String scheme, AccessMechanism accessMechanism){
		accessMechanisms.put(scheme, accessMechanism);
	}
	
	public void setDefaultAccessMechanism(AccessMechanism accessMechanism){
		addAccessMechanism(DEFAULT_MECHANISM, accessMechanism);
	}
	
	protected StoredResource buildResource(AccessMechanism accessMechanism, URI resourceURI, URI resolvedURI){
		return new BaseStoredResource(accessMechanism, resourceURI, resolvedURI);
	}
	
	
	@Override
	public StoredResource lookup(URI uri) throws ResourceAccessException,
			StorageSecuirtyException {
		AccessMechanism accessMechanism = getAccessMechanism(uri.getScheme());
		URI translated = accessMechanism.resolve(uri);
		return buildResource(accessMechanism, uri, translated);
	}

	@Override
	public StoredResource create(URI uri) throws ResourceAccessException,
			StorageSecuirtyException {
		AccessMechanism accessMechanism = getAccessMechanism(uri.getScheme());
		if(accessMechanism.resourceExist(uri)){
			throw new ResourceAccessException("File already exists.");
		}
		URI translated = accessMechanism.create(uri);
		return buildResource(accessMechanism, uri, translated);
	}

	@Override
	public void delete(StoredResource resource) throws ResourceAccessException,
			StorageSecuirtyException {
		getAccessMechanism(resource.getURI().getScheme()).delete(resource.getURI());
	}

	@Override
	public StoredResource relocate(StoredResource resource, URI toURI)
			throws StorageException {
		String schemeFrom = resource.getURI().getScheme();
		String schemeTo = toURI.getScheme();
		if(schemeFrom.equals(schemeTo)){
			AccessMechanism accessMechanism = getAccessMechanism(schemeFrom);
			URI reloactedAccessURI = accessMechanism.move(resource.getURI(), toURI);
			return buildResource(accessMechanism, toURI, reloactedAccessURI);
		}else{
			throw new StorageException();// FIXME: Not implemented moving between different storage systems
		}
	}

	@Override
	public StoredResource copy(StoredResource resource, URI toURI)
			throws StorageException {
		String schemeFrom = resource.getURI().getScheme();
		String schemeTo = toURI.getScheme();
		if(schemeFrom.equals(schemeTo)){
			AccessMechanism accessMechanism = getAccessMechanism(schemeFrom);
			URI reloactedAccessURI = accessMechanism.copy(resource.getURI(), toURI);
			return buildResource(accessMechanism, toURI, reloactedAccessURI);
		}else{
			throw new StorageException();// FIXME: Not implemented moving between different storage systems
		}
	}

	@Override
	public void close() throws StorageException {
		// we might need some cleanup here , but not for now :)
	}


	@Override
	public boolean exists(URI uri) throws ResourceAccessException,
			StorageSecuirtyException {
		return getAccessMechanism(uri.getScheme()).resourceExist(uri);
	}

}
