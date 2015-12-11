package net.megx.model.ws.data;

import java.util.HashMap;
import java.util.Map;

public class WSDataUpdateStatement {
	
	private String schemaName;
	
	private String tableName;
	
	private String fieldsToUpdate;
	
	private String whereStatement;
	
	private Map<String, Object> paramsMap;

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFieldsToUpdate() {
		return fieldsToUpdate;
	}

	public void setFieldsToUpdate(String fieldsToUpdate) {
		this.fieldsToUpdate = fieldsToUpdate;
	}

	public String getWhereStatement() {
		return whereStatement;
	}

	public void setWhereStatement(String whereStatement) {
		this.whereStatement = whereStatement;
	}

	public Map<String, Object> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, Object> paramsMap) {
		this.paramsMap = paramsMap;
	}
	
	public Map<String, String> fetchUpdateStatementArguments(){
		Map<String, String> result = new HashMap<String, String>();
		
		result.put("schemaName", this.schemaName);
		result.put("tableName", this.tableName);
		result.put("fieldsToUpdate", this.fieldsToUpdate);
		result.put("whereStatement", this.whereStatement);
		
		return result;
	}

	@Override
	public String toString() {
		return "WSDataUpdateStatement [schemaName=" + schemaName
				+ ", tableName=" + tableName + ", fieldsToUpdate="
				+ fieldsToUpdate + ", whereStatement=" + whereStatement + "]";
	}
}
