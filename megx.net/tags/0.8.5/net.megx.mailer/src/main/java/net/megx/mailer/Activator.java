package net.megx.mailer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.megx.mailer.service.ErrorLoggingMailerService;
import net.megx.mailer.service.MailerServiceImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;

public class Activator extends ResTplConfiguredActivator {

    private Log log = LogFactory.getLog(getClass());

    @Override
    protected String getName() {
        return "net.megx.mailer";
    }

    @Override
    protected void onAppAdded(org.osgi.framework.BundleContext context,
            JCRApplication app) {
        super.onAppAdded(context, app);

        try {
            final Properties properties = this.readConfiguration();
            MailerServiceImpl mailerService = new MailerServiceImpl(properties);
            log.debug((String.format(
                    "Registering service instance %s of class (%s) as (%s)",
                    mailerService.toString(),
                    MailerServiceImpl.class.getName(),
                    BaseMailerService.class.getName())));

            RegUtils.reg(getBundleContext(), BaseMailerService.class.getName(),
                    mailerService, null);
        } catch (final Exception e) {
            log.error(
                    String.format(
                            "Starting of the service instance %s of class (%s) as (%s) "
                                    + "failed due to an error when initializing the service.",
                            this.getName(), MailerServiceImpl.class.getName(),
                            BaseMailerService.class.getName()), e);
            // when initializing the real mailer service fails, a dummy mailing
            // service is registered, which logs an ERROR for each mail which
            // should have been sent.
            RegUtils.reg(getBundleContext(), BaseMailerService.class.getName(),
                    new ErrorLoggingMailerService(), null);
        }

    };

    @Override
    protected void registerExtensions(JCRApplication arg0) {
        // TODO Auto-generated method stub

    }

    /**
     * Reads the configuration from {@code mailer.properties} which has to be
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
                    .getResourceAsStream("mailer.properties");

            if (inputStream == null) {
                throw new FileNotFoundException(
                        "property file mailer.properties not found in the classpath");
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
