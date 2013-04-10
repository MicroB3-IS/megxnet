package net.megx.security.filter.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.megx.security.auth.SecurityException;
import net.megx.security.filter.StopFilterException;

public class OpenIdEndpoint extends BaseSecurityEntrypoint{

	private String loginEndpoint = ".*/alt_login";
	private String callbackEndpoint = ".*/callback";
	
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			SecurityException, StopFilterException {
		//ConsumerManager manager;
	}

	@Override
	protected void doInitialize() {
		loginEndpoint = config.optString("loginEndpoint", loginEndpoint);
		callbackEndpoint = config.optString("callbackEndpoint", callbackEndpoint);
	}

}
