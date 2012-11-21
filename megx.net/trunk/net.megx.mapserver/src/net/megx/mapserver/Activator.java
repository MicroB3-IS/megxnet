package net.megx.mapserver;

import java.util.Hashtable;

import javax.servlet.Servlet;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	// local root url listener
	private static final String PROP_MAPSERVER_ROOT = "mapserver.root"; 
	
	// remote mapserver basic url
	private static final String PROP_MAPSERVER_PROXY = "mapserver.proxy"; // remove
	
	// remote mapserver path
	private static final String PROP_MAPSERVER_PROXY_PATH = "mapserver.proxy.path";
	
	
	private static final String DEFAULT_MAPSERVER_ROOT = "/wms";
	
	private static final String DEFAULT_MAPSERVER_PROXY = "http://www.megx.net";
	
	private static final String DEFAULT_MAPSERVER_PROXY_PATH = "/wms/gms";
	
	
	private ServiceRegistration servletContainerRegRef;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		//registering ProxyServlet under mapserver.root, default /wms
		String serveltRoot = System.getProperty(PROP_MAPSERVER_ROOT, DEFAULT_MAPSERVER_ROOT);
		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put("alias", serveltRoot);
		
		String mapserverProxy = System.getProperty(PROP_MAPSERVER_PROXY, DEFAULT_MAPSERVER_PROXY);
		String mapserverProxyPath = System.getProperty(PROP_MAPSERVER_PROXY_PATH, DEFAULT_MAPSERVER_PROXY_PATH);
		ProxyServlet proxyServlet = new ProxyServlet(mapserverProxy, mapserverProxyPath);
		servletContainerRegRef = bundleContext.registerService(Servlet.class.getName(), proxyServlet, props);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		if(servletContainerRegRef != null) {
			servletContainerRegRef.unregister();
		}
	}

}
