package org.microb3.security.filter;

import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;

public class Activator extends ResTplConfiguredActivator {

	@Override
	protected void registerExtensions(JCRApplication app) {
		// TODO register extension
	}

	@Override
	protected String getName() {
		return "org.microb3.security.filter";
	}
	
}
