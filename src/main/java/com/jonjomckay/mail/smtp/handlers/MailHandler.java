package com.jonjomckay.mail.smtp.handlers;

import com.jonjomckay.mail.smtp.SmtpResponse;
import com.jonjomckay.mail.smtp.SmtpSession;

public class MailHandler implements Handler {
    private final SmtpSession session;

    public MailHandler(SmtpSession session) {
        this.session = session;
    }

    @Override
    public SmtpResponse handle(String message) {
        this.session.setFrom(message);

        return new SmtpResponse(250, "OK");
    }
}
