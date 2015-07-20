package net.megx.mailer.service;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import net.megx.mailer.BaseMailerService;
import net.megx.mailer.MailMessage;

/**
 * Default implementation of the {@link BaseMailerService} interface to provide
 * a mailing service to all megx.net modules.
 * 
 * @author jgerken
 */
public class MailerServiceImpl implements BaseMailerService {

    /**
     * Provides a password authentication used by the jaxax.mail API.
     * 
     * @author jgerken
     */
    private static final class PasswordAuthenticator extends Authenticator {

        private final String username;
        private final String password;

        public PasswordAuthenticator(final String username,
                final String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }

    private final Session session;

    /**
     * Creates a new instance of the mailer service from the given
     * configuration.
     * 
     * @param configuration
     *            the configuration for the mailer service.
     * 
     * @throws IllegalArgumentException
     *             if the given {@code configuration} do not contain all
     *             required configurations.
     * @see {@link #verifyConfiguration(Properties)}
     */
    public MailerServiceImpl(final Properties configuration) throws IOException {

        this.verifyConfiguration(configuration);

        // read credentials
        final String username = configuration
                .getProperty(PROPERTY_KEY_USERNAME);
        final String password = configuration
                .getProperty(PROPERTY_KEY_PASSWORD);

        // remove credentials from properties before passing them to other
        // objects
        configuration.remove(PROPERTY_KEY_USERNAME);
        configuration.remove(PROPERTY_KEY_PASSWORD);

        // create session using credentials or not - depending on configuration
        if (Boolean.parseBoolean(configuration
                .getProperty(PROPERTY_KEY_USE_AUTH))) {
            session = Session.getInstance(configuration,
                    new PasswordAuthenticator(username, password));
        } else {
            session = Session.getInstance(configuration);
        }
    }

    private void verifyConfiguration(final Properties mailingProperties) {
        final StringBuilder errorMessage = new StringBuilder(
                "MailerService configuration is incomplete.");
        boolean error = false;

        if (mailingProperties.getProperty(PROPERTY_KEY_HOST) == null
                || mailingProperties.getProperty(PROPERTY_KEY_HOST).trim()
                        .isEmpty()) {
            errorMessage.append(" Missing configuration for host.");
            error = true;
        }
        if (mailingProperties.getProperty(PROPERTY_KEY_PORT) == null
                || mailingProperties.getProperty(PROPERTY_KEY_PORT).trim()
                        .isEmpty()) {
            errorMessage.append(" Missing configuration for port.");
            error = true;
        }
        if (mailingProperties.getProperty(PROPERTY_KEY_USE_AUTH) == null
                || mailingProperties.getProperty(PROPERTY_KEY_USE_AUTH).trim()
                        .isEmpty()) {
            errorMessage
                    .append(" Missing configuration whether authentication is required or not (use auth).");
            error = true;
        } else {
            if (Boolean.parseBoolean(mailingProperties
                    .getProperty(PROPERTY_KEY_USE_AUTH))) {
                if (mailingProperties.getProperty(PROPERTY_KEY_USERNAME) == null
                        || mailingProperties.getProperty(PROPERTY_KEY_USERNAME)
                                .trim().isEmpty()) {
                    errorMessage
                            .append(" Missing configuration for username (required for authentication).");
                    error = true;
                }
                if (mailingProperties.getProperty(PROPERTY_KEY_PASSWORD) == null
                        || mailingProperties.getProperty(PROPERTY_KEY_PASSWORD)
                                .trim().isEmpty()) {
                    errorMessage
                            .append(" Missing configuration for password (required for authentication).");
                    error = true;
                }
            }
        }

        if (error) {
            throw new IllegalArgumentException(errorMessage.toString());
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws MessagingException
     */
    @Override
    public void send(final MailMessage message) throws MessagingException {

        final Message mimeMessage = new MimeMessage(this.session);
        mimeMessage.setSubject(message.getSubject());
        mimeMessage.setText(message.getMessageBody());
        mimeMessage.setFrom(message.getSenderAddress());

        if (message.hasReceipients()) {
            mimeMessage
                    .setRecipients(RecipientType.TO, message.getRecipients());
        }

        if (message.hasCCReceipients()) {
            mimeMessage.setRecipients(RecipientType.CC,
                    message.getCCRecipients());
        }

        if (message.hasBCCReceipients()) {

            mimeMessage.setRecipients(RecipientType.BCC,
                    message.getBCCRecipients());
        }

        if (message.hasReplyToAddress()) {
            mimeMessage.setReplyTo(new InternetAddress[] { message
                    .getReplyToAddress() });
        }

        Transport.send(mimeMessage);

    }

}