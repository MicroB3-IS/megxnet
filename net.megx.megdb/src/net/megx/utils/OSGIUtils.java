package net.megx.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

public class OSGIUtils {
	
	private static Log log = LogFactory.getLog(OSGIUtils.class);
	
	public static interface OnServiceAvailable<S>{
		public void serviceAvailable(String name, S service);
	}
	
	public static interface OnServicesAvailable{
		public void servicesAvailable(Map<String, Object> services);
	}
	
	@SuppressWarnings("unchecked")
	public static <S> void requestService(final String name, final BundleContext context,final OnServiceAvailable<S> callback){
		if(log.isDebugEnabled()){
			log.debug(String.format("Registering listener for service '%s' with callback: '%s'.",name, callback.toString()));
		}
		ServiceReference ref = context.getServiceReference(name);
		if( ref!= null ){
			Object svc = context.getService(ref);
			if(svc != null){
				callback.serviceAvailable(name, (S)svc);
				return;
			}
		}
		ServiceListener listener = new ServiceListener() {

			@Override
			public void serviceChanged(ServiceEvent ev) {
				ServiceReference reference = ev.getServiceReference();
				switch (ev.getType()) {
				case ServiceEvent.REGISTERED:
					try {
						log.debug("Received 'REGISTERED' event. Calling the callback...");
						callback.serviceAvailable(name, (S) context
								.getService(reference));
						
					} catch (Throwable e) {
						log.error("Failed to reload the service.",e);
					}
				}
			}
		};

		String filter = "(objectclass=" + name + ")";
		try {
			context.addServiceListener(listener, filter);
			log.debug("Registered.");
		} catch (InvalidSyntaxException e) {
			log.error(e);
		}
	}
	
	private static class MultiServiceCallback implements OnServiceAvailable<Object>{
		public Map<String, Object> services = new HashMap<String, Object>();
		
		private OnServicesAvailable callback;
		private int sizeRequested = 0;
		
		
		
		public MultiServiceCallback(OnServicesAvailable callback, String [] services) {
			this.callback = callback;
			this.sizeRequested = services.length;
		}



		@Override
		public void serviceAvailable(String name, Object service) {
			synchronized (this) {
				services.put(name, service);
			}
			if(services.size() == sizeRequested){
				callback.servicesAvailable(services);
			}
		}
	}
	
	
	public static void requestServices(final BundleContext context, OnServicesAvailable callback,String...names){
		MultiServiceCallback multiServiceCallback = new MultiServiceCallback(callback, names);
		for(String name: names){
			requestService(name, context, multiServiceCallback);
		}
	}
}
