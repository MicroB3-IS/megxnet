package net.megx.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseStoredResource implements StoredResource{

	private AccessMechanism mechanism;
	private URI resourceURI;
	
	
	private InputStream inputStream;
	private OutputStream outputStream;
	private ResourceMeta meta;
	
	private static Log log = LogFactory.getLog(BaseStoredResource.class);
	
	
	protected BaseStoredResource(AccessMechanism mechanism, URI resourceURI) {
		this.mechanism = mechanism;
		this.resourceURI = resourceURI;
	}
	
	@Override
	public InputStream read()  throws StorageSecuirtyException, ResourceAccessException {
		if(inputStream == null){
			inputStream = openInputStream();
		}
		return inputStream;
	}

	protected InputStream openInputStream() throws StorageSecuirtyException, ResourceAccessException {
		return mechanism.readResource(getURI());
	}
	
	@Override
	public OutputStream write()  throws StorageSecuirtyException, ResourceAccessException {
		if(outputStream == null){
			outputStream = openOutputStream();
		}
		return outputStream;
	}

	protected OutputStream openOutputStream() throws StorageSecuirtyException, ResourceAccessException{
		return mechanism.writeResource(getURI());
	}
	
	
	@Override
	public URI getURI() {
		return resourceURI;
	}

	@Override
	public ResourceMeta getResourceMeta() {
		if(meta == null){
			meta = createResourceMeta();
		}
		return meta;
	}

	protected ResourceMeta createResourceMeta(){
		return new StoredResourceMeta();
	}
	
	
	@Override
	public URI getAccessURI() {
		return mechanism.resolve(getURI());
	}
	
	
	public void dispose() {
		log.debug("Disposing " + this);
		if(inputStream != null){
			try {
				inputStream.close();
			} catch (IOException e) {
				log.warn("Error while closing input stream to resource.", e);
			}
		}
		if(outputStream != null){
			try {
				outputStream.close();
			} catch (IOException e) {
				log.warn("Error while closing output stream to resource.", e);
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		dispose();
	}
	
	
	@Override
	public String toString() {
		return super.toString() + " {" + resourceURI + "}";
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
