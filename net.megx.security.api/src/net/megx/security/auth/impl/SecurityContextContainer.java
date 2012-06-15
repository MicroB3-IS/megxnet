package net.megx.security.auth.impl;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.SecurityContext;

public class SecurityContextContainer implements SecurityContext{
	
	private Authentication authentication;
	private String lastRequest;
	
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
		lastRequest = url;
	}

	@Override
	public String getLastRequestedURL() {
		return lastRequest;
	}

}
