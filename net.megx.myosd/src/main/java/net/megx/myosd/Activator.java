package net.megx.myosd;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;

public class Activator extends ResTplConfiguredActivator {

    private Log log = LogFactory.getLog(getClass());

    

    @Override
    protected String getName() {
        return "myosd";
    }

}
