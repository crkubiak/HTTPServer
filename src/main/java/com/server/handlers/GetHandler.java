package com.server.handlers;

import com.server.Request;
import com.server.Response;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GetHandler implements Handler {
    @Override
    public void handle(Request request, Response response) throws IOException {
//        try {
//            FileInputStream file = new FileInputStream(request.getPath().substring(1));
            response.setResponseCode(200, "OK");
            response.addHeader("Content-Type", "text/html");
//            StringBuffer buf = new StringBuffer();
//            int c;
//            while ((c = file.read()) != -1) {
//                buf.append((char) c);
//            }
//            response.addBody(buf.toString());
//        } catch (FileNotFoundException ex) {
//            response.setResponseCode(404, "Not Found");
//        }
    }
}
