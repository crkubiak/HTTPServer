package com.server.handlers;

import com.server.Request;
import com.server.Response;

public class NotImplementedHandler implements Handler {
    @Override
    public void handle(Request request, Response response) {
        response.setStatusCode(405, "METHOD NOT ALLOWED");
        response.addHeader("Content-Type", "text/html");
        response.addHeader("Allow", "HEAD, OPTIONS");
    }
}
