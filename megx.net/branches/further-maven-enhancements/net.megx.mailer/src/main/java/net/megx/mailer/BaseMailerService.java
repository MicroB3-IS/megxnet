package net.megx.mailer;

import javax.mail.MessagingException;

import org.json.JSONObject;

public interface BaseMailerService {

    public void send(final MailMessage message) throws MessagingException;

    /**
     * @deprecated will be removed in the near future.
     */
    @Deprecated
    public void sendMail(JSONObject config, String email, String name,
            String comment) throws Exception;

}
