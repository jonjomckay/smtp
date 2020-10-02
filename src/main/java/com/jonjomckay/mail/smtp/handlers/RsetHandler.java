package com.jonjomckay.mail.smtp.handlers;

import com.jonjomckay.mail.smtp.SmtpResponse;
import com.jonjomckay.mail.smtp.SmtpSession;

public class RsetHandler implements Handler {
    private final SmtpSession session;

    public RsetHandler(SmtpSession session) {
        this.session = session;
    }

    @Override
    public SmtpResponse handle(String message) {
        this.session.reset();

        return new SmtpResponse(250, "OK");
    }
}
