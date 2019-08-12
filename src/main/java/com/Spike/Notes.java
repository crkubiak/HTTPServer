package com.Spike;

public class Notes {
}

//package com.server;
//
//        import javax.imageio.IIOException;
//        import java.io.*;
//        import java.net.ServerSocket;
//        import java.net.Socket;
//        import java.util.Date;
//        import java.util.StringTokenizer;
//
//public class HTTPServer {
//    static final File WEB_ROOT = new File(".");
//    static final String DEFAULT_FILE = "index.html;";
//    static final String FILE_NOT_FOUND = "404.html";
//    static final String METHOD_NOT_SUPPORTED = "not_supported.html";
//
//    //port to listen for connection
//    static final int PORT = 8080;
//
//    //verbose mode
//    static final boolean verbose = true;
//
//    //client connection via socket
//    private Socket connect;
//
//    public HTTPServer(Socket c) {
//        this.connect = c;
//    }
//
//    public static void main(String[] args) {
//        try {
//            ServerSocket serverConnect = new ServerSocket(PORT);
//            System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");
//
//            while (true) {
//                HTTPServer myServer = new HTTPServer(serverConnect.accept());
//
//                if (verbose) {
//                    System.out.println("Connection opened. (" + new Date() + ")");
//                }
//
//                //dedicated thread to manage the client connection
//                Thread thread = new Thread(myServer);
//                thread.start();
//            }
//
//        } catch (IOException ex) {
//            System.err .println("Server Connection error : " + ex.getMessage());
//        }
//
//    }
//
//    @Override
//    public void run() {
//        //we manage particular client connection
//        BufferedReader in = null;
//        PrintWriter out = null;
//        BufferedOutputStream dataOut = null;
//        String fileRequested = null;
//
//        try {
//            //read characters from the client via input stream on the socket
//            in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
//            //we get character output stream to the client for headers
//            out = new PrintWriter(connect.getOutputStream());
//            // get binary output stream to client for requested data
//            dataOut = new BufferedOutputStream(connect.getOutputStream());
//
//            //get first line of the request from the client
//            String input = in.readLine();
//            //parse the request with a string tokenizer
//            StringTokenizer parse = new StringTokenizer(input);
//            String method = parse.nextToken().toUpperCase();
//            //we get file requested
//            fileRequested = parse.nextToken().toLowerCase();
//
//            //we suport only GET and HEAD methods, we check
//            if (!method.equals("GET") && !method.equals("HEAD")) {
//                if(verbose) {
//                    System.out.println("501 Not Implemented : " + method + " method.");
//                }
//                //we return the not supported file to client
//
//            } else {
//
//            }
//
//        } catch (IOException ex) {
//            System.err.println("Server error : " + ex);
//            ex.printStackTrace();
//        }
//    }
//
//}


