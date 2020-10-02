package com.jonjomckay.mail.smtp;

import com.jonjomckay.mail.smtp.storage.MessageStore;
import com.sun.mail.smtp.SMTPMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class SmtpSession {
    private final static Logger LOGGER = LoggerFactory.getLogger(SmtpSession.class);


    private String from;
    private StringBuilder data = new StringBuilder();
    private Set<String> recipients = new HashSet<>();

    private final MessageStore messageStore;

    public SmtpSession(MessageStore messageStore) {
        this.messageStore = messageStore;
    }

    public void addData(String data) {
        this.data.append(data);
    }

    public void addRecipient(String recipient) {
        this.recipients.add(recipient);
    }

    public void reset() {
        this.from = null;
        this.data = new StringBuilder();
        this.recipients = new HashSet<>();
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void store() {
        // TODO: Can I use UTF-8?
        try {
            SMTPMessage smtpMessage = new SMTPMessage(null, new ByteArrayInputStream(data.toString().getBytes(StandardCharsets.UTF_8)));

            for (var address : smtpMessage.getAllRecipients()) {
                var recipient = (InternetAddress) address;
                if (recipient == null) {
                    continue;
                }

                // TODO: Check if recipient exists, and queue failure message back if it doesn't (async?)
                if (messageStore.doesMailboxExist(recipient.getAddress())) {
                    messageStore.store(recipient.getAddress(), smtpMessage);
                } else {
                    LOGGER.warn("Received a message for {}, but that mailbox does not exist", recipient.getAddress());
                }
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
