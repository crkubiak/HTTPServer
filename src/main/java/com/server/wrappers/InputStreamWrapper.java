package com.server;


import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;

class InputStreamWrapper extends InputStream  {
    private Reader source;
    private int bytesRemaining;

    public InputStreamWrapper(Reader source, Map<String, String> headers) throws IOException  {
        this.source = source;

        try  {
            String headerValue = headers.get("Content-Length");
            bytesRemaining = Integer.parseInt(headerValue.trim());
        } catch (NumberFormatException e)  {
            throw new IOException("Malformed or missing Content-Length header");
        }
    }

    public int read() throws IOException  {
        if (bytesRemaining == 0)  {
            return -1;
        } else  {
            bytesRemaining -= 1;
            return source.read();
        }
    }
}