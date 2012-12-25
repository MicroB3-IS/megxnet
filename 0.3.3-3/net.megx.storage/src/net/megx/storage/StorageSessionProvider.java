package net.megx.storage;

public interface StorageSessionProvider {
	public StorageSession openSession(Context context) throws StorageException;
}
