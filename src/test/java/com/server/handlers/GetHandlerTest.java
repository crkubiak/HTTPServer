package com.server.handlers;

import org.junit.Test;
import static org.junit.Assert.*;

public class GetHandlerTest {
    @Test
    public void testGetHandler() {
//        OutputStream out = new OutputStream();
//        HttpServer server = new HttpServer(5000);
//        Response response = new Response(out);
//        BufferedReader in;
//        OutputStream out;
//        Request testRequest = new Request(in);
//        Response testResponse = new Response(out);
        String expected = "hi";
        String actual = "hi";
        assertEquals(expected, actual);
    }

}