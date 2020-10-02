package com.jonjomckay.mail.imap;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.net.NetServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImapVerticle extends AbstractVerticle {
    private final static Logger LOGGER = LoggerFactory.getLogger(ImapVerticle.class);

    @Override
    public void start(Promise<Void> promise) throws Exception {
        NetServer netServer = vertx.createNetServer();
        netServer.connectHandler(socket -> {
            socket.closeHandler(event -> {
                LOGGER.debug("Socket close");
            });

            socket.exceptionHandler(e -> {
                LOGGER.error("Unhandled exception", e);
            });

//            var messageStore = new MaildirMessageStore(new File("/home/jonjo/tmp/maildir"));
//            var smtpSession = new SmtpSession(messageStore);
            var smtpHandler = new ImapHandler(socket);
//            var dataCommand = new AtomicBoolean(false);

            socket.handler(fullMessage -> {
                var message = fullMessage.toString().trim();

                LOGGER.trace(message);

                // TODO
                var spaceIndex = message.indexOf(" ");
                var thread = message.substring(0, spaceIndex);
                var command = message.substring(spaceIndex + 1);

                switch (command) {
                    default:
                        LOGGER.warn("The command {} is not supported", command);
                        smtpHandler.writeResponse(new ImapResponse(thread, "BAD Unsupported command"));
                        break;
                }
            });

            // Accept the connection by sending a 220 response
            smtpHandler.writeResponse(new ImapResponse("*", "OK IMAP4rev1 Service Ready"));
        });

        netServer.listen(1143, "localhost", event -> {
            if (event.failed()) {
                LOGGER.error("Unable to start the server", event.cause());
                promise.fail(event.cause());
                return;
            }

            LOGGER.info("Started server on port {}", event.result().actualPort());
            promise.complete();
        });
    }
}
