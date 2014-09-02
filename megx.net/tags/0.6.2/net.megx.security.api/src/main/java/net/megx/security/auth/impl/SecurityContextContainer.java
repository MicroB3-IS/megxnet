package net.megx.security.auth.impl;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.SecurityContext;

public class SecurityContextContainer implements SecurityContext{
	
	private Authentication authentication;
	private String lastRequest;
	private Exception lastException;
	
	public SecurityContextContainer() {
		
	}
	
	@Override
	public Authentication getAuthentication() {
		return authentication;
	}

	@Override
	public void clearAuthentication() {
		authentication = null;
	}

	@Override
	public void setAuthentication(Authentication authentication) {
		if(this.authentication == null){
			this.authentication = authentication;
		}
	}

	@Override
	public void storeLastRequestedURL(String url) {
	    if (url == null) {
	        return;
	    }
		lastRequest = url.trim();
	}

	@Override
	public String getLastRequestedURL() {
		return lastRequest;
	}

	@Override
	public Exception getLastException() {
		return lastException;
	}

	@Override
	public void storeLastException(Exception exception) {
		this.lastException = exception;
	}

	@Override
	public Exception pullLastException() {
		Exception e = getLastException();
		storeLastException(null);
		return e;
	}

}
