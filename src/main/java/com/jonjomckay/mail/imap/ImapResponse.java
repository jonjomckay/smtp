package com.jonjomckay.mail.imap;

public class ImapResponse {
    private String thread;
    private String message;

    public ImapResponse(String thread, String message) {
        this.thread = thread;
        this.message = message;
    }

    public String getThread() {
        return thread;
    }

    public String getMessage() {
        return message;
    }
}
