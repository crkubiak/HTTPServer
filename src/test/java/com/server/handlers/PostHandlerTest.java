package com.server.handlers;

import com.server.Request;
import com.server.Response;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;

public class PostHandlerTest {
    @Test
    public void testPostHandler() throws IOException {
//        String requestLine = "POST /echo_body HTTP/1.1";
//        BufferedReader testSimpleGet = new BufferedReader(new StringReader(requestLine));
//        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
//        Request request = new Request(testSimpleGet);
//        Response response = new Response(testOutput);
//        PostHandler postHandler = new PostHandler();
//        postHandler.handle(request, response);
//        assertEquals(200, response.getStatusCode());
//        assertEquals("GET, HEAD, OPTIONS, PUT, POST", response.getHeaders().get("Allow"));
//        assertEquals("text/html", response.getHeaders().get("Content-Type"));
    }
}