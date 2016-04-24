package net.megx.myosd;

import net.megx.megdb.myosd.MyOsdDbService;
import net.megx.megdb.myosd.impl.MyOsdDbServiceImpl;
import net.megx.utils.OSGIUtils;
import net.megx.ws.myosd.registry.MyOsdRegistry;
import net.megx.ws.myosd.registry.impl.MyOsdRegistryRestImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;

public class Activator extends ResTplConfiguredActivator {

  protected Log log = LogFactory.getLog(getClass());

  @Override
  protected void registerExtensions(JCRApplication app) {
    log.debug("MyOSD Registry Starting up...");
    // getting MegDb storage service
    // OSGIUtils.requestService(MyOsdDbService.class.getName(),
    // getBundleContext(),
    // new OSGIUtils.OnServiceAvailable<MyOsdDbService>() {
    //
    // @Override
    // public void serviceAvailable( String name,
    // MyOsdDbService service ) {
    // log.debug(" service received...");
    //MyOsdRegistryRestImpl api = new MyOsdRegistryRestImpl();
   // RegUtils.reg(getBundleContext(), MyOsdDbServiceImpl.class.getName(), api,
     //   null);
    // log.debug(" app started.");
    // }
    //
    // });
    log.debug("MyOSD Registry service started.");
  }
  
  protected void onAppAdded(org.osgi.framework.BundleContext context, JCRApplication app) {
    super.onAppAdded(context, app);
    MyOsdRegistryRestImpl api = new MyOsdRegistryRestImpl();
    RegUtils.reg(getBundleContext(), MyOsdRegistryRestImpl.class.getName(), api,null);
  };
  

  @Override
  protected String getName() {
    return "net.megx.myosd";
  }

}
