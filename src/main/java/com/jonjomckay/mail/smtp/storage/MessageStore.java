package com.jonjomckay.mail.smtp.storage;

import javax.mail.internet.MimeMessage;

public interface MessageStore {
    boolean doesMailboxExist(String address);
    void store(String address, MimeMessage message);
}
