package net.megx.security.utils;

import java.util.List;

public interface Cache {
	public void storeObject(Object key, Object object);
	public Object getObject(Object key);
	public Object removeObject(Object key);
	public List<Object> getCached();
}
