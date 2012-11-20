package net.megx.security.filter.impl;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.megx.security.auth.SecurityException;
import net.megx.security.auth.web.WebUtils;
import net.megx.security.filter.ui.Result;

import org.json.JSONArray;

import com.google.gson.Gson;

public class RestServicesExceptionHandler extends BaseExceptionHandler{

	private String [] resourcesPatterns;
	private Gson gson = new Gson();
	
	@Override
	public boolean handleException(Exception e, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		if(resourcesPatterns != null){
			String path = WebUtils.getRequestPath(request, false);
			for(String pattern: resourcesPatterns){
				if(path.matches(pattern)){
					return doHandleException(e, request, response);
				}
			}
		}
		return true;
	}
	
	
	protected boolean doHandleException(Exception e, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		if(e instanceof SecurityException){
			if(!response.isCommitted()){
				SecurityException se = (SecurityException)e;
				response.setStatus(se.getResponseCode());
				Result<SecurityException> result = new Result<SecurityException>(true, se.getMessage(), "security-exception");
				//result.setData(se);
				response.getWriter().write(gson.toJson(result));
				return false;
			}
		}else{
			if(!response.isCommitted()){
				response.setStatus(500);
				Result<Object> result = new Result<Object>(true, e.getMessage(), "server-error");
				response.getWriter().write(gson.toJson(result));
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void init() {
		JSONArray pts = config.optJSONArray("patterns");
		if(pts != null){
			List<String> patterns = new LinkedList<String>();
			for(int i = 0; i < pts.length(); i++){
				String pattern = pts.optString(i);
				if(pattern != null && !"".equals(pattern.trim()) ){
					patterns.add(pattern.trim());
				}
			}
			if(patterns.size() > 0){
				resourcesPatterns = patterns.toArray(new String []{});
			}
		}
	}

}
