package com.jonjomckay.mail.smtp.handlers;

import com.jonjomckay.mail.smtp.SmtpResponse;
import com.jonjomckay.mail.smtp.SmtpSession;

public class DataHandler implements Handler {
    private final SmtpSession session;

    public DataHandler(SmtpSession session) {
        this.session = session;
    }

    @Override
    public SmtpResponse handle(String message) {
        return new SmtpResponse(354, "Start mail input; end with [CRLF].[CRLF]");
    }
}
