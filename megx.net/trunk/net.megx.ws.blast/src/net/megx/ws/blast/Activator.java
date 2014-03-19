package net.megx.ws.blast;

import net.megx.megdb.blast.BlastService;
import net.megx.utils.OSGIUtils;
import net.megx.ws.blast.rest.BlastServiceAPI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;

public class Activator extends ResTplConfiguredActivator {

	private Log log = LogFactory.getLog(getClass());

	@Override
	protected void registerExtensions(JCRApplication app) {
		log.debug("BlastServiceAPI starting up");
		OSGIUtils.requestService(BlastService.class.getName(),
				getBundleContext(),
				new OSGIUtils.OnServiceAvailable<BlastService>() {

					@Override
					public void serviceAvailable(String name,
							BlastService service) {
						log.debug("BlastService received...");
						BlastServiceAPI api = new BlastServiceAPI(service);
						RegUtils.reg(getBundleContext(),
								BlastServiceAPI.class.getName(), api, null);
						log.debug("BlastServiceAPI started.");
					}

				});
	}

	@Override
	protected String getName() {
		return "net.megx.ws.blast";
	}

}
