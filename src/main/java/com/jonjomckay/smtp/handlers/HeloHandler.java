package com.jonjomckay.smtp.handlers;

import com.jonjomckay.smtp.Response;
import com.jonjomckay.smtp.SmtpSession;

public class HeloHandler implements Handler {
    private final SmtpSession session;

    public HeloHandler(SmtpSession session) {
        this.session = session;
    }

    @Override
    public Response handle(String message) {
        return new Response(250, "kuashaonline.com You are not kicked off :)");
    }
}
