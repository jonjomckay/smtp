package com.jonjomckay.mail.smtp.handlers;

import com.jonjomckay.mail.smtp.SmtpResponse;
import com.jonjomckay.mail.smtp.SmtpSession;

public class VrfyHandler implements Handler {
    private final SmtpSession session;

    public VrfyHandler(SmtpSession session) {
        this.session = session;
    }

    @Override
    public SmtpResponse handle(String message) {
        return new SmtpResponse(252, "Nice try");
    }
}
