package com.jonjomckay.smtp.handlers;

import com.jonjomckay.smtp.Response;
import com.jonjomckay.smtp.SmtpSession;

public class RsetHandler implements Handler {
    private final SmtpSession session;

    public RsetHandler(SmtpSession session) {
        this.session = session;
    }

    @Override
    public Response handle(String message) {
        this.session.reset();

        return new Response(250, "OK");
    }
}
