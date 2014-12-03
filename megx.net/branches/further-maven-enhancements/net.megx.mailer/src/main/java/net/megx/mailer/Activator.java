package net.megx.mailer;

import java.io.IOException;

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
            MailerServiceImpl mailerService = new MailerServiceImpl();
            log.debug((String.format(
                    "Registering service instance %s of class (%s) as (%s)",
                    mailerService.toString(),
                    MailerServiceImpl.class.getName(),
                    BaseMailerService.class.getName())));

            RegUtils.reg(getBundleContext(), BaseMailerService.class.getName(),
                    mailerService, null);
        } catch (final IOException e) {
            log.error(
                    String.format(
                            "Starting of the service instance %s of class (%s) as (%s) "
                                    + "failed due to an IO error when reading the configuration file",
                            this.getName(), MailerServiceImpl.class.getName(),
                            BaseMailerService.class.getName()), e);
        }

    };

    @Override
    protected void registerExtensions(JCRApplication arg0) {
        // TODO Auto-generated method stub

    }
}
