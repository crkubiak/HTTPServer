package com.server;

import com.server.interfaces.SocketInterface;
import com.server.wrappers.ServerSocketWrapperSpy;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class HttpServerTest {
    @Test
    public void createsSocketAndListensForClient() throws IOException {
//        BufferedReader input = new BufferedReader(
//                new StringReader("echo"));
//        PrintWriter output = new PrintWriter(new StringWriter(), true);
//        ServerSocketWrapperSpy testServerSocket = new ServerSocketWrapperSpy(input, output);
//        HttpServer testServer = new HttpServer(testServerSocket);
//        testServer.start();
//
//
//        assertTrue(testServerSocket.wasCreateAndListenCalled());
//        assertTrue(testServerSocket.wasCloseCalled());
    }

}