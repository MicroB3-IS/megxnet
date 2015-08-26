package net.megx.mailer;

import javax.mail.MessagingException;

public interface BaseMailerService {
    
    /**
     * Property key used for the user name.
     */
    public static final String PROPERTY_KEY_USERNAME = "mail.smtp.auth.username";
    /**
     * Property key used for the password.
     */
    public static final String PROPERTY_KEY_PASSWORD = "mail.smtp.auth.password";
    /**
     * Property key used for the authentication flag.
     */
    public static final String PROPERTY_KEY_USE_AUTH = "mail.smtp.auth";
    /**
     * Property key used for the start TLS flag.
     */
    public static final String PROPERTY_KEY_STARTTLS = "mail.smtp.starttls.enable";
    /**
     * Property key used for the mail server host.
     */
    public static final String PROPERTY_KEY_HOST = "mail.smtp.host";
    /**
     * Property key used for the mail server port.
     */
    public static final String PROPERTY_KEY_PORT = "mail.smtp.port";

    public void send(final MailMessage message) throws MessagingException;

}
