package com.jonjomckay.smtp.handlers;

import com.jonjomckay.smtp.Response;
import com.jonjomckay.smtp.SmtpSession;

public class QuitHandler implements Handler {
    private final SmtpSession session;

    public QuitHandler(SmtpSession session) {
        this.session = session;
    }

    @Override
    public Response handle(String message) {
        return new Response(221, "See ya");
    }
}
