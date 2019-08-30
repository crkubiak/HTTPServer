package com.server.handlers;

import com.server.Request;
import com.server.Response;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import static org.junit.Assert.*;

public class NotImplementedHandlerTest {
    @Test
    public void testNotImplementedHandler() {
        String requestLine = "GET /simple_get HTTP/1.1";
        BufferedReader testSimpleGet = new BufferedReader(new StringReader(requestLine));
        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
        Request request = new Request(testSimpleGet);
        Response response = new Response(testOutput);
        NotImplementedHandler notImplementedHandler = new NotImplementedHandler();
        notImplementedHandler.handle(request, response);
        assertEquals(405, response.getStatusCode());
        assertEquals("METHOD NOT ALLOWED", response.getStatusMessage());
        assertEquals("HEAD, OPTIONS", response.getHeaders().get("Allow"));
    }
}