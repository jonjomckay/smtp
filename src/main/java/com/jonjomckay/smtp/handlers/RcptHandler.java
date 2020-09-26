package com.jonjomckay.smtp.handlers;

import com.jonjomckay.smtp.Response;
import com.jonjomckay.smtp.SmtpSession;

public class RcptHandler implements Handler {
    private final SmtpSession session;

    public RcptHandler(SmtpSession session) {
        this.session = session;
    }

    @Override
    public Response handle(String message) {
        this.session.addRecipient(message);

        return new Response(250, "OK");
    }
}
