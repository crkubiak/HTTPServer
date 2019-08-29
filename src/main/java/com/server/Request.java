package com.server;

import com.server.interfaces.RequestInterface;
import com.server.wrappers.InputStreamWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Request {
    private String method;
    private String path;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> queryParameters = new HashMap<>();
    private BufferedReader in;

    public Request(BufferedReader in)  {
        this.in = in;
    }

    String getMethod()  {
        return method;
    }

    String getPath()  {
        return path;
    }

    private void parseQueryParameters(String queryString)  {
        for (String parameter : queryString.split("&"))  {
            int separator = parameter.indexOf('=');
            if (separator > -1)  {
                queryParameters.put(parameter.substring(0, separator),
                        parameter.substring(separator + 1));
            } else  {
                queryParameters.put(parameter, null);
            }
        }
    }

    boolean parse() throws IOException {
        String initialLine = in.readLine();
        log(initialLine);
        StringTokenizer token = new StringTokenizer(initialLine);
        String[] components = new String[3];
        for (int i = 0; i < components.length; i++)  {
            if (token.hasMoreTokens())  {
                components[i] = token.nextToken();
            } else  {
                return false;
            }
        }

        method = components[0];


        while (true)  {
            String headerLine = in.readLine();
            log(headerLine);
            if (headerLine.length() == 0)  {
                break;
            }

            int separator = headerLine.indexOf(":");
            if (separator == -1)  {
                return false;
            }
            headers.put(headerLine.substring(0, separator),
                    headerLine.substring(separator + 1));
        }

        if (!components[1].contains("?"))  {
            path = components[1];
        } else  {
            path = components[1].substring(0, components[1].indexOf("?"));
            parseQueryParameters(components[1].substring(
                    components[1].indexOf("?") + 1));
        }

        if ("/".equals(path))  {
            path = "/index.html";
        }

        return true;
    }

    private void log(String msg)  {
        System.out.println(msg);
    }

    public String toString()  {
        return method  + " " + path + " " + headers.toString();
    }

    public InputStream getBody() throws IOException {
        return new InputStreamWrapper(in, headers);
    }
}
