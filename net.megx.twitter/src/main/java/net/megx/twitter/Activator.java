package net.megx.twitter;

import net.megx.twitter.service.TwitterServiceImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;

public class Activator extends ResTplConfiguredActivator {

	private Log log = LogFactory.getLog(getClass());

	@Override
	protected String getName() {
		return "net.megx.twitter";
	}
	
	@Override
	protected void onAppAdded(org.osgi.framework.BundleContext context, JCRApplication app) {
		super.onAppAdded(context, app);
		
		TwitterServiceImpl twitterService = new TwitterServiceImpl();
		
		log.debug((String.format(
				"Registering service instance %s of class (%s) as (%s)",
				twitterService.toString(), TwitterServiceImpl.class.getName(), BaseTwitterService.class.getName())));
		
		RegUtils.reg(getBundleContext(), BaseTwitterService.class.getName(), twitterService, null);
	};

	@Override
	protected void registerExtensions(JCRApplication arg0) {
		// TODO Auto-generated method stub

	}

}
