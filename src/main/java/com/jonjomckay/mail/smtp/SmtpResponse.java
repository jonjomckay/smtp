package com.jonjomckay.mail.smtp;

public class SmtpResponse {
    private int code;
    private String message;

    public SmtpResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
