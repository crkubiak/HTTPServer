package com.Spike;

        import java.io.*;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.util.Date;
        import java.util.StringTokenizer;

public class HTTPServer implements Runnable {

    static final File WEB_ROOT = new File(".");
    static final String DEFAULT_FILE = "index.html";
    static final String FILE_NOT_FOUND = "404.html";
    static final String METHOD_NOT_SUPPORTED = "not_supported.html";

    //port to listen for connection
    static final int PORT = 5000;

    //verbose mode
    static final boolean verbose = true;

    //client connection via socket
    private Socket connect;

    public HTTPServer(Socket c) {
        this.connect = c;
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverConnect = new ServerSocket(PORT);
            System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");

            while (true) {
                HTTPServer myServer = new HTTPServer(serverConnect.accept());

                if (verbose) {
                    System.out.println("Connection opened. (" + new Date() + ")");
                }

                //dedicated thread to manage the client connection
                Thread thread = new Thread(myServer);
                thread.start();
            }

        } catch (IOException ioe) {
            System.err .println("Server Connection error : " + ioe.getMessage());
        }

    }

    @Override
    public void run() {
        //we manage particular client connection
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedOutputStream dataOut = null;
        String fileRequested = null;

        try {
                                                                                        //read characters from the client via input stream on the socket
            in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                                                                                        //we get character output stream to the client for headers
            out = new PrintWriter(connect.getOutputStream());
                                                                                        // get binary output stream to client for requested data
            dataOut = new BufferedOutputStream(connect.getOutputStream());

                                                                                        //get first line of the request from the client
            String input = in.readLine();
                                                                                        //parse the request with a string tokenizer
            StringTokenizer parse = new StringTokenizer(input);
            String method = parse.nextToken().toUpperCase();
                                                                                        //we get file requested
            fileRequested = parse.nextToken().toLowerCase();
            System.out.println("parse: " + parse + "  method: " + method + "  file requested: " + fileRequested);
                                                                                        //we suport only GET and HEAD methods, we check
            if (!method.equals("GET") && !method.equals("HEAD")) {
                if (verbose) {
                    System.out.println("501 Not Implemented : " + method + " method.");
                }
                                                                                        //we return the not supported file to client
                File file = new File(WEB_ROOT, METHOD_NOT_SUPPORTED);
                int fileLength = (int) file.length();
                String contentMimeType = "text/html";
                                                                                        //read content to return to client
                byte[] fileData = readFileData(file, fileLength);

                                                                                        //we send HTTP Headers with data to client
                out.println("HTTP/1.1 501 Not Implemented");
                out.println("Server: Java HTTP Server");
                out.println("Date: " + new Date());
                out.println("Content-type: " + contentMimeType);
                out.println("Content-length: " + file.length());
                out.println(); // blank line between headers and content
                out.flush(); //flush character output stream buffer
                //file
                dataOut.write(fileData, 0, fileLength);
                dataOut.flush();

            } else {
                //GET or HEAD method
                if (fileRequested.endsWith("/")) {
                    fileRequested += DEFAULT_FILE;
                    System.out.println("Line 104 " + fileRequested);
                }

                File file = new File(WEB_ROOT, fileRequested);

                int fileLength = (int) file.length();
                String content = getContentType(fileRequested);

                if (method.equals("GET")) { //get method so we return content
                    byte[] fileData = readFileData(file, fileLength);

                    //send http headers
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: Java HTTP Server");
                    out.println("Date: " + new Date());
                    out.println("Content-type: " + content);
                    out.println("Content-length: " + fileLength);
                    out.println(); // blank line between headers and content
                    out.flush(); //flush character output stream buffer

//                    dataOut.write(fileData, 0, fileLength);
//                    dataOut.flush();
                }

                if (verbose) {
                    System.out.println("File " + fileRequested + " of type" + content + " returned");
                }
            }

        } catch (FileNotFoundException fnfEx) {
            try {
                fileNotFound(out, dataOut, fileRequested);
            } catch (IOException ioe) {
                System.err.println("Error with file not found exception : " + ioe.getMessage());
            }

        } catch (IOException ioe) {
            System.err.println("Server error : " + ioe);
        } finally {
            try {
                in.close(); //close character input stream;
                out.close();
                dataOut.close();
                connect.close(); // we close socket connection
            } catch (IOException ioe) {
                System.err.println("Error closing stream : " + ioe.getMessage());
            }

            if (verbose) {
                System.out.println("Connection closed.\n");
            }
        }
    }

    private void fileNotFound(PrintWriter out, BufferedOutputStream dataOut, String fileRequested) throws IOException {
        File file = new File(WEB_ROOT, FILE_NOT_FOUND);
        int fileLength = (int) file.length();
        String content = "text/html";
        byte[] fileData = readFileData(file, fileLength);

        out.println("HTTP/1.1 404 File Not Found");
        out.println("Server: Java HTTP Server");
        out.println("Date: " + new Date());
        out.println("Content-type: " + content);
        out.println("Content-length: " + fileLength);
        out.println(); // blank line between headers and content
        out.flush(); //flush character output stream buffer

        dataOut.write(fileData, 0, fileLength);
        dataOut.flush();

        if (verbose) {
            System.out.println("File " + fileRequested + " not found.");
        }
    }

    //return suported MIME types
    private String getContentType(String fileRequested) {
        if (fileRequested.endsWith(".htm") || fileRequested.endsWith(".html")) {
            return "text/html";
        } else {
            return "text/plain";
        }
    }

    private byte[] readFileData(File file, int fileLength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];

        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null) {
                fileIn.close();
            }
        }
        return fileData;
    }
}


//Junit decorators
//public HTTPServerTest() {
//    System.out.printf("Constructor invoked. Instance: %s%n", this);
//}
//
//    @BeforeClass
//    public static void beforeClassMethod() {
//        System.out.println("@BeforeClass static method invoked.");
//    }
//
//    @Test
//    public void test1() {
//        System.out.printf("@Test method 1  invoked. Instance: %s%n", this);
//    }
//
//    @Test
//    public void test2() {
//        System.out.printf("@Test method 2  invoked. Instance: %s%n", this);
//    }
//
//    @Before
//    public void beforeMethod() {
//        System.out.printf("@Before method invoked. Instance: %s%n", this);
//    }
//
//    @After
//    public void afterMethod() {
//        System.out.printf("@After method invoked. Instance: %s%n", this);
//    }
//
//    @AfterClass
//    public static void afterClassMethod() {
//        System.out.printf("@AfterClass static method invoked.%n");
//    }