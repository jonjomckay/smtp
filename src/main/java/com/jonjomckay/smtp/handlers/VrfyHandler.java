package com.jonjomckay.smtp.handlers;

import com.jonjomckay.smtp.Response;
import com.jonjomckay.smtp.SmtpSession;

public class VrfyHandler implements Handler {
    private final SmtpSession session;

    public VrfyHandler(SmtpSession session) {
        this.session = session;
    }

    @Override
    public Response handle(String message) {
        return new Response(252, "Nice try");
    }
}
