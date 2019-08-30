package com.server.interfaces;

public interface SocketInterface {
    void createAndListen(int port);
    String receive();
    void send(String data);
    void close();
}
