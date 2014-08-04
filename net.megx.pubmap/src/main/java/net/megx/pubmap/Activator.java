package net.megx.pubmap;

import net.megx.megdb.pubmap.PubMapService;
import net.megx.pubmap.rest.PubmapAPI;
import net.megx.utils.OSGIUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;

public class Activator extends ResTplConfiguredActivator {

	private static final Log log = LogFactory.getLog(Activator.class);

	@Override
	protected void registerExtensions(JCRApplication app) {
		log.debug("PubMapService starting up");
		OSGIUtils.requestService(PubMapService.class.getName(),
				getBundleContext(),
				new OSGIUtils.OnServiceAvailable<PubMapService>() {

					@Override
					public void serviceAvailable(String name,
							PubMapService service) {
						log.debug("PubMapService received...");
						PubmapAPI api = new PubmapAPI(service);
						RegUtils.reg(getBundleContext(),
								PubmapAPI.class.getName(), api, null);
						log.debug("PubMapService started.");
					}

				});
	}

	@Override
	protected String getName() {
		return "net.megx.pubmap";
	}

}
