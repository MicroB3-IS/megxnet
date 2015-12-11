package net.megx.ws.data;

import net.megx.megdb.ws.data.WSDataService;
import net.megx.utils.OSGIUtils;
import net.megx.ws.data.app.WsDataApp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;

public class Activator extends ResTplConfiguredActivator {

    private Log log = LogFactory.getLog(getClass());

    

    @Override
    protected String getName() {
        return "net.megx.ws.data";
    }


    @Override
	protected void registerExtensions(JCRApplication app) {
		if(log.isDebugEnabled()){
			log.debug("Starting up net.megx.ws.data bundle...");
		}
		OSGIUtils.requestService(WSDataService.class.getName(),
				getBundleContext(),
				new OSGIUtils.OnServiceAvailable<WSDataService>() {

            @Override
            public void serviceAvailable( String name,
            		WSDataService service ) {
                if(log.isDebugEnabled()){
                	log.debug("WSDataService service received...");
                }
                
                WsDataApp wsDataApp = new WsDataApp(service);
                
                RegUtils.reg(getBundleContext(),
                		WsDataApp.class.getName(), wsDataApp, null);
            	
                if(log.isDebugEnabled()){
                	log.debug("net.megx.ws.data app started.");
                }
            }
        });
	}

}
