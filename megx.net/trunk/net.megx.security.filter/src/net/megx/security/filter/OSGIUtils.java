package net.megx.security.filter;

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
		public void serviceAvailable(S service);
	}
	
	public static <S> void requestService(String name, final BundleContext context,final OnServiceAvailable<S> callback){
		if(log.isDebugEnabled()){
			log.debug(String.format("Registering listener for service '%s' with callback: '%s'.",name, callback.toString()));
		}
		ServiceListener listener = new ServiceListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void serviceChanged(ServiceEvent ev) {
				ServiceReference reference = ev.getServiceReference();
				switch (ev.getType()) {
				case ServiceEvent.REGISTERED:
					try {
						log.debug("Received 'REGISTERED' event. Calling the callback...");
						callback.serviceAvailable((S) context
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
}
