package net.megx.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.megx.security.auth.AuthenticationManager;
import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.WebResource;
import net.megx.security.auth.services.WebResourcesService;
import net.megx.security.auth.web.WebContextUtils;
import net.megx.security.auth.web.WebUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.BundleContext;

public class SecurityFilter implements Filter{

	private BundleContext context;
	private JSONObject bundleConfig;
	private AuthenticationManager authenticationManager;
	private WebResourcesService resourcesService;
	List<EntryPointWrapper> entryPoints = new ArrayList<SecurityFilter.EntryPointWrapper>();
	private boolean enabled;
	
	public SecurityFilter(BundleContext context) {
		super();
		this.context = context;
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if(request instanceof HttpServletRequest && response instanceof HttpServletResponse){
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			if(!enabled){
				chain.doFilter(request, response);
				return;
			}
			boolean hasMatched = false;
			for(EntryPointWrapper entryPoint: entryPoints){
					if(entryPoint.matches(WebUtils.getRequestPath(httpRequest, false))){
						hasMatched = true;
						try {
							entryPoint.entrypoint.doFilter(httpRequest, httpResponse);
						} catch (StopFilterException e) {
							break;
						} catch (Exception e) {
							handleException(e, httpRequest, httpResponse);
						}
					}
			}
			if(hasMatched){
				try {
					checkAuthenticationResult(httpRequest, httpResponse);
				} catch (SecurityException e) {
					handleException(e, httpRequest, httpResponse);
				}
			}
		}else{
			throw new ServletException("Wrong Request Type.");
		}
	}
	
	protected void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		if(e instanceof IOException ){
			throw (IOException)e;
		}else if(e instanceof ServletException){
			throw (ServletException)e;
		}else if(e instanceof SecurityException){
			response.sendError(((SecurityException)e).getResponseCode());
		}else{
			throw new ServletException(e);
		}
	}
	
	protected void checkAuthenticationResult(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, SecurityException{
		SecurityContext context = WebContextUtils.getSecurityContext(request);
		if(context == null){
			throw new SecurityException(HttpServletResponse.SC_UNAUTHORIZED);
		}
		if(context.getAuthentication() == null){
			throw new SecurityException(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		List<WebResource> resources = null;
		try {
			resources = resourcesService.match(WebUtils.getRequestPath(request, true), request.getMethod());
		} catch (Exception e) {
			throw new ServletException(e);
		}
		if(resources == null){
			return;
		}
		for(WebResource resource: resources){
			authenticationManager.checkAuthentication(context.getAuthentication(), resource);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		try {
			JSONObject filterConfig = bundleConfig.getJSONObject("filter");
			enabled = Boolean.TRUE.equals(filterConfig.optBoolean("enabled"));
			JSONArray entryPoints = filterConfig.getJSONArray("entrypoints");
			
			for(int i = 0; i < entryPoints.length(); i++){
				JSONObject entryPointConfig = entryPoints.getJSONObject(i);
				buildEntryPoint(entryPointConfig);
			}
			
			Collections.sort(this.entryPoints);
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	
	@SuppressWarnings("unchecked")
	protected void buildEntryPoint(JSONObject config) throws JSONException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		String name = config.getString("name");
		String urlPattern = config.getString("urlPattern");
		String clazz = config.getString("class");
		int order = config.optInt("order");
		Class<? extends SecurityFilterEntrypoint> epClass = (Class<? extends SecurityFilterEntrypoint>)getClassLoader().loadClass(clazz);
		SecurityFilterEntrypoint entrypoint = epClass.newInstance();
		entrypoint.initialize(context, config);
		entryPoints.add(new EntryPointWrapper(entrypoint, urlPattern, name, order));
	}
	
	
	protected class EntryPointWrapper implements Comparable<EntryPointWrapper>{
		public SecurityFilterEntrypoint entrypoint;
		private String urlPattern;
		public String name;
		public int order;
		public EntryPointWrapper(SecurityFilterEntrypoint entrypoint,
				String urlPattern,
				String name,
				int order) {
			super();
			this.entrypoint = entrypoint;
			this.urlPattern = urlPattern;
			this.name       = name;
			this.order = order;
		}

		public boolean matches(String path){
			return path.matches(urlPattern);
		}

		@Override
		public int compareTo(EntryPointWrapper o) {
			return order - o.order;
		}
	}
	
	protected ClassLoader getClassLoader(){
		return SecurityFilter.class.getClassLoader();
	}
}
