package net.megx.was.webdav;

import java.util.Hashtable;

import javax.jcr.Repository;
import javax.servlet.Servlet;

import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.cms.model.ContentModel;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator extends ResTplConfiguredActivator {
	
	private static final String ALIAS = "/dav";
	private ServiceRegistration servletContainerRegRef;
	
	@Override
	protected String getName() {
		return "net.megx.was.webdav";
	}

	@Override
	protected void registerExtensions(JCRApplication app) {
		System.out.println("*APP************** Activator.onAppAdded()");
		Jocko j = new Jocko();
		j.metoda();
		
		ContentModel cm = app.createContentModelInstance(getName());
		Repository repository = cm.getSession().getRepository();
		
		System.out.println("!!!!!! Starting DAV activator!!!");
		String serveltRoot = System.getProperty("dav.root", ALIAS);
		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put("alias", serveltRoot);
		props.put("init.resource-path-prefix", ALIAS);
		props.put("init.missing-auth-mapping", "");
		props.put("init.resource-config", "__config/config.xml");
		SimpleWebdavServletImpl servlet = new SimpleWebdavServletImpl(repository);
		servletContainerRegRef = getBundleContext().registerService(
				Servlet.class.getName(), servlet, props);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		if(servletContainerRegRef != null) {
			servletContainerRegRef.unregister();
		}
	}
	
	

}
