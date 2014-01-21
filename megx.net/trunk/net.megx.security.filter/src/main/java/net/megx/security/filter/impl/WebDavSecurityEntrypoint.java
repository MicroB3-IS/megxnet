package net.megx.security.filter.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.SecurityException;
import net.megx.security.auth.web.GeneralAuthenticationHandler;
import net.megx.security.auth.web.WebContextUtils;
import net.megx.security.auth.web.WebUtils;
import net.megx.security.filter.StopFilterException;
import net.megx.utils.OSGIUtils;

import org.apache.commons.codec.binary.Base64;

public class WebDavSecurityEntrypoint extends BaseSecurityEntrypoint{

	private String webDavContextPath;
	private GeneralAuthenticationHandler authHandler;
	
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			SecurityException, StopFilterException {
		log.debug("Checking WebDAV Request...");
		checkAuthentication(request, response);
		String path;
		try {
			path = WebUtils.getRequestPath(request, false);
		} catch (URISyntaxException e) {
			throw new ServletException(e);
		}
		String username = null;
		SecurityContext context = WebContextUtils.getSecurityContext(request);
		
		if(context != null){
			Authentication authentication = context.getAuthentication();
			username = authentication != null ? authentication.getUserPrincipal().toString() : null;
		}
		
		if(username != null){
			if(path == null){
				throw new WebDavSecurityException("Invalid request", 400); // invalid request
			}
			String userContextPath  = getContextPathForUser(username);
			path = WebUtils.normalize(path);
			if(!path.startsWith(userContextPath)){
				throw new WebDavSecurityException("Forbidden", 401);
			}
		}
		log.debug("Checking WebDAV Request - complete");
	}

	@Override
	protected void doInitialize() {	
		webDavContextPath = config.optString("contextPath", "/webdav");
		OSGIUtils.requestService(GeneralAuthenticationHandler.class.getName(), context, new OSGIUtils.OnServiceAvailable<GeneralAuthenticationHandler>() {

			@Override
			public void serviceAvailable(String name, GeneralAuthenticationHandler service) {
				authHandler = service;
			}
		});
	}
	
	protected String getContextPathForUser(String username){
		return String.format("%s/%s", webDavContextPath, username);
	}

	
	private void checkAuthentication(HttpServletRequest request, HttpServletResponse response) throws SecurityException, ServletException, IOException{
		String authorizationHeader = request.getHeader("Authorization");
		log.debug("Authorization Header: "+authorizationHeader);
		if(authorizationHeader != null){
			authorizationHeader = authorizationHeader.trim();
			StringTokenizer st = new StringTokenizer(authorizationHeader);
			String basic = st.nextToken().trim();
			String authorizationStrB64 = st.nextToken().trim();
			
			if("Basic".equals(basic)){
				String decoded = new String(Base64.decodeBase64(authorizationStrB64)); // FIXME: utf-8
				log.debug("Decoded Base64 authentication: " + decoded);
				int index = decoded.indexOf(":");
				if(index < 0){
					throw new SecurityException(400);// bad request
				}
				String username = decoded.substring(0, index);
				String password = decoded.substring(index+1);
				if(authHandler != null){
					request.setAttribute(GeneralAuthenticationHandler.USERNAME_KEY, username);
					request.setAttribute(GeneralAuthenticationHandler.PASSWORD_KEY, password);
					log.debug(String.format("Set username=[%s] and password=[%s]", username, password));
					Authentication authentication = authHandler.createAuthentication(request);
					if(authentication != null){
						log.debug("Authentication confirmed: " + authentication);
						SecurityContext context = WebContextUtils.getSecurityContext(request);
						if(context == null){
							context = WebContextUtils.newSecurityContext(request);
						}
						context.clearAuthentication();
						context.setAuthentication(authentication);
					}
				}else{
					throw new SecurityException(503);
				}
			}else{
				throw new SecurityException(400); // Bad request
			}
			
		}
	}
	
	
	
	
	public static void main(String[] args) {
		String [] paths = new String [] {
				"/",
				"../",
				".",
				"..",
				"/.",
				"/path",
				"path",
				"path/../../../../",
				"path/./.././..",
				"path/./.././..",
				"../path",
				"/path/../../path",
				"./path",
				"/./path"
		};
		for(String path: paths){
			System.out.println(String.format("[%s] -> [%s]", path, WebUtils.normalize(path)));
		}
	}
	
	
}
