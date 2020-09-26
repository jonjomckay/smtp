package com.jonjomckay.smtp.storage;

import com.jonjomckay.smtp.SmtpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class MaildirMessageStore implements MessageStore {
    private final static AtomicLong COUNTER = new AtomicLong(0);
    private final static Logger LOGGER = LoggerFactory.getLogger(SmtpSession.class);

    private final File directory;

    public MaildirMessageStore(File directory) {
        this.directory = directory;
    }

    @Override
    public boolean doesMailboxExist(String address) {
        return Files.exists(Paths.get(directory.getAbsolutePath(), address));
    }

    @Override
    public void store(String address, MimeMessage message) {
        var now = OffsetDateTime.now();

        String filename;
        try {
            filename = String.format("%d.M%dP%d_%d.%s",
                    now.toEpochSecond(),
                    now.getNano() / 1000,
                    ProcessHandle.current().pid(),
                    COUNTER.incrementAndGet(),
                    InetAddress.getLocalHost().getHostName()
            );

            LOGGER.debug("{}: {}", address, filename);
        } catch (UnknownHostException e) {
            LOGGER.error("Unable to determine the current machine's hostname", e);
            return;
        }

        // First, write the message to $MAILDIR/tmp
        var outputPathTmp = Paths.get(directory.getAbsolutePath(), address, "tmp", filename);
        var outputPathNew = Paths.get(directory.getAbsolutePath(), address, "new", filename);
        var outputFileTmp = outputPathTmp.toFile();
        var outputFileNew = outputPathNew.toFile();

        // Make sure the $MAILDIR/tmp and $MAILDIR/new directories definitely exist
        outputFileTmp.getParentFile().mkdirs();
        outputFileNew.getParentFile().mkdirs();

        try (var outputStream = new FileOutputStream(outputFileTmp)) {
            message.writeTo(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        // Then move the message to $MAILDIR/new
        try {
            Files.move(outputPathTmp, outputPathNew);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
