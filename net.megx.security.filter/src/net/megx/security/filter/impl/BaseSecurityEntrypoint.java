package net.megx.security.filter.impl;

import javax.servlet.http.HttpServletRequest;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.web.WebContextUtils;
import net.megx.security.filter.SecurityFilterEntrypoint;

import org.json.JSONObject;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

public abstract class BaseSecurityEntrypoint implements SecurityFilterEntrypoint{

	protected BundleContext context;
	protected JSONObject config;
	
	
	@Override
	public final void initialize(BundleContext context, JSONObject config) {
		this.config = config;
		this.context = context;
		doInitialize();
	}

	
	protected <S> void requestService(String name, final OnServiceAvailable<S> onServiceAvailable){
		ServiceListener listener = new ServiceListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void serviceChanged(ServiceEvent ev) {
				ServiceReference reference = ev.getServiceReference();
				switch(ev.getType()){
				case ServiceEvent.REGISTERED:
					try{
						onServiceAvailable.serviceAvailable((S)context.getService(reference));
					}catch (Throwable e) {
						System.out.println(e);
					}
				}
			}
		};
		
		String filter = "(objectclass=" + name + ")";
		try {
			context.addServiceListener(listener,filter);
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
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
