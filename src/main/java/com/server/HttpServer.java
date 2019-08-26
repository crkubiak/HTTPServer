package com.server;

import com.server.handlers.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {
    private int port;
    private Map<String, Map<String, Handler>> handlers = new HashMap<>();

    private HttpServer(int port)  {
        this.port = port;
    }

    private void addHandler(String method, String path, Handler handler)  {
        Map<String, Handler> methodHandlers = handlers.computeIfAbsent(method, k -> new HashMap<>());
        methodHandlers.put(path, handler);
        System.out.println("Key: " + methodHandlers.keySet().toString());
        System.out.println("Value: " + methodHandlers.values().toString());
    }

    private void start() throws IOException {
        ServerSocket socket = new ServerSocket(port);
        System.out.println("Listening on port " + port);
        Socket client;
        while ((client = socket.accept()) != null)  {
            System.out.println("Received connection from " + client.getRemoteSocketAddress().toString());
            SocketHandler handler = new SocketHandler(client, handlers);
            Thread t = new Thread(handler);
            t.start();
        }
    }

    public static void main(String[] args) throws IOException  {
        HttpServer server = new HttpServer(5000);

        server.addHandler("GET", "/simple_get", new GetHandler());
        server.addHandler("HEAD", "/simple_get", new HeadHandler());
        server.addHandler("HEAD", "/get_with_body", new HeadHandler());
        server.addHandler("GET", "/get_with_body", new NotImplementedHandler());
        server.addHandler("GET", "/redirect", new RedirectHandler());
        server.addHandler("OPTIONS", "/method_options", new OptionsHandler());
        server.addHandler("GET", "/method_options", new GetHandler());
        server.addHandler("HEAD", "/method_options", new HeadHandler());
        server.addHandler("OPTIONS", "/method_options2", new OptionsHandler2());
        server.addHandler("POST", "/echo_body", new PostHandler());

        server.start();
    }
}
