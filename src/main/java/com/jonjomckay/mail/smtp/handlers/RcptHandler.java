package com.jonjomckay.mail.smtp.handlers;

import com.jonjomckay.mail.smtp.SmtpResponse;
import com.jonjomckay.mail.smtp.SmtpSession;

public class RcptHandler implements Handler {
    private final SmtpSession session;

    public RcptHandler(SmtpSession session) {
        this.session = session;
    }

    @Override
    public SmtpResponse handle(String message) {
        this.session.addRecipient(message);

        return new SmtpResponse(250, "OK");
    }
}
