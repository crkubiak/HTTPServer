package com.server.wrappers;

public interface SocketInterface {
    void createAndListen(int port);
    String receive();
    void send(String data);
    void close();
}
