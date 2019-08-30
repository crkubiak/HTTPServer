package com.server.handlers;

import com.server.Request;
import com.server.Response;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import static org.junit.Assert.*;

public class HeadHandlerTest {

    @Test
    public void testHeadHandler() {
        String requestLine = "HEAD /simple_get HTTP/1.1";
        BufferedReader testSimpleGet = new BufferedReader(new StringReader(requestLine));
        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
        Request request = new Request(testSimpleGet);
        Response response = new Response(testOutput);
        HeadHandler headHandler = new HeadHandler();
        headHandler.handle(request, response);
        System.out.println();
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals(null, response.getBody());
        assertEquals("text/html", response.getHeaders().get("Content-Type"));

    }
}