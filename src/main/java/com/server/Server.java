package com.server;

import com.server.wrappers.ServerSocketWrapper;
import com.server.wrappers.SocketInterface;

import java.io.IOException;

public class Server {
    SocketInterface socketWrapper;

    public Server(SocketInterface socketWrapper) {
        this.socketWrapper = socketWrapper;
    }

    public void start(int port) {
        socketWrapper.createAndListen(port);
        run();
    }

    private void run() {
        String message;
        while((message = socketWrapper.receive()) != null) {
            if (message.equals("bye")) {
                socketWrapper.send(message);
                break;
            } else {
                socketWrapper.send(message);
            }
            stop();
        }
    }

    private void stop() {
        socketWrapper.close();
    }

}