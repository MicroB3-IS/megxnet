package net.megx.storage;

import java.util.Date;

public interface ResourceMeta {
	public Date getDateCreated() throws StorageSecuirtyException, ResourceAccessException;
	public Date getDateModified() throws StorageSecuirtyException, ResourceAccessException;
	public long getSize() throws StorageSecuirtyException, ResourceAccessException;
	public Object getAttr(String meta) throws StorageSecuirtyException, ResourceAccessException;
	public void setAttr(String name, Object value) throws StorageSecuirtyException, ResourceAccessException;
}
