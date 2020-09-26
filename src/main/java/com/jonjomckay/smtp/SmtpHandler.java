package com.jonjomckay.smtp;

import io.vertx.core.Future;
import io.vertx.core.net.NetSocket;

public class SmtpHandler {
    private final NetSocket socket;

    public SmtpHandler(NetSocket socket) {
        this.socket = socket;
    }

    public Future<Void> writeResponse(Response response) {
        return socket.write(String.format("%d %s \r\n", response.getCode(), response.getMessage()));
    }
}
