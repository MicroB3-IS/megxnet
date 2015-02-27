package net.megx.security.auth.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.chon.web.RegUtils;
import org.osgi.framework.BundleContext;

public class WebUtils {
	public static String getRequestPath(HttpServletRequest request, boolean withQuery) throws URISyntaxException{
		String requestedPath = request.getRequestURI().substring(request.getContextPath().length());
		
		
		
		//System.out.println("------------------------------------");
		//System.out.println("RequestURI: "+request.getRequestURI());
		//System.out.println("RequestURL: "+ request.getRequestURL().toString());
		//System.out.println("ContextPAth: "+request.getContextPath());
		//System.out.println("Query String: " + request.getQueryString());
		//System.out.println("------------------");
		//System.out.println("REQUESTED_PATH: " + requestedPath);
		//System.out.println("------------------------------------");
		Pattern pattern = Pattern.compile("^(/+)");
		Matcher matcher = pattern.matcher(requestedPath);
		requestedPath = matcher.replaceFirst("/");
		requestedPath =  new URI(requestedPath).normalize().getPath();
		if(withQuery && request.getQueryString() != null){
			requestedPath += "?" + request.getQueryString();
		}
		return requestedPath;
	}
	
	
	public static void registerFilter(BundleContext context, Filter filter,
			String pattern,
			String contextId,
			int serviceRanking,
			Map<String, String> initParameters) {
		Hashtable<String, String> properties = new Hashtable<String,String>();
		if(pattern !=null){
			properties.put("pattern", pattern);
		}
		if(contextId != null){
			properties.put("contextId", contextId);
		}
		
		properties.put("service.ranking", String.valueOf(serviceRanking));
		
		if(initParameters != null){
			for(Map.Entry<String, String> e: initParameters.entrySet()){
				if(e.getValue() != null){
					properties.put("init."+e.getKey(), e.getValue());
				}
			}
		}
		RegUtils.reg(context, Filter.class.getName(), filter, properties);
		System.out.println("Registered filter: "+filter);
	}
	
	
	public static void registerServlet(BundleContext context, Servlet servlet, String mapping, 
			String contextId, Map<String, String> initParams){
		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put("alias", mapping);
		if(contextId != null)
			props.put("contextId", contextId);
		if(initParams != null){
			for(Map.Entry<String, String> e: initParams.entrySet()){
				props.put("init."+e.getKey(), e.getValue());
			}
		}
		RegUtils.reg(context, Servlet.class.getName(), servlet, props);
	}
	
	public static void replaceSession(HttpServletRequest request){
		HttpSession session = request.getSession();
		Map<String, Object> sessionParams = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Enumeration<String> names = session.getAttributeNames();
		if(names != null){
			while(names.hasMoreElements()){
				String name = names.nextElement();
				sessionParams.put(name, session.getAttribute(name));
			}
		}
		session.invalidate();
		session = request.getSession();
		for(Map.Entry<String, Object> e: sessionParams.entrySet()){
			session.setAttribute(e.getKey(), e.getValue());
		}
	}
	
	
	public static String getFullContextURL(HttpServletRequest request){
		return request.getRequestURL().toString(); // FIXME: Is there a better way to do this?
	}
	
	public static String getAppURL(HttpServletRequest request) throws URISyntaxException{
		String requestPath = getRequestPath(request, false);
		String fullRequestUrl = getFullContextURL(request);
		return fullRequestUrl.substring(0, fullRequestUrl.length() - requestPath.length()) + "/";
	}
	
	public static String normalize(String path){
		try {
			return new URI(path).normalize().getPath();
		} catch (URISyntaxException e) {
			return null;
		}
	}
	
}
