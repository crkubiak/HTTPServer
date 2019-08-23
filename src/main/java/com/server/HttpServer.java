package com.server;

import com.server.handlers.FileHandler;
import com.server.handlers.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class HttpServer {
    private int port;
    private Handler defaultHandler = null;
    // Two level map: first level is HTTP Method (GET, POST, OPTION, etc.), second level is the
    // request paths.
    private Map<String, Map<String, Handler>> handlers = new HashMap<String, Map<String, Handler>>();

    // TODO SSL support
    private HttpServer(int port)  {
        this.port = port;
    }

    /**
     * @param path if this is the special string "/*", this is the default handler if
     *   no other handler matches.
     */
    public void addHandler(String method, String path, Handler handler)  {
        Map<String, Handler> methodHandlers = handlers.get(method);
        if (methodHandlers == null)  {
            methodHandlers = new HashMap<String, Handler>();
            handlers.put(method, methodHandlers);
        }
        methodHandlers.put(path, handler);
    }

    public void start() throws IOException {
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

        //this needs to be a interface/class, not a method
        server.addHandler("GET", "/simple_get", (request, response) -> {
            String html = "";
            response.setResponseCode(200, "OK");
            response.addHeader("Content-Type", "text/html");
            response.addBody(html);
        });

        server.addHandler("HEAD", "/simple_get", (request, response) -> {
            response.setResponseCode(200, "OK");
            response.addHeader("Content-Type", "text/html");
        });

        server.addHandler("HEAD", "/get_with_body", (request, response) -> {
            response.setResponseCode(200, "OK");
            response.addHeader("Content-Type", "text/html");
        });

        server.addHandler("GET", "/get_with_body", (request, response) -> {
            response.setResponseCode(405, "METHOD NOT ALLOWED");
            response.addHeader("Content-Type", "text/html");
            response.addHeader("Allow", "HEAD, OPTIONS");
        });

        server.addHandler("GET", "/redirect", (request, response) -> {
            response.setResponseCode(301, "MOVED PERMANENTLY");
            response.addHeader("Location", "http://127.0.0.1:5000/simple_get");
            response.addHeader("Content-Type", "text/html");
        });

        server.addHandler("OPTIONS", "/method_options", (request, response) -> {
            response.setResponseCode(200, "OK");
            response.addHeader("Content-Type", "text/html");
            response.addHeader("Allow", "GET, HEAD, OPTIONS");
        });

        server.addHandler("OPTIONS", "/method_options2", (request, response) -> {
            response.setResponseCode(200, "OK");
            response.addHeader("Content-Type", "text/html");
            response.addHeader("Allow", "GET, HEAD, OPTIONS, PUT, POST");
        });

        server.addHandler("POST", "/echo_body", new Handler() {
            public void handle(Request request, Response response) throws IOException {
                StringBuffer buf = new StringBuffer();
                InputStream in = request.getBody();
                int c;
                while ((c = in.read()) != -1) {
                    buf.append((char) c);
                }
                String[] components = buf.toString().split("&");
                Map<String, String> urlParameters = new HashMap<String, String>();
                for (String component : components) {
                    String[] pieces = component.split("=");
                    urlParameters.put(pieces[0], pieces[1]);
                }
                String html = "<body>Welcome, " + urlParameters.get("username") + "</body>";

                response.setResponseCode(200, "OK");
                response.addHeader("Content-Type", "text/html");
                response.addBody(html);
            }
        });

        server.addHandler("GET", "/*", new FileHandler());  // Default handler
        server.start();
    }
}
