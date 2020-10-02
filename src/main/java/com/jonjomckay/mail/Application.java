package com.jonjomckay.mail;

import com.jonjomckay.mail.imap.ImapVerticle;
import com.jonjomckay.mail.smtp.SmtpVerticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ImapVerticle());
        vertx.deployVerticle(new SmtpVerticle());
    }
}
