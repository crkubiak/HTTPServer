package com.server.handlers;

import com.server.Request;
import com.server.Response;

public class RedirectHandler implements Handler {
    @Override
    public void handle(Request request, Response response) {
        response.setResponseCode(301, "MOVED PERMANENTLY");
        response.addHeader("Location", "http://127.0.0.1:5000/simple_get");
        response.addHeader("Content-Type", "text/html");
    }
}
