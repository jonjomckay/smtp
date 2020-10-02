package com.jonjomckay.mail.smtp.handlers;

import com.jonjomckay.mail.smtp.SmtpResponse;
import com.jonjomckay.mail.smtp.SmtpSession;

public class HeloHandler implements Handler {
    private final SmtpSession session;

    public HeloHandler(SmtpSession session) {
        this.session = session;
    }

    @Override
    public SmtpResponse handle(String message) {
        return new SmtpResponse(250, "kuashaonline.com You are not kicked off :)");
    }
}
