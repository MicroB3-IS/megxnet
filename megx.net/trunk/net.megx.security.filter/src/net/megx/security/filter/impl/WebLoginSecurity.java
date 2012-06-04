package net.megx.security.filter.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.web.WebLoginHandler;
import net.megx.security.filter.SecurityException;
import net.megx.security.filter.StopFilterException;

public class WebLoginSecurity extends BaseSecurityEntrypoint{

	
	WebLoginHandler webLoginHandler;
	
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			SecurityException, StopFilterException {
		if(webLoginHandler == null){
			throw new SecurityException("WebAuthenticationHandler service is not available yet!",HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		}
		Authentication authentication = webLoginHandler.createAuthentication(request);
		if(authentication != null){
			saveAuthentication(authentication, request);
		}
	}

	@Override
	protected void doInitialize() {
		requestService(WebLoginHandler.class.getName(), new OnServiceAvailable<WebLoginHandler>() {

			@Override
			public void serviceAvailable(WebLoginHandler service) {
				WebLoginSecurity.this.webLoginHandler = service;
			}
			
		});
	}

}
