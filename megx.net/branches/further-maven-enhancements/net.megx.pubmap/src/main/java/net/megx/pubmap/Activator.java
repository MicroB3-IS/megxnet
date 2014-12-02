package net.megx.pubmap;

import net.megx.megdb.pubmap.PubMapService;
import net.megx.pubmap.geonames.GeonamesService;
import net.megx.pubmap.geonames.GeonamesServiceImpl;
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
	protected void onAppAdded(org.osgi.framework.BundleContext context,
			JCRApplication app) {
		super.onAppAdded(context, app);

		GeonamesServiceImpl geonamesService = new GeonamesServiceImpl();

		log.debug((String.format(
				"Registering service instance %s of class (%s) as (%s)",
				geonamesService.toString(),
				GeonamesServiceImpl.class.getName(),
				GeonamesService.class.getName())));

		RegUtils.reg(getBundleContext(), GeonamesService.class.getName(),
				geonamesService, null);
	};

	@Override
	protected void registerExtensions(JCRApplication app) {
		log.debug("PubMapService starting up");
		OSGIUtils.requestService(PubMapService.class.getName(),
				getBundleContext(),
				new OSGIUtils.OnServiceAvailable<PubMapService>() {

					@Override
					public void serviceAvailable(String name,
							final PubMapService service) {
						log.debug("PubMapService received...");

						log.debug("Requesting GeonamesService service now...");
						OSGIUtils.requestService(
								GeonamesService.class.getName(),
								getBundleContext(),
								new OSGIUtils.OnServiceAvailable<GeonamesService>() {

									@Override
									public void serviceAvailable(String name,
											GeonamesService geonamesService) {
										log.debug("GeonamesService service received...");

										PubmapAPI api = new PubmapAPI(service,
												geonamesService);
										RegUtils.reg(getBundleContext(),
												PubmapAPI.class.getName(), api,
												null);

										log.debug("PubMapService started.");

									}

								});
					}

				});
	}

	@Override
	protected String getName() {
		return "net.megx.pubmap";
	}

}
