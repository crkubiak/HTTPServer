package com.server.wrappers;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerSocketWrapper implements SocketInterface {
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private BufferedOutputStream dataOutput;

    @Override
    public void createAndListen(int port) {
        try {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (UnknownHostException uex) {
            System.err.println("[-]Unknown host error.");
            uex.getCause();
        } catch (IOException ex) {
            System.err.println("[-]Connection error.");
            ex.getMessage();
        }
    }

    @Override
    public String receive() {
        try {
            return input.readLine();
        } catch (IOException ex) {
            System.err.println("[-]Input not received.");
            return ex.getMessage();
        }
    }

    @Override
    public void send(String data) {
        output.println(data);
    }

    @Override
    public void close() {
        try {
            output.close();
            input.close();
            socket.close();
            serverSocket.close();
        } catch (IOException ex) {
            System.err.println("[-]Shutdown error.");
            ex.getMessage();
        }
    }
}
