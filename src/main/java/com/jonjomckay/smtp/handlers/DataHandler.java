package com.jonjomckay.smtp.handlers;

import com.jonjomckay.smtp.Response;
import com.jonjomckay.smtp.SmtpSession;

public class DataHandler implements Handler {
    private final SmtpSession session;

    public DataHandler(SmtpSession session) {
        this.session = session;
    }

    @Override
    public Response handle(String message) {
        return new Response(354, "Start mail input; end with [CRLF].[CRLF]");
    }
}
