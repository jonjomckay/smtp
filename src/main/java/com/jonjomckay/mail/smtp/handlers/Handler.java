package com.jonjomckay.mail.smtp.handlers;

import com.jonjomckay.mail.smtp.SmtpResponse;

public interface Handler {
    SmtpResponse handle(String message);
}
