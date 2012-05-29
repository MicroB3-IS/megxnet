package net.megx.security.auth;

public interface Cache {
	public void storeObject(Object key, Object object);
	public Object getObject(Object key);
	public Object removeObject(Object key);
}
