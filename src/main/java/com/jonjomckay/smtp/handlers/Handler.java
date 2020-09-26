package com.jonjomckay.smtp.handlers;

import com.jonjomckay.smtp.Response;

public interface Handler {
    Response handle(String message);
}
