package com.server.handlers;

import com.server.Request;
import com.server.Response;

import java.io.IOException;

public class OptionsHandler implements Handler {
    @Override
    public void handle(Request request, Response response) {
        response.setResponseCode(200, "OK");
        response.addHeader("Content-Type", "text/html");
        response.addHeader("Allow", "GET, HEAD, OPTIONS");
    }
}
