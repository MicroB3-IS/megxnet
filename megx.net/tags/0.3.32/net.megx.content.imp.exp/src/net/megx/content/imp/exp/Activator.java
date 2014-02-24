package net.megx.content.imp.exp;

import java.util.Hashtable;

import javax.servlet.Servlet;

import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator extends ResTplConfiguredActivator {

	

	private ServiceRegistration servletContainerRegRef;

	@Override
	protected void registerExtensions(JCRApplication app) {		
		BundleContext bundleContext = getBundleContext();
		ContentImportExportServlet contentExportServlet = new ContentImportExportServlet(bundleContext);
		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put("alias", "/admin/content-import-export");
		servletContainerRegRef = bundleContext.registerService(Servlet.class.getName(), contentExportServlet, props);
		
		String urlPrefix = getName();
		app.regExtension(getName(), new ImpExpExtension(urlPrefix));
	}

	@Override
	protected String getName() {
		return "net.megx.content.imp.exp";
	}
	
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		if(servletContainerRegRef != null) {
			servletContainerRegRef.unregister();
		}
	}
	
}
