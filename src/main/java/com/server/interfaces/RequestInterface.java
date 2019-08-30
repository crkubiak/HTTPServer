package com.server.interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public interface RequestInterface {
    String getMethod();
    String getPath();
    void parseQueryParameters(String queryString);
    boolean parse() throws IOException;
    void log(String msg);
    String toString();
    InputStream getBody() throws IOException;
}
