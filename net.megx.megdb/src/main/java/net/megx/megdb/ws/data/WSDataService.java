package net.megx.megdb.ws.data;

public interface WSDataService {
	public int savetableCellInput(String schemaName, String tableName, String updatePayload) throws Throwable;
}
