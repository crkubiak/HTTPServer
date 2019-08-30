package com.server.handlers;

import com.server.Request;
import com.server.Response;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class GetHandlerTest {
    @Test
    public void testGetHandler() {
        String requestLine = "GET /simple_get HTTP/1.1";
        BufferedReader testSimpleGet = new BufferedReader(new StringReader(requestLine));
        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
        Request request = new Request(testSimpleGet);
        Response response = new Response(testOutput);
        GetHandler getHandler = new GetHandler();
        getHandler.handle(request, response);
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("text/html", response.getHeaders().get("Content-Type"));
    }
}