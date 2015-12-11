package net.megx.megdb.ws.data.mappers;

import org.apache.ibatis.annotations.Param;

public interface WSDataMapper {
	public int executeDynamicUpdateStatement(
			@Param("schemaName") String schemaName,
			@Param("tableName") String tableName,
			@Param("fieldsToUpdate") String fieldsToUpdate,
			@Param("whereStatement") String whereStatement);
}
