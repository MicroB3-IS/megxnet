package net.megx.pubmap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;

public class Activator extends ResTplConfiguredActivator {
	
	private static final Log log = LogFactory.getLog(Activator.class);
	
	@Override
	protected void registerExtensions(JCRApplication app) {
		// TODO register extensions
		log.debug("Start PubMap bundle");
	}

	@Override
	protected String getName() {
		return "net.megx.pubmap";
	}
}
