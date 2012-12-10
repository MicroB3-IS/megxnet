package net.megx.ws.blast;

import net.megx.ws.blast.rest.BlastRestService;

import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;

public class Activator extends ResTplConfiguredActivator {

	@Override
	protected void registerExtensions(JCRApplication app) {
		BlastRestService blastRestService = new BlastRestService();
		RegUtils.reg(getBundleContext(), BlastRestService.class.getName(), blastRestService, null);
	}

	@Override
	protected String getName() {
		return "net.megx.ws.blast";
	}
	
}
