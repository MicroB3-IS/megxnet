package net.megx.mailer.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

import org.json.JSONObject;

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
     * Property key used for the user name.
     */
    private static final String PROPERTY_KEY_USERNAME = "mail.smtp.auth.username";
    /**
     * Property key used for the password.
     */
    private static final String PROPERTY_KEY_PASSWORD = "mail.smtp.auth.password";
    /**
     * Property key used for the authentication flag.
     */
    private static final String PROPERTY_KEY_USE_AUTH = "mail.smtp.auth";

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
     * Creates a new instance of the mailer service. The configuration is read
     * from the classpath.
     * 
     * @throws IOException
     *             if an IO error occurs while reading the configuration.
     * @see {@link #readConfiguration()}
     */
    public MailerServiceImpl() throws IOException {

        // read properties
        final Properties mailingProperties = readConfiguration();

        // read credentials
        final String username = mailingProperties
                .getProperty(PROPERTY_KEY_USERNAME);
        final String password = mailingProperties
                .getProperty(PROPERTY_KEY_PASSWORD);

        // remove credentials from properties before passing them to other
        // objects
        mailingProperties.remove(PROPERTY_KEY_USERNAME);
        mailingProperties.remove(PROPERTY_KEY_PASSWORD);

        // create session using credentials or not - depending on configuration
        if (Boolean.parseBoolean(mailingProperties
                .getProperty(PROPERTY_KEY_USE_AUTH))) {
            session = Session.getInstance(mailingProperties,
                    new PasswordAuthenticator(username, password));
        } else {
            session = Session.getInstance(mailingProperties);
        }
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

    /**
     * {@inheritDoc}
     * 
     * @throws MessagingException
     */
    @Override
    public void send(final MailMessage message) throws MessagingException {

        Message mimeMessage = new MimeMessage(this.session);
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

    @Override
    public void sendMail(JSONObject config, String email, String name,
            String comment) throws Exception {
        throw new RuntimeException(
                "No longer implemented. Will be removed soon. Use send(MailMessage) instead.");
    }
}