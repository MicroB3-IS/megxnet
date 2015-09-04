package net.megx.pubmap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.megx.mailer.BaseMailerService;
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
        geonamesService.toString(), GeonamesServiceImpl.class.getName(),
        GeonamesService.class.getName())));

    RegUtils.reg(getBundleContext(), GeonamesService.class.getName(),
        geonamesService, null);
  };

  @Override
  protected void registerExtensions(JCRApplication app) {
    log.debug("PubMapService starting up");
    OSGIUtils.requestService(PubMapService.class.getName(), getBundleContext(),
        new OSGIUtils.OnServiceAvailable<PubMapService>() {

          @Override
          public void serviceAvailable(String name, final PubMapService service) {
            log.debug("PubMapService received...");

            log.debug("Requesting GeonamesService service now...");
            OSGIUtils.requestService(GeonamesService.class.getName(),
                getBundleContext(),
                new OSGIUtils.OnServiceAvailable<GeonamesService>() {

                  @Override
                  public void serviceAvailable(String name,
                      final GeonamesService geonamesService) {
                    log.debug("GeonamesService service received...");

                    log.debug("Requesting MailerService service now...");
                    OSGIUtils.requestService(BaseMailerService.class.getName(),
                        getBundleContext(),
                        new OSGIUtils.OnServiceAvailable<BaseMailerService>() {

                          @Override
                          public void serviceAvailable(String name,
                              BaseMailerService mailerService) {
                            log.debug("MailerService service received...");

                            try {
                              final Properties configuration = readConfiguration();

                              PubmapAPI api = new PubmapAPI(service,
                                  geonamesService, mailerService, configuration);
                              RegUtils.reg(getBundleContext(),
                                  PubmapAPI.class.getName(), api, null);

                              log.debug("PubMapService started.");
                            } catch (final Exception e) {
                              log.error(
                                  "Error while initializing PubMapService.", e);
                              throw new RuntimeException(
                                  "Error while initializing PubMapService.", e);
                            }
                          }

                        });
                  }

                });
          }

        });
  }

  /**
   * Reads the configuration from {@code pubmap.properties} which has to be
   * present in the classpath.
   * 
   * @throws IOException
   *           if an IO error occurs.
   * @throws FileNotFoundException
   *           if {@code pubmap.properties} cannot be found in the classpath.
   */
  private final Properties readConfiguration() throws IOException {

    final Properties properties = new Properties();
    InputStream inputStream = null;

    try {
      inputStream = this.getClass().getClassLoader()
          .getResourceAsStream("pubmap.properties");

      if (inputStream == null) {
        throw new FileNotFoundException(
            "property file pubmap.properties not found in the classpath");
      }

      properties.load(inputStream);
    } finally {
      if (inputStream != null) {
        inputStream.close();
      }
    }
    return properties;
  }

  @Override
  protected String getName() {
    return "net.megx.pubmap";
  }

}
