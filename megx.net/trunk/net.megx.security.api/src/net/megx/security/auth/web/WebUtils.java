package net.megx.security.auth.web;

import java.util.Hashtable;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

import org.chon.web.RegUtils;
import org.osgi.framework.BundleContext;

public class WebUtils {
	public static String getRequestPath(HttpServletRequest request, boolean withQuery){
		String requestedPath = request.getRequestURI().substring(request.getContextPath().length());
		
		if(withQuery){
			requestedPath += "?" + request.getQueryString();
		}
		
		System.out.println("------------------------------------");
		System.out.println("RequestURI: "+request.getRequestURI());
		System.out.println("RequestURL: "+ request.getRequestURL().toString());
		System.out.println("ContextPAth: "+request.getContextPath());
		System.out.println("Query String: " + request.getQueryString());
		System.out.println("------------------");
		System.out.println("REQUESTED_PATH: " + requestedPath);
		System.out.println("------------------------------------");
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
	
}
