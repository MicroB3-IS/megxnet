package net.megx.ws.common;

import java.util.LinkedList;
import java.util.List;

import net.megx.ws.core.providers.csv.SimpleCSVProvider.SimpleCSVProvider_App_CSV;
import net.megx.ws.core.providers.csv.SimpleCSVProvider.SimpleCSVProvider_Text_CSV;
import net.megx.ws.core.providers.html.VelocityHTMLProvider;
import net.megx.ws.core.providers.txt.AbstractPlainTextProvider.SimplePlainTextProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;
import org.choncms.rest.libs.RestServiceProvider;

public class Activator extends ResTplConfiguredActivator {

	private Log log = LogFactory.getLog(getClass());

	private List<Class<?>> exposedProviders = new LinkedList<Class<?>>();
	
	
	
	
	@Override
	protected void registerExtensions(JCRApplication app) {
		log.debug("Exposing JAX-RS Providers....");
		exposeProviderClasses();
		
		RestServiceProvider rsp = new RestServiceProvider() {
			
			@Override
			public List<Class<?>> getServices() {
				return exposedProviders;
			}
		};
		RegUtils.reg(getBundleContext(), rsp.getClass().getName(), rsp, null);
		log.debug("Registered the service providers.");
		doTest();
			
		log.debug("The WS Common Bundle started successfully.");
		
	}

	@Override
	protected String getName() {
		return "net.megx.ws.common";
	}
	
	private void exposeProviderClasses(){
		exposedProviders.add(VelocityHTMLProvider.class);
		exposedProviders.add(SimpleCSVProvider_App_CSV.class);
		exposedProviders.add(SimpleCSVProvider_Text_CSV.class);
		exposedProviders.add(SimplePlainTextProvider.class);
	}

	private void doTest(){
		RegUtils.reg(getBundleContext(), TestResource.class.getName(), 
				new TestResource(), null);
	}
	
}
