package net.megx.security.filter;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

public class OSGIUtils {
	public static interface OnServiceAvailable<S>{
		public void serviceAvailable(S service);
	}
	public static <S> void requestService(String name, final BundleContext context,final OnServiceAvailable<S> callback){
		ServiceListener listener = new ServiceListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void serviceChanged(ServiceEvent ev) {
				ServiceReference reference = ev.getServiceReference();
				switch (ev.getType()) {
				case ServiceEvent.REGISTERED:
					try {
						callback.serviceAvailable((S) context
								.getService(reference));
					} catch (Throwable e) {
						System.out.println(e);
					}
				}
			}
		};

		String filter = "(objectclass=" + name + ")";
		try {
			context.addServiceListener(listener, filter);
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
	}
}
