package com.server;

import java.net.ServerSocket;

public class HTTPServer {
    public static void main(String[] args) throws Exception {

        final ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Listening for a connection on port 8080...");
        while (true) {

        }
    }
}