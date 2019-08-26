package com.server.handlers;

import com.server.Request;
import com.server.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PostHandler implements Handler {
    @Override
    public void handle(Request request, Response response) throws IOException {
        InputStream body = request.getBody();
        InputStreamReader test = new InputStreamReader(body);
        BufferedReader buffy = new BufferedReader(test);
        response.setResponseCode(200, "OK");
        response.addHeader("Content-Type", "text/html");
        response.addHeader("Allow", "GET, HEAD, OPTIONS, PUT, POST");
        String line;
        boolean firstLine = true;
        StringBuilder builtResponse = new StringBuilder();
        while ((line = buffy.readLine()) != null) {
            if (firstLine) {
                builtResponse.append(line);
                firstLine = false;
            } else {
                builtResponse.append(" \n").append(line);
            }
        }
        System.out.println(builtResponse);
        response.addBody(builtResponse.toString());
    }
}