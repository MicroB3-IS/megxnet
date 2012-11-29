package net.megx.storage;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public interface StoredResource {
	public InputStream read() throws StorageSecuirtyException, ResourceAccessException;
	public OutputStream write()  throws StorageSecuirtyException, ResourceAccessException;
	public URI getURI();
	public URI getAccessURI();
	public ResourceMeta getResourceMeta();
}
