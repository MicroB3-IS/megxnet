package net.megx.security.filter.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.megx.security.auth.AccessDeniedException;
import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.SecurityException;
import net.megx.security.auth.web.WebContextUtils;
import net.megx.security.auth.web.WebUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

public class DefaultExceptionHandler extends BaseExceptionHandler{
	protected Log log = LogFactory.getLog(getClass());
	
	private Map<Integer, String> redirects = new HashMap<Integer, String>();
	
	@Override
	public boolean handleException(Exception e, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		log.debug("Exception: ",e);
		try {
			storeRequestURL(request);
		} catch (URISyntaxException e1) {
			throw new ServletException(e1);
		}
		if(e instanceof IOException ){
			throw (IOException)e;
		}else if(e instanceof ServletException){
			throw (ServletException)e;
		}else if(e instanceof SecurityException){
			SecurityContext context = WebContextUtils.getSecurityContext(request);
			if(context != null){
				context.storeLastException(e);
			}
			if(!response.isCommitted()){
				//response.sendError(((SecurityException)e).getResponseCode(),e.getMessage());
				try {
					handleExceptionRedirect(e, request, response, ((SecurityException)e).getResponseCode());
				} catch (URISyntaxException e1) {
					throw new ServletException(e1);
				}
			}
		}else if(e instanceof AccessDeniedException){
			//response.sendError(HttpServletResponse.SC_FORBIDDEN);
			try {
				handleExceptionRedirect(e, request, response, HttpServletResponse.SC_FORBIDDEN);
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				throw new ServletException(e1);
			}
		}else{
			throw new ServletException(e);
		}
		return true;
	}
	
	private void storeRequestURL(HttpServletRequest request) throws URISyntaxException{
		if(!"get".equals(request.getMethod().toLowerCase())){
			if(log.isDebugEnabled())
				log.debug("Last Request will not be stored. Method: " + request.getMethod());
			return;
		}
		if(log.isDebugEnabled())
			log.debug("Saving last request: " + request);
		SecurityContext context = WebContextUtils.getSecurityContext(request);
		if(context == null){
			context = WebContextUtils.newSecurityContext(request);
			WebContextUtils.replaceSecurityContext(context, request, true);
		}
		String requestURL = WebUtils.getRequestPath(request, true);
		if(context.getLastRequestedURL() != null){
			// check to see if we already have denied access to some URL
			requestURL = context.getLastRequestedURL();
		}
		if(log.isDebugEnabled())
			log.debug("   -> Last Request URL: " + requestURL);
		context.storeLastRequestedURL(requestURL);
	}

	
	@Override
	public void init() {
		if(this.config != null){
			JSONObject redirects = config.optJSONObject("redirects");
			if(redirects != null){
				String [] codes = JSONObject.getNames(redirects);
				if(codes != null){
					for(String code: codes){
						try{
							int httpCode = Integer.parseInt(code);
							String redirectURL = redirects.getString(code);
							if(redirectURL != null){
								log.debug(String.format("\t Mapped redirect: %d => %s", httpCode, redirectURL));
								this.redirects.put(httpCode, redirectURL);
							}
						}catch (Throwable e) {}
					}
				}
			}
		}
	}
	
	
	protected void handleExceptionRedirect(Exception e, HttpServletRequest request,
			HttpServletResponse response, int code)throws IOException, ServletException, URISyntaxException{
		String redirectURL = redirects.get(code);
		if(redirectURL != null){
			String appUrl = WebUtils.getAppURL(request);
			if(appUrl.endsWith("/") && redirectURL.startsWith("/")){
				redirectURL = redirectURL.substring(1);
			}else if(!appUrl.endsWith("/") && !redirectURL.startsWith("/")){
				redirectURL = "/" + redirectURL;
			}
			String redirect = appUrl + redirectURL;
			log.debug("REDIRECT => " + redirect);
			response.sendRedirect(redirect);
		}else{
			response.sendError(code, e.getMessage());
		}
	}
	
}
