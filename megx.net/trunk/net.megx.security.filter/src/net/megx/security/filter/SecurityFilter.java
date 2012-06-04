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
import net.megx.security.filter.http.HttpRequestWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.BundleContext;

public class SecurityFilter implements Filter{

	private Log log = LogFactory.getLog(getClass());
	
	private BundleContext context;
	private JSONObject bundleConfig;
	private AuthenticationManager authenticationManager;
	private WebResourcesService resourcesService;
	List<EntryPointWrapper> entryPoints = new ArrayList<SecurityFilter.EntryPointWrapper>();
	private boolean enabled;
	
	public SecurityFilter(BundleContext context, JSONObject bundleConfig) {
		super();
		this.context = context;
		this.bundleConfig = bundleConfig;
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		log.debug("Security filter start...");
		if(request instanceof HttpServletRequest && response instanceof HttpServletResponse){
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			if(!enabled){
				log.debug("Secuirty filter is not enabled. Will pass the request to the chain.");
				chain.doFilter(request, response);
				return;
			}
			boolean hasMatched = false;
			for(EntryPointWrapper entryPoint: entryPoints){
					log.debug(String.format("Matching enty-point %s",entryPoint.name));
					if(entryPoint.matches(WebUtils.getRequestPath(httpRequest, false))){
						log.debug("\t -> match");
						hasMatched = true;
						try {
							entryPoint.entrypoint.doFilter(httpRequest, httpResponse);
						} catch (StopFilterException e) {
							return;
						} catch (Exception e) {
							handleException(e, httpRequest, httpResponse);
							return;
						}
					}
			}
			if(hasMatched){
				try {
					checkAuthenticationResult(httpRequest, httpResponse);
				} catch (SecurityException e) {
					handleException(e, httpRequest, httpResponse);
					return;
				}
			}
			SecurityContext context = WebContextUtils.getSecurityContext(httpRequest);
			if(context != null){
				httpRequest = new HttpRequestWrapper(httpRequest, context.getAuthentication());
			}
			log.debug("Security filter - pass chain.");
			chain.doFilter(httpRequest, httpResponse);
		}else{
			throw new ServletException("Wrong Request Type.");
		}
		
		log.debug("Security filter end.");
	}
	
	protected void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		log.debug(e);
		storeRequestURL(request);
		if(e instanceof IOException ){
			throw (IOException)e;
		}else if(e instanceof ServletException){
			throw (ServletException)e;
		}else if(e instanceof SecurityException){
			if(!response.isCommitted())
				response.sendError(((SecurityException)e).getResponseCode(),e.getMessage());
		}else{
			throw new ServletException(e);
		}
	}
	
	private void storeRequestURL(HttpServletRequest request){
		SecurityContext context = WebContextUtils.getSecurityContext(request);
		if(context == null){
			context = WebContextUtils.newSecurityContext(request);
			WebContextUtils.replaceSecurityContext(context, request, true);
		}
		context.storeLastRequestedURL(WebUtils.getRequestPath(request, true));
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
		log.debug("Initializing security filter chain...");
		System.out.println("BOOM!");
		try {
			JSONObject filterConfig = bundleConfig.getJSONObject("filter");
			if(log.isDebugEnabled()){
				log.debug(String.format("Filter configuration: %s", filterConfig.toString(3)));
			}
			enabled = Boolean.TRUE.equals(filterConfig.optBoolean("enabled"));
			JSONArray entryPoints = filterConfig.getJSONArray("entrypoints");
			
			log.debug("Initializing secuirty entrypoints...");
			for(int i = 0; i < entryPoints.length(); i++){
				JSONObject entryPointConfig = entryPoints.getJSONObject(i);
				buildEntryPoint(entryPointConfig);
			}
			log.debug("Entrypoint built.");
			Collections.sort(this.entryPoints);
			
			OSGIUtils.requestService(AuthenticationManager.class.getName(), context, 
					new OSGIUtils.OnServiceAvailable<AuthenticationManager>() {

				@Override
				public void serviceAvailable(AuthenticationManager service) {
					SecurityFilter.this.authenticationManager = service;
					log.debug("Obtained authentication manager instance: " + service.toString());
				}
				
			});
			
			
		} catch (Exception e) {
			log.error(e);
			throw new ServletException(e);
		}
		log.debug("Secuirty filter successfully initialized.");
	}

	
	@SuppressWarnings("unchecked")
	protected void buildEntryPoint(JSONObject config) throws JSONException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		String name = config.getString("name");
		String urlPattern = config.getString("urlPattern");
		String clazz = config.getString("class");
		int order = config.optInt("order");
		
		log.debug(String.format("Building entrypoint (name='%s',urlPattern='%s',class='%s',order='%d')",name, urlPattern,clazz, order));
		log.debug("Full configuration for entrypoint: " + config);
		Class<? extends SecurityFilterEntrypoint> epClass = (Class<? extends SecurityFilterEntrypoint>)getClassLoader().loadClass(clazz);
		SecurityFilterEntrypoint entrypoint = epClass.newInstance();
		entrypoint.initialize(context, config);
		entryPoints.add(new EntryPointWrapper(entrypoint, urlPattern, name, order));
		log.debug("Entry-point successfuly built.");
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
			System.out.println(String.format("\t matching '%s' to pattern '%s'",path, urlPattern));
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
