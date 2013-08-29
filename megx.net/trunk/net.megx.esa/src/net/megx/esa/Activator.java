package net.megx.esa;

import net.megx.esa.rest.EarthSamplingAppAPI;
import net.megx.megdb.esa.EarthSamplingAppService;
import net.megx.utils.OSGIUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;

public class Activator extends ResTplConfiguredActivator {
	protected Log log = LogFactory.getLog(getClass());
	@Override
	protected void registerExtensions(JCRApplication app) {
		log.debug("ESA Starting up...");
		OSGIUtils.requestService(EarthSamplingAppService.class.getName(), 
				getBundleContext(), 
				new OSGIUtils.OnServiceAvailable<EarthSamplingAppService>() {

			@Override
			public void serviceAvailable(String name,
					EarthSamplingAppService service) {
				log.debug("EarthSamplingApp service received...");
				EarthSamplingAppAPI api = new EarthSamplingAppAPI(service);
				RegUtils.reg(getBundleContext(), EarthSamplingAppAPI.class.getName(), api, null);
				log.debug("Earth Sampling App API started.");
			}
			
		});
		log.debug("Earth Sampling App service requested.");
	}

	@Override
	protected String getName() {
		return "net.megx.esa";
	}
	
}
