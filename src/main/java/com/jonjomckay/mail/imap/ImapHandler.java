package com.jonjomckay.mail.imap;

import io.vertx.core.Future;
import io.vertx.core.net.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImapHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(ImapHandler.class);

    private final NetSocket socket;

    public ImapHandler(NetSocket socket) {
        this.socket = socket;
    }

    public Future<Void> writeResponse(ImapResponse response) {
        var message = String.format("%s %s \r\n", response.getThread(), response.getMessage());

        LOGGER.trace("Sent response: {}", message);

        return socket.write(message);
    }
}
