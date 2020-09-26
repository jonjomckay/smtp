package com.jonjomckay.smtp.handlers;

import com.jonjomckay.smtp.Response;
import com.jonjomckay.smtp.SmtpSession;

public class MailHandler implements Handler {
    private final SmtpSession session;

    public MailHandler(SmtpSession session) {
        this.session = session;
    }

    @Override
    public Response handle(String message) {
        this.session.setFrom(message);

        return new Response(250, "OK");
    }
}
