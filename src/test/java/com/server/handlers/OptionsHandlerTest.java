package com.server.handlers;

import com.server.Request;
import com.server.Response;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import static org.junit.Assert.*;

public class OptionsHandlerTest {
    @Test
    public void testOptionsHandler() {
        String requestLine = "OPTIONS /method_options HTTP/1.1";
        BufferedReader testSimpleGet = new BufferedReader(new StringReader(requestLine));
        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
        Request request = new Request(testSimpleGet);
        Response response = new Response(testOutput);
        OptionsHandler optionsHandler = new OptionsHandler();
        optionsHandler.handle(request, response);
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("GET, HEAD, OPTIONS", response.getHeaders().get("Allow"));
        assertEquals("text/html", response.getHeaders().get("Content-Type"));
    }
}