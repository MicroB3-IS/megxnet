package net.megx.security.filter;

import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;

public class Activator extends ResTplConfiguredActivator {

	@Override
	protected void registerExtensions(JCRApplication app) {
		// TODO register extension
	}

	@Override
	protected String getName() {
		return "net.megx.security.filter";
	}
	
}
