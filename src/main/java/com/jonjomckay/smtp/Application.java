package com.jonjomckay.smtp;

import com.jonjomckay.smtp.handlers.*;
import com.jonjomckay.smtp.storage.MaildirMessageStore;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class Application {
    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new AbstractVerticle() {
            @Override
            public void start(Promise<Void> startPromise) {
                NetServer netServer = vertx.createNetServer();
                netServer.connectHandler(socket -> {
                    socket.closeHandler(event -> {
                        LOGGER.debug("Socket close");
                    });

                    socket.exceptionHandler(e -> {
                        LOGGER.error("Unhandled exception", e);
                    });

                    var messageStore = new MaildirMessageStore(new File("/home/jonjo/tmp/maildir"));
                    var smtpSession = new SmtpSession(messageStore);
                    var smtpHandler = new SmtpHandler(socket);
                    var dataCommand = new AtomicBoolean(false);

                    socket.handler(fullMessage -> {
                        var message = fullMessage.toString();

                        LOGGER.trace(message);

                        if (dataCommand.get()) {
                            smtpSession.addData(message);

                            if (message.endsWith("\r\n.\r\n")) {
                                smtpSession.store();

                                smtpHandler.writeResponse(new Response(250, "OK: Queued as 12345"));

                                // We're no longer in a data command, and are able to handle other commands again
                                dataCommand.set(false);
                            }
                        } else {
                            message = message.trim();

                            // TODO
                            var command = message.substring(0, 4);

                            switch (command) {
                                case "HELO":
                                    smtpHandler.writeResponse(new HeloHandler(smtpSession).handle(message));
                                    break;
                                case "MAIL":
                                    smtpHandler.writeResponse(new MailHandler(smtpSession).handle(message));
                                    break;
                                case "RCPT":
                                    smtpHandler.writeResponse(new RcptHandler(smtpSession).handle(message));
                                    break;
                                case "DATA":
                                    // Mark this socket as "in" a DATA command, so we aren't trying to decode the follow-up packets as commands
                                    dataCommand.set(true);

                                    smtpHandler.writeResponse(new DataHandler(smtpSession).handle(message));
                                    break;
                                case "QUIT":
                                    smtpHandler.writeResponse(new QuitHandler(smtpSession).handle(message));
                                    break;
                                case "NOOP":
                                    smtpHandler.writeResponse(new NoopHandler(smtpSession).handle(message));
                                    break;
                                case "RSET":
                                    smtpHandler.writeResponse(new RsetHandler(smtpSession).handle(message));
                                    break;
                                case "VRFY":
                                    smtpHandler.writeResponse(new VrfyHandler(smtpSession).handle(message));
                                    break;
                                default:
                                    LOGGER.warn("The command {} is not supported", command);
                                    smtpHandler.writeResponse(new Response(502, "Unsupported command"));
                                    break;
                            }
                        }
                    });

                    // Accept the connection by sending a 220 response
                    smtpHandler.writeResponse(new Response(220, "Ready"));
                });

                netServer.listen(1025, "localhost", event -> {
                    if (event.failed()) {
                        LOGGER.error("Unable to start the server", event.cause());
                        startPromise.fail(event.cause());
                        return;
                    }

                    LOGGER.info("Started server on port {}", event.result().actualPort());
                    startPromise.complete();
                });
            }
        });
    }
}
