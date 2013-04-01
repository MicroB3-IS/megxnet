package net.megx.prox.osgi;

import net.megx.prox.rest.PfamProxy;

import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;

public class Activator extends ResTplConfiguredActivator {

	@Override
	protected void registerExtensions(JCRApplication app) {
		PfamProxy pfamProxy = new PfamProxy();
		RegUtils.reg(getBundleContext(),
				PfamProxy.class
						.getName(), pfamProxy, null);
	}

	@Override
	protected String getName() {
		return "net.megx.prox";
	}
	
}
