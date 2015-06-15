package net.megx.security.filter.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import net.megx.security.auth.SecurityException;
import net.megx.security.auth.web.WebUtils;

public class WebDavExceptionHandler extends BaseExceptionHandler{

	private String [] patterns;
	
	@Override
	public boolean handleException(Exception e, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		try {
			if(!canHandle(e, request)){
				return true;
			}
		} catch (URISyntaxException e1) {
			throw new ServletException(e1);
		}
		if(e instanceof SecurityException){
			SecurityException wde = (SecurityException)e;
			if(!response.isCommitted()){
				response.setStatus(wde.getResponseCode());
				if(wde.getResponseCode() == 401){
					response.addHeader("WWW-Authenticate", "Basic realm=\"WebDAV Workspaces\"");
				}
				String message = wde.getMessage();
				if(message == null)
					message = fromHttpCode(wde.getResponseCode());
				if(message == null)
					message = "WebDAV Error: " + wde.getResponseCode();
				response.getWriter().write(message);
				response.getWriter().flush();
				return false;
			}else{
				throw new ServletException(e);
			}
		}
		return true;
	}

	@Override
	public void init() {
		JSONArray patternsArr = config.optJSONArray("patterns");
		if(patternsArr != null ){
			List<String> patternsList = new LinkedList<String>();
			for(int i = 0; i < patternsArr.length(); i++){
				String p = patternsArr.optString(i);
				if(p != null){
					patternsList.add(p);
				}
			}
			patterns = patternsList.toArray(new String []{});
		}
	}
	
	protected boolean canHandle(Exception e, HttpServletRequest request) throws URISyntaxException{
		if(patterns == null)
			return false;
		String path = WebUtils.getRequestPath(request, false);
		for(String pattern: patterns){
			if(path.matches(pattern))
				return true;
		}
		return false;
	}
}
