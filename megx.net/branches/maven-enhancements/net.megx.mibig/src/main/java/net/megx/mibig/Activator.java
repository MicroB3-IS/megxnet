package net.megx.mibig;

import net.megx.megdb.mibig.MibigService;
import net.megx.mibig.rest.MibigAPI;
import net.megx.utils.OSGIUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;

public class Activator extends ResTplConfiguredActivator {

	private Log log = LogFactory.getLog(getClass());

	@Override
	protected String getName() {
		return "net.megx.mibig";
	}

	@Override
	protected void registerExtensions(JCRApplication app) {
		log.debug("Mibig Service starting up");
		OSGIUtils.requestService(MibigService.class.getName(),
				getBundleContext(),
				new OSGIUtils.OnServiceAvailable<MibigService>() {

					@Override
					public void serviceAvailable(String name,
							MibigService service) {
						log.debug("Mibig Service received...");
						MibigAPI api = new MibigAPI(service);
						RegUtils.reg(getBundleContext(),
								MibigAPI.class.getName(), api, null);
						log.debug("Mibig Service started.");
					}

				});
	}

}
