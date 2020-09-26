package com.jonjomckay.smtp.handlers;

import com.jonjomckay.smtp.Response;
import com.jonjomckay.smtp.SmtpSession;

public class NoopHandler implements Handler {
    private final SmtpSession session;

    public NoopHandler(SmtpSession session) {
        this.session = session;
    }

    @Override
    public Response handle(String message) {
        return new Response(250, "OK");
    }
}
