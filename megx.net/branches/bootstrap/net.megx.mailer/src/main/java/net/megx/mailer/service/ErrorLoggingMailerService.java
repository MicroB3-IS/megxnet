package net.megx.mailer.service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import net.megx.mailer.BaseMailerService;
import net.megx.mailer.MailMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ErrorLoggingMailerService implements BaseMailerService {
    
    private static final String NEWLINE = System.getProperty("line.separator");
    
    private final Log log = LogFactory.getLog(getClass());


    @Override
    public void send(MailMessage message) throws MessagingException {
        
        final StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("MailerService is not configured correctly. Could not send the following mail:");
        errorMessage.append(NEWLINE);
        errorMessage.append("FROM: ");
        errorMessage.append(message.getSenderAddress());
        errorMessage.append(NEWLINE);
        errorMessage.append("TO: ");
        for (final InternetAddress recipient : message.getRecipients()) {
            errorMessage.append(recipient.toString());
            errorMessage.append(",");
        }
        errorMessage.append(NEWLINE);
        errorMessage.append("CC: ");
        for (final InternetAddress recipient : message.getCCRecipients()) {
            errorMessage.append(recipient.toString());
            errorMessage.append(",");
        }
        errorMessage.append(NEWLINE);
        errorMessage.append("BCC: ");
        for (final InternetAddress recipient : message.getBCCRecipients()) {
            errorMessage.append(recipient.toString());
            errorMessage.append(",");
        }
        errorMessage.append(NEWLINE);
        errorMessage.append("SUBJECT: ");
        errorMessage.append(message.getSubject());
        errorMessage.append(NEWLINE);
        errorMessage.append("CONTENT: ");
        errorMessage.append(message.getMessageBody());
        log.error(errorMessage.toString());
    }


}
