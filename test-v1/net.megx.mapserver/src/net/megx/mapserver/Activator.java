package net.megx.mapserver;

import java.util.Hashtable;

import javax.servlet.Servlet;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private static final String ALIAS = "/mapserver";
	private ServiceRegistration servletContainerRegRef;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		//registering ProxyServlet under mapserver.root, default /mapserver 
		String serveltRoot = System.getProperty("mapserver.root", ALIAS);
		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put("alias", serveltRoot);
		ProxyServlet proxyServlet = new ProxyServlet();
		servletContainerRegRef = bundleContext.registerService(Servlet.class.getName(), proxyServlet, props);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		if(servletContainerRegRef != null) {
			servletContainerRegRef.unregister();
		}
	}

}
