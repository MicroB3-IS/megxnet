package net.megx.storage;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Date;

public class BaseStoredResource implements StoredResource{

	private AccessMechanism mechanism;
	private URI resourceURI;
	
	protected BaseStoredResource(AccessMechanism mechanism, URI resourceURI) {
		this.mechanism = mechanism;
		this.resourceURI = resourceURI;
	}
	
	@Override
	public InputStream read()  throws StorageSecuirtyException, ResourceAccessException {
		return mechanism.readResource(getURI());
	}

	@Override
	public OutputStream write()  throws StorageSecuirtyException, ResourceAccessException {
		return mechanism.writeResource(getURI());
	}

	@Override
	public URI getURI() {
		return resourceURI;
	}

	@Override
	public ResourceMeta getResourceMeta() {
		return new StoredResourceMeta();
	}

	@Override
	public URI getAccessURI() {
		return mechanism.resolve(getURI());
	}

	protected class StoredResourceMeta implements ResourceMeta{

		@Override
		public Date getDateCreated()  throws StorageSecuirtyException, ResourceAccessException{
			Object dc = mechanism.readAttribute(getURI(), "date-created");
			if(dc != null && dc instanceof Date){
				return (Date) dc;
			}
			return null;
		}

		@Override
		public Date getDateModified()  throws StorageSecuirtyException, ResourceAccessException{
			Object dc = mechanism.readAttribute(getURI(), "date-modified");
			if(dc != null && dc instanceof Date){
				return (Date) dc;
			}
			return null;
		}

		@Override
		public long getSize()  throws StorageSecuirtyException, ResourceAccessException{
			Object dc = mechanism.readAttribute(getURI(), "size");
			if(dc != null){
				if(dc instanceof Long){
					return (Long) dc;
				}else if( dc instanceof Integer){
					int dci = (Integer)dc;
					return (long) dci;
				}
			}
			return 0;
		}

		@Override
		public Object getAttr(String meta)  throws StorageSecuirtyException, ResourceAccessException{
			return mechanism.readAttribute(getURI(), meta);
		}

		@Override
		public void setAttr(String name, Object value)  throws StorageSecuirtyException, ResourceAccessException{
			mechanism.saveAttribute(getURI(), name, value);
		}
		
	}
}
