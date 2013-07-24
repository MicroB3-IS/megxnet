package net.megx.storage;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

public interface AccessMechanism {
	public boolean resourceExist(URI uri) throws StorageSecuirtyException;
	public InputStream readResource(URI uri) throws StorageSecuirtyException, ResourceAccessException;
	public OutputStream writeResource(URI uri) throws StorageSecuirtyException, ResourceAccessException;
	public URI create(URI uri) throws StorageSecuirtyException, ResourceAccessException;
	public void delete(URI uri) throws StorageSecuirtyException, ResourceAccessException;
	public URI move(URI uri, URI toURI) throws StorageSecuirtyException, ResourceAccessException;
	public URI copy(URI uri, URI toURI) throws StorageSecuirtyException, ResourceAccessException;
	public URI resolve(URI input);
	public URI createURI(String...parts) throws URISyntaxException;
	
	public Object readAttribute(URI resource, String attrName) throws StorageSecuirtyException, ResourceAccessException;
	public void saveAttribute(URI resource, String attrName, Object attrValue) throws StorageSecuirtyException, ResourceAccessException;
}
