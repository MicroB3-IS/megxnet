package net.megx.security.filter.impl;

import javax.servlet.http.HttpServletRequest;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.web.WebContextUtils;
import net.megx.security.filter.SecurityFilterEntrypoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.osgi.framework.BundleContext;

public abstract class BaseSecurityEntrypoint implements SecurityFilterEntrypoint{

	protected BundleContext context;
	protected JSONObject config;
	protected Log log = LogFactory.getLog(getClass());
	
	@Override
	public final void initialize(BundleContext context, JSONObject config) {
		this.config = config;
		this.context = context;
		doInitialize();
	}

	
	
	
	protected abstract void doInitialize();
	
	protected interface OnServiceAvailable<S>{
		public void serviceAvailable(S service);
	}
	
	
	protected void saveAuthentication(Authentication authentication, HttpServletRequest request){
		SecurityContext context = WebContextUtils.getSecurityContext(request);
		if(context == null){
			context = WebContextUtils.newSecurityContext(request);
		}
		context.setAuthentication(authentication);
	}
}
