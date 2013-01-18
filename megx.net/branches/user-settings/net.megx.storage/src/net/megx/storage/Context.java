package net.megx.storage;

public interface Context {
	public Object getUserPrincipal();
	public Object getProperty(String name);
}
