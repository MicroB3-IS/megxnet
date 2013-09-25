package net.megx.ws.metagenome.traits;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;

public class Activator extends ResTplConfiguredActivator {

    private Log log = LogFactory.getLog(getClass());


    @Override
    protected void registerExtensions(JCRApplication app) {
        // TODO Auto-generated method stub
        
    }    

    @Override
    protected String getName() {
        return "net.megx.ws.mg-traits";
    }

}
