package net.megx.ws.metagenome.traits;

import net.megx.megdb.mgtraits.MGTraitsService;
import net.megx.utils.OSGIUtils;
import net.megx.ws.mg.traits.rest.MGTraitsAPI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;

public class Activator extends ResTplConfiguredActivator {

    private Log log = LogFactory.getLog(getClass());


    @Override
    protected void registerExtensions(JCRApplication app) {
        log.debug("MGTraitsAPI starting up");
        OSGIUtils.requestService(MGTraitsService.class.getName(), 
				getBundleContext(), 
				new OSGIUtils.OnServiceAvailable<MGTraitsService>() {

			@Override
			public void serviceAvailable(String name,
					MGTraitsService service) {
				log.debug("MGTraitsService received...");
				MGTraitsAPI api = new MGTraitsAPI(service);
				RegUtils.reg(getBundleContext(), MGTraitsAPI.class.getName(), api, null);
				log.debug("MGTraitsAPI started.");
			}
			
		});
    }    

    @Override
    protected String getName() {
        return "net.megx.ws.mg-traits";
    }

}
