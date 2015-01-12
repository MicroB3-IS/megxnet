package net.megx.mailer;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Message to be sent by mail.
 * 
 * @author jgerken
 */
public class MailMessage {

    private final InternetAddress fromAddress;
    private InternetAddress replyToAddress;
    private final List<InternetAddress> recipients = new ArrayList<InternetAddress>();
    private final List<InternetAddress> ccRecipients = new ArrayList<InternetAddress>();
    private final List<InternetAddress> bccRecipients = new ArrayList<InternetAddress>();
    private String subject;
    private String message;

    /**
     * Creates a new message.
     * 
     * @param senderAddress
     *            the email address which will be used as sender.
     * @throws AddressException
     *             if the given {@code fromAddress} is no valid email address.
     * @throws IllegalArgumentException
     *             if {@code fromAddress} is {@code null} or empty.
     */
    public MailMessage(final String senderAddress) throws AddressException {
        if (senderAddress == null || senderAddress.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "fromAddress must not be null or empty.");
        }
        this.fromAddress = new InternetAddress(senderAddress);
    }

    /**
     * Sets the subject of the message.
     * 
     * @param subject
     *            the subject.
     * @throws IllegalArgumentException
     *             if {@code fromAddress} is {@code null} or empty.
     */
    public void setSubject(final String subject) {
        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "subject must not be null or empty.");
        }
        this.subject = subject;
    }

    /**
     * Sets the body of the message.
     * 
     * @param messageBody
     *            the message body.
     * @throws IllegalArgumentException
     *             if {@code messageBody} is {@code null} or empty.
     */
    public void setMessageBody(final String messageBody) {
        if (messageBody == null || messageBody.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "message must not be null or empty.");
        }
        this.message = messageBody;
    }

    /**
     * Sets the reply to address for the message. This method needs to be called
     * only in cases where the {@code reply to} address differs {@code from} the
     * from address.
     * 
     * @param replyToAddress
     *            the reply to address.
     * @throws AddressException
     *             if the given {@code replyToAddress} is not a valid email
     *             address.
     * @throws IllegalArgumentException
     *             if {@code fromAddress} is {@code null} or empty.
     */
    public void setReplyToAddress(final String replyToAddress)
            throws AddressException {
        if (replyToAddress == null || replyToAddress.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "replyToAddress must not be null.");
        }
        this.replyToAddress = new InternetAddress(replyToAddress);
    }

    /**
     * Adds the recipients for the mail. {@code recipientList} can hold a single
     * mail address (e.g. {@code contact@megx.net} or a comma-separated list of
     * addresses (e.g. {@code contact@megx.net,help@megx.net}.
     * 
     * @param recipientAddress
     *            the email address(es) of the new recipient(s).
     * @throws AddressException
     *             if the given {@code recipientList} is not a valid email
     *             address or list of email addresses.
     * @throws IllegalArgumentException
     *             if {@code recipientAddress} is {@code null} or empty.
     */
    public void addRecipients(final String recipientList)
            throws AddressException {
        if (recipientList == null || recipientList.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "recipientList must not be null or empty.");
        }

        final InternetAddress[] recipientAddresses = InternetAddress
                .parse(recipientList);

        for (InternetAddress recipientAddress : recipientAddresses) {
            this.recipients.add(recipientAddress);
        }
    }

    /**
     * Adds the cc-recipients for the mail. {@code ccRecipientList} can hold a
     * single mail address (e.g. {@code contact@megx.net} or a comma-separated
     * list of addresses (e.g. {@code contact@megx.net,help@megx.net}.
     * 
     * @param ccRecipientList
     *            the email address(es) of the new cc-recipient(s).
     * @throws AddressException
     *             if the given {@code ccRecipientList} is not a valid email
     *             address or list of email addresses.
     * @throws IllegalArgumentException
     *             if {@code ccRecipientAddress} is {@code null} or empty.
     */
    public void addCCReceipient(final String ccRecipientList)
            throws AddressException {
        if (ccRecipientList == null || ccRecipientList.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "ccRecipientList must not be null or empty.");
        }
        
        final InternetAddress[] recipientAddresses = InternetAddress
                .parse(ccRecipientList);

        for (InternetAddress recipientAddress : recipientAddresses) {
            this.ccRecipients.add(recipientAddress);
        }
    }

    /**
     * Adds the bcc-recipients for the mail. {@code bccRecipientList} can hold a
     * single mail address (e.g. {@code contact@megx.net} or a comma-separated
     * list of addresses (e.g. {@code contact@megx.net,help@megx.net}.
     * 
     * @param bccRecipientList
     *            the email address(es) of the new cc-recipient(s).
     * @throws AddressException
     *             if the given {@code ccRecipientList} is not a valid email
     *             address or list of email addresses.
     * @throws IllegalArgumentException
     *             if {@code ccRecipientAddress} is {@code null} or empty.
     */
    public void addBCCRecipient(final String bccRecipientList)
            throws AddressException {
        if (bccRecipientList == null || bccRecipientList.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "bccRecipientList must not be null or empty.");
        }
        
        final InternetAddress[] recipientAddresses = InternetAddress
                .parse(bccRecipientList);

        for (InternetAddress recipientAddress : recipientAddresses) {
            this.bccRecipients.add(recipientAddress);
        }
    }

    /**
     * Gets the subject of the message.
     * 
     * @return the subject.
     */
    public String getSubject() {
        return this.subject;
    }

    /**
     * Gets the body of the message.
     * 
     * @return the message body.
     */
    public String getMessageBody() {
        return this.message;
    }

    /**
     * Gets the sender address of the message.
     * 
     * @return the sender address.
     */
    public InternetAddress getSenderAddress() {
        return this.fromAddress;
    }

    /**
     * Gets the reply-to address of the message.
     * 
     * @return the reploy-to address of the message or {@code null} if no
     *         reply-to address has been set.
     */
    public InternetAddress getReplyToAddress() {
        return this.replyToAddress;
    }

    /**
     * Tests if a reply-to address has been specified for the message.
     * 
     * @return {@code true} if a reply-to address has been specified for the
     *         message, {@code false} otherwise.
     */
    public boolean hasReplyToAddress() {
        return this.replyToAddress != null;
    }

    /**
     * Gets the recipients of this message.
     * 
     * @return the recipients of this message or an empty array if
     *         {@link #hasReceipients()} {@code == false}.
     */
    public InternetAddress[] getRecipients() {
        return this.recipients.toArray(new InternetAddress[this.recipients
                .size()]);
    }

    /**
     * Gets the cc-recipients of this message.
     * 
     * @return the recipients of this message or an empty array if
     *         {@link #hasCCReceipients()} {@code == false}.
     */
    public InternetAddress[] getCCRecipients() {
        return this.ccRecipients.toArray(new InternetAddress[this.ccRecipients
                .size()]);
    }

    /**
     * Gets the bcc-recipients of this message.
     * 
     * @return the bcc-recipients of this message or an empty array if
     *         {@link #hasBCCReceipients()} {@code == false}.
     */
    public InternetAddress[] getBCCRecipients() {
        return this.bccRecipients
                .toArray(new InternetAddress[this.bccRecipients.size()]);
    }

    /**
     * Tests if a recipient ({@code to} address) has been specified for the
     * message.
     * 
     * @return {@code true} if a recipient ({@code to} address) has been
     *         specified for the message, {@code false} otherwise.
     */
    public boolean hasReceipients() {
        return this.recipients.size() > 0;
    }

    /**
     * Tests if a cc-recipient address has been specified for the message.
     * 
     * @return {@code true} if a cc-recipient address has been specified for the
     *         message, {@code false} otherwise.
     */
    public boolean hasCCReceipients() {
        return this.ccRecipients.size() > 0;
    }

    /**
     * Tests if a bcc-recipient address has been specified for the message.
     * 
     * @return {@code true} if a bcc-recipient address has been specified for
     *         the message, {@code false} otherwise.
     */
    public boolean hasBCCReceipients() {
        return this.bccRecipients.size() > 0;
    }

}
