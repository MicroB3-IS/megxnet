package net.megx.contact;

import net.megx.contact.rest.ContactAPI;
import net.megx.megdb.contact.ContactService;
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
        return "net.megx.contact";
    }



	@Override
	protected void registerExtensions(JCRApplication app) {
		log.debug("ContactAPI starting up");
		OSGIUtils.requestService(ContactService.class.getName(),getBundleContext(),
				new OSGIUtils.OnServiceAvailable<ContactService>() {

					@Override
					public void serviceAvailable(String name,
							ContactService service) {
						log.debug("ContactService received...");
						ContactAPI contactAPI = new ContactAPI(service);
						RegUtils.reg(getBundleContext(), ContactAPI.class.getName(), contactAPI, null);
						log.debug("ContactApi started.");
						
					}
				});
	}

}
