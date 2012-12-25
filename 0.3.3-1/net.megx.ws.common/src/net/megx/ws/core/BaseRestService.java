package net.megx.ws.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	
	public Result<Map<String, Object>> handleException(Exception e){
		log.error("An error occured while processing: ", e);
		return exceptionToResult(e, true);
	}
	
	public static Result<Map<String, Object>> exceptionToResult(Exception e, boolean debugging){
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		
		result.setError(true);
		result.setMessage(e.getMessage());
		result.setData(transformException(e, debugging));
		
		return result;
	}
	
	
	public static Map<String, Object> transformException(Exception e, boolean debugging){
		Map<String, Object> result = new HashMap<String, Object>();
		
		_mapException(result, e, new HashSet<Throwable>(), debugging);
		
		return result;
	}
	
	private static void _mapException(Map<String, Object> map, Throwable e, Set<Throwable> mapped, boolean debugging){
		if(mapped.contains(e)){
			return;
		}
		map.put("message", e.getMessage());
		if(debugging){
			StackTraceElement [] ses = e.getStackTrace();
			if(ses != null && ses.length > 0){
				String [] stackTrace = new String [ses.length];
				for(int i = 0; i < ses.length; i++){
					StackTraceElement el = ses[i];
					String line = String.format("%s#%s (%s, line %d)",el.getClassName(), el.getMethodName(), el.getFileName(), el.getLineNumber());
					stackTrace[i] = line;
				}
				map.put("stackTrace", stackTrace);
			}
			mapped.add(e);
		}
		if(e.getCause() != null){
			Map<String, Object> cause = new HashMap<String, Object>();
			_mapException(cause, e.getCause(), mapped, debugging);
			map.put("cause", cause);
		}
	}
	
}
