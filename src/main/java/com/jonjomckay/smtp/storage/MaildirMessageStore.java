package com.jonjomckay.smtp.storage;

import java.io.File;

public class MaildirMessageStore implements MessageStore {
    private final File directory;

    public MaildirMessageStore(File directory) {
        this.directory = directory;
    }
}
