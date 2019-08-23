package com.server;

import com.server.wrappers.ServerSocketWrapperSpy;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerTest {
    @Test
    public void testReceivedDataIsEchoedBackInSentData() {
        BufferedReader input = new BufferedReader(
                new StringReader("echo"));
        PrintWriter output = new PrintWriter(new StringWriter(), true);
        ServerSocketWrapperSpy socketWrapper =
                new ServerSocketWrapperSpy(input, output);
        Server server = new Server(socketWrapper);
        server.start(5000);
        assertTrue(socketWrapper.wasCreateAndListenCalled());
        assertEquals("echo", socketWrapper.getSentData());
        assertTrue(socketWrapper.wasCloseCalled());
    }

}