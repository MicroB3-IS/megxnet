package net.megx.mibig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.megx.megdb.mibig.MibigService;
import net.megx.mibig.rest.MibigAPI;
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
		return "net.megx.mibig";
	}

	@Override
	protected void registerExtensions(JCRApplication app) {
		log.debug("Mibig Service starting up");
		OSGIUtils.requestService(MibigService.class.getName(),
				getBundleContext(),
				new OSGIUtils.OnServiceAvailable<MibigService>() {

					@Override
					public void serviceAvailable(String name,
							MibigService service) {
						log.debug("Mibig Service received...");
					
            try {
              final Properties configuration = readConfiguration();
              MibigAPI api = new MibigAPI(service,configuration);
              RegUtils.reg(getBundleContext(),
                  MibigAPI.class.getName(), api, null);
              log.debug("Mibig Service started.");
            } catch (final Exception e) {
              log.error(
                      "Error while initializing MibigAPI.",
                      e);
              throw new RuntimeException(
                      "Error while initializing MibigAPI.",
                      e);
          }
						
					}

				});
	}
	
	 /**
   * Reads the configuration from {@code mibig.properties} which has to be
   * present in the classpath.
   * 
   * @throws IOException
   *             if an IO error occurs.
   * @throws FileNotFoundException
   *             if {@code mibig.properties} cannot be found in the
   *             classpath.
   */
  private final Properties readConfiguration() throws IOException {

      final Properties properties = new Properties();
      InputStream inputStream = null;

      try {
          inputStream = this.getClass().getClassLoader()
                  .getResourceAsStream("mibig.properties");

          if (inputStream == null) {
              throw new FileNotFoundException(
                      "property file mibig.properties not found in the classpath");
          }

          properties.load(inputStream);
      } finally {
          if (inputStream != null) {
              inputStream.close();
          }
      }
      return properties;
  }

}
