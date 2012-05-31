package net.megx.security.filter.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.web.WebAuthenticationHandler;
import net.megx.security.filter.SecurityException;
import net.megx.security.filter.StopFilterException;

public class WebLoginSecurity extends BaseSecurityEntrypoint{

	
	WebAuthenticationHandler webLoginHandler;
	
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			SecurityException, StopFilterException {
		if(webLoginHandler == null){
			throw new SecurityException(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		}
		Authentication authentication = webLoginHandler.createAuthentication(request);
		if(authentication != null){
			saveAuthentication(authentication, request);
		}
	}

	@Override
	protected void doInitialize() {
		requestService("net.megx.security.auth.web.WebLoginHandler", new OnServiceAvailable<WebAuthenticationHandler>() {

			@Override
			public void serviceAvailable(WebAuthenticationHandler service) {
				WebLoginSecurity.this.webLoginHandler = service;
			}
			
		});
	}

}
