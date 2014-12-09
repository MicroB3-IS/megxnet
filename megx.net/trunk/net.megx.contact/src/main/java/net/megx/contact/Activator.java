package net.megx.contact;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.megx.contact.rest.ContactAPI;
import net.megx.mailer.BaseMailerService;
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
        OSGIUtils.requestService(ContactService.class.getName(),
                getBundleContext(),
                new OSGIUtils.OnServiceAvailable<ContactService>() {

                    @Override
                    public void serviceAvailable(String name,
                            final ContactService service) {
                        log.debug("ContactService received...");

                        log.debug("Requesting MailerService service now...");
                        OSGIUtils.requestService(
                                BaseMailerService.class.getName(),
                                getBundleContext(),
                                new OSGIUtils.OnServiceAvailable<BaseMailerService>() {

                                    @Override
                                    public void serviceAvailable(String name,
                                            BaseMailerService mailerService) {
                                        log.debug("MailerService service received...");

                                        try {
                                            final Properties configuration = readConfiguration();
                                            ContactAPI contactAPI = new ContactAPI(
                                                    service, mailerService,
                                                    configuration);
                                            RegUtils.reg(getBundleContext(),
                                                    ContactAPI.class.getName(),
                                                    contactAPI, null);
                                            log.debug("ContactApi started.");
                                        } catch (final Exception e) {
                                            log.error(
                                                    "Error while initializing ContactAPI.",
                                                    e);
                                            throw new RuntimeException(
                                                    "Error while initializing ContactAPI.",
                                                    e);
                                        }
                                    }

                                });
                    }
                });
    }

    /**
     * Reads the configuration from {@code contact.properties} which has to be
     * present in the classpath.
     * 
     * @throws IOException
     *             if an IO error occurs.
     * @throws FileNotFoundException
     *             if {@code mailer.properties} cannot be found in the
     *             classpath.
     */
    private final Properties readConfiguration() throws IOException {

        final Properties properties = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = this.getClass().getClassLoader()
                    .getResourceAsStream("contact.properties");

            if (inputStream == null) {
                throw new FileNotFoundException(
                        "property file contact.properties not found in the classpath");
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
