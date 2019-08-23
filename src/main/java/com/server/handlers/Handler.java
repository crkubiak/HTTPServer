package com.server.handlers;

import com.server.Request;
import com.server.Response;

import java.io.IOException;

public interface Handler {
    void handle(Request request, Response response) throws IOException;
}
