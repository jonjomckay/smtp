package com.jonjomckay.mail.smtp.handlers;

import com.jonjomckay.mail.smtp.SmtpResponse;
import com.jonjomckay.mail.smtp.SmtpSession;

public class NoopHandler implements Handler {
    private final SmtpSession session;

    public NoopHandler(SmtpSession session) {
        this.session = session;
    }

    @Override
    public SmtpResponse handle(String message) {
        return new SmtpResponse(250, "OK");
    }
}
