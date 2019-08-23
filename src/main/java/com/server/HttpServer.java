package com.server;

import com.server.handlers.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {
    private int port;
    private Map<String, Map<String, Handler>> handlers = new HashMap<String, Map<String, Handler>>();

    private HttpServer(int port)  {
        this.port = port;
    }

    private void addHandler(String method, String path, Handler handler)  {
        Map<String, Handler> methodHandlers = handlers.get(method);
        if (methodHandlers == null)  {
            methodHandlers = new HashMap<String, Handler>();
            handlers.put(method, methodHandlers);
        }
        methodHandlers.put(path, handler);
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
        server.addHandler("OPTIONS", "/method_options2", new OptionsHandler2());

        server.addHandler("POST", "/echo_body", (request, response) -> {
            InputStream body = request.getBody();
            response.setResponseCode(200, "OK");
            response.addHeader("Content-Type", "text/html");
            response.addHeader("Allow", "GET, HEAD, OPTIONS, PUT, POST");
//            response.addBody("some body");
            response.addBody(String.valueOf(body.toString().getBytes()));
//            System.out.print(request.getBody());
        });

//        server.addHandler("POST", "/echo_body", new Handler() {
//            public void handle(Request request, Response response) throws IOException {
//                StringBuffer buf = new StringBuffer();
//                InputStream in = request.getBody();
//                int c;
//                while ((c = in.read()) != -1) {
//                    buf.append((char) c);
//                }
//                String[] components = buf.toString().split("&");
//                Map<String, String> urlParameters = new HashMap<String, String>();
//                for (String component : components) {
//                    String[] pieces = component.split("=");
//                    urlParameters.put(pieces[0], pieces[1]);
//                }
//                String html = "<body>Welcome, " + urlParameters.get("username") + "</body>";
//
//                response.setResponseCode(200, "OK");
//                response.addHeader("Content-Type", "text/html");
//                response.addBody(html);
//            }
//        });

        server.addHandler("POST", "/test", new FileHandler());  // Default handler
        server.start();
    }
}
