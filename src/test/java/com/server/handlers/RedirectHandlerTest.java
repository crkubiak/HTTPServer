package com.server.handlers;

import com.server.Request;
import com.server.Response;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import static org.junit.Assert.*;

public class RedirectHandlerTest {
    @Test
    public void testOptionsHandler() {
        String requestLine = "OPTIONS /method_options HTTP/1.1";
        BufferedReader testSimpleGet = new BufferedReader(new StringReader(requestLine));
        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
        Request request = new Request(testSimpleGet);
        Response response = new Response(testOutput);
        RedirectHandler redirectHandler = new RedirectHandler();
        redirectHandler.handle(request, response);
        assertEquals(301, response.getStatusCode());
        assertEquals("MOVED PERMANENTLY", response.getStatusMessage());
        assertEquals("http://127.0.0.1:5000/simple_get", response.getHeaders().get("Location"));
        assertEquals("text/html", response.getHeaders().get("Content-Type"));
    }
}