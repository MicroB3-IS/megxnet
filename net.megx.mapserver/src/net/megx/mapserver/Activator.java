package net.megx.mapserver;

import java.util.Hashtable;

import javax.servlet.Servlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
	private static final Log log = LogFactory.getLog(Activator.class);
	
	// local root url listener
	private static final String PROP_MAPSERVER_ROOT = "mapserver.local.root"; 
	
	// remote mapserver basic url
	private static final String PROP_MAPSERVER_URL = "mapserver.remote.url";
	
	//map file that goes as a parameter to maprser.remote.url
	private static final String PROP_MAPSERVER_MAPFILE = "mapserver.map.file";
	
	
	private static final String DEFAULT_MAPSERVER_ROOT = "/wms";
	
	private static final String DEFAULT_MAPSERVER_URL = "";
	
	private static final String DEFAULT_MAPSERVER_MAPFILE = "";
	
	
	private ServiceRegistration servletContainerRegRef;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		//registering ProxyServlet under mapserver.root, default /wms
		String serveltRoot = System.getProperty(PROP_MAPSERVER_ROOT, DEFAULT_MAPSERVER_ROOT);
		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put("alias", serveltRoot);
		
		log.debug("Registering mapserver ProxyServlet under mapserver.root = " + serveltRoot);
		
		String mapserverUrl = System.getProperty(PROP_MAPSERVER_URL, DEFAULT_MAPSERVER_URL);
		String mapserverMapfile = System.getProperty(PROP_MAPSERVER_MAPFILE, DEFAULT_MAPSERVER_MAPFILE);
		
		log.debug("- mapserverURL = " + mapserverUrl);
		log.debug("- mapserverMapfile = " + mapserverMapfile);
		
		MapserverProxyServlet proxyServlet = new MapserverProxyServlet(mapserverUrl, mapserverMapfile);
		servletContainerRegRef = bundleContext.registerService(Servlet.class.getName(), proxyServlet, props);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		if(servletContainerRegRef != null) {
			servletContainerRegRef.unregister();
		}
	}

}
