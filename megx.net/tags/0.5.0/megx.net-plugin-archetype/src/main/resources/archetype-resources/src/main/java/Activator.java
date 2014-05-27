#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.${chon_activator};

public class Activator extends ${chon_activator} {

    private Log log = LogFactory.getLog(getClass());

    

    @Override
    protected String getName() {
        return "${artifactId}";
    }

}
