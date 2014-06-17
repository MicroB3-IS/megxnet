package net.megx.twitter;

import net.megx.twitter.service.TwitterServiceImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.osgi.framework.BundleContext;

public class Activator extends ResTplConfiguredActivator {

	private Log log = LogFactory.getLog(getClass());

	@Override
	protected String getName() {
		return "net.megx.twitter";
	}

	@Override
	public void start(BundleContext context) throws Exception {

		log.debug("Twitter Services starting.");

		try {

			TwitterServiceImpl service = new TwitterServiceImpl();

			context.registerService(BaseTwitterService.class.getName(),
					service, null);

			log.info("Twitter Services start success.");

		} catch (Exception e) {
			log.error(e);
		}
		super.start(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

	@Override
	protected void registerExtensions(JCRApplication arg0) {
		// TODO Auto-generated method stub

	}

}
