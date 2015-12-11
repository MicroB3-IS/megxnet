package net.megx.megdb.ws.data.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.megx.megdb.BaseMegdbService;
import net.megx.megdb.ws.data.WSDataService;
import net.megx.model.ws.data.WSDataUpdateStatement;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

public class PostgreSQLWSDataServiceImpl extends BaseMegdbService implements WSDataService {
	
	private Log log = LogFactory.getLog(getClass());
	
	@Override
	public int savetableCellInput(String schemaName, String tableName, String updatePayload) throws Throwable {
		JSONObject payload = new JSONObject(updatePayload.trim());
		WSDataUpdateStatement statement = new WSDataUpdateStatement();
		
		if(log.isDebugEnabled()){
			log.debug(String.format("Updating table cells for table %s.%s", schemaName, tableName));
		}
		
		statement.setSchemaName(schemaName);
		statement.setTableName(tableName);
		statement.setFieldsToUpdate(fetchColumnUpdateStatement(payload));
		statement.setWhereStatement(fetchPayloadId(payload));
		//statement.setParamsMap(fetchParamsMap(payload));
		
		return executeDynamicUpdate(statement);
	}
	
	private String fetchColumnUpdateStatement(JSONObject payload) throws Throwable{
		Iterator<?> keys = payload.keys();
		List<String> updateStatements = new ArrayList<String>();

		while( keys.hasNext() ) {
		    String key = (String)keys.next();
		    if(!key.equalsIgnoreCase("idCol") && !key.equalsIgnoreCase("idVal")){
		    	updateStatements.add(String.format("%s = '%s'", key, payload.get(key)));
		    }
		}
		
		return StringUtils.join(updateStatements, ',');
	}
	
	private String fetchPayloadId(JSONObject payload) throws Throwable {
		return String.format("%s = '%s'", payload.get("idCol"), payload.get("idVal"));
	}
	
	private Map<String, Object> fetchParamsMap(JSONObject payload) throws Throwable {
		Map<String, Object> result = new HashMap<String, Object>();
		Iterator<?> keys = payload.keys();

		while( keys.hasNext() ) {
		    String key = (String)keys.next();
		    if(!key.equalsIgnoreCase("idCol") && !key.equalsIgnoreCase("idVal")){
		    	result.put(key, payload.get(key));
		    }
		}
		
		return result;
	}
}
