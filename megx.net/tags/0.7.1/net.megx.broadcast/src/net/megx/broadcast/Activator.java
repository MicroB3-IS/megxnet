package net.megx.broadcast;

import net.megx.broadcast.proxy.BroadcasterProxy;
import net.megx.broadcast.proxy.impl.AtmosphereBroadcastProxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;

public class Activator extends ResTplConfiguredActivator {

protected Log log = LogFactory.getLog(getClass());
	
	@Override
	protected void onAppAdded(org.osgi.framework.BundleContext context, JCRApplication app) {
		super.onAppAdded(context, app);
		
		AtmosphereBroadcastProxy proxyToRegister = new AtmosphereBroadcastProxy(); 
		
		log.debug((String.format(
				"Registering service instance %s of class (%s) as (%s)",
				proxyToRegister.toString(), AtmosphereBroadcastProxy.class.getName(), BroadcasterProxy.class.getName())));
		
		RegUtils.reg(getBundleContext(), BroadcasterProxy.class.getName(), proxyToRegister,null);
	};
	
	@Override
	protected void registerExtensions(JCRApplication arg0) {
	}

	@Override
	protected String getName() {
		return "net.megx.broadcast";
	}
	
}
