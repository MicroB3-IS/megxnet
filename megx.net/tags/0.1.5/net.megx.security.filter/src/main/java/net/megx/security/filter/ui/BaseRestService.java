package net.megx.security.filter.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;

public class BaseRestService {
	protected Gson gson = new Gson();
	protected Log log = LogFactory.getLog(getClass());
	
	public String toJSON(Object object){
		return gson.toJson(object);
	}
	
	public <T> T fromJSON(String jsonSrc, Class<T> type){
		return gson.fromJson(jsonSrc, type);
	}
	
	public Result<Exception> handleException(Exception e){
		Result<Exception> result = new Result<Exception>();
		log.error("An error occured while processing: ", e);
		result.setError(true);
		result.setMessage(e.getMessage());
		result.setData(e);
		
		return result;
	}
}
