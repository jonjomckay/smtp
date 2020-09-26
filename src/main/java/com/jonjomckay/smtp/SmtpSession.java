package com.jonjomckay.smtp;

import com.jonjomckay.smtp.storage.MessageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class SmtpSession {
    private final static Logger LOGGER = LoggerFactory.getLogger(SmtpSession.class);

    private static AtomicLong counter = new AtomicLong(0);

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
            MimeMessage mimeMessage = new MimeMessage(null, new ByteArrayInputStream(data.toString().getBytes(StandardCharsets.UTF_8)));
            
            for (var recipient : recipients) {
                // TODO: Check if recipient exists, and queue failure message back if it doesn't (async?)

                var now = OffsetDateTime.now();

                var filename = String.format("%d.M%dP%d_%d.%s",
                        now.toEpochSecond(),
                        now.getNano() / 1000,
                        ProcessHandle.current().pid(),
                        counter.incrementAndGet(),
                        InetAddress.getLocalHost().getHostName()
                );

                LOGGER.debug("{}: {}", recipient, filename);
            }

            int i = 0;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
