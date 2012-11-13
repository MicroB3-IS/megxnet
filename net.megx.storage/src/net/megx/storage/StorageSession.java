package net.megx.storage;

import java.net.URI;
import java.net.URISyntaxException;

public interface StorageSession {
	public StoredResource lookup(URI uri) throws ResourceAccessException , StorageSecuirtyException;
	public StoredResource create(URI uri) throws ResourceAccessException, StorageSecuirtyException;
	public void delete(StoredResource resource) throws ResourceAccessException, StorageSecuirtyException;
	
	public StoredResource relocate(StoredResource resource, URI toURI) throws StorageException; // equals to move
	public StoredResource copy(StoredResource resource, URI toURI) throws StorageException;
	
	public boolean exists(URI uri) throws ResourceAccessException , StorageSecuirtyException;
	public URI  createURI(String preferedProvider, String...parts) throws StorageException,URISyntaxException;
	
	public void close() throws StorageException;
	
}
