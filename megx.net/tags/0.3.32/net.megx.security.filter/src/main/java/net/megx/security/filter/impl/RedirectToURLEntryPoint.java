package net.megx.security.filter.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.SecurityException;
import net.megx.security.auth.web.WebContextUtils;
import net.megx.security.filter.StopFilterException;

public class RedirectToURLEntryPoint extends BaseSecurityEntrypoint{
	
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			SecurityException, StopFilterException {
		
		SecurityContext context = WebContextUtils.getSecurityContext(request);
		if(context == null){
			log.debug("Creating new security context...");
			context = WebContextUtils.newSecurityContext(request);
		}
		
		if(request.getParameter("redirectURL") != null){
			context.storeLastRequestedURL(request.getParameter("redirectURL"));
		}
	}

	@Override
	protected void doInitialize() {
		// TODO Auto-generated method stub
		
	}

}
