package com.bondarenko.webserver.util;

import com.bondarenko.webserver.entity.HttpMethod;
import com.bondarenko.webserver.entity.HttpStatus;
import com.bondarenko.webserver.entity.Request;
import com.bondarenko.webserver.exception.ServerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestReader {
    public static Request parseRequest(BufferedReader reader) {
        try {
            String line = reader.readLine();
            Request request = new Request();
            injectUriAndMethod(request, line);
            injectHeaders(reader, request);
            return request;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ServerException(HttpStatus.METHOD_NOT_ALLOWED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(HttpStatus.BAD_REQUEST);
        }
    }

    public static void injectUriAndMethod(Request request, String requestLine) {
        String[] strings = requestLine.split(" ");
        try {
            HttpMethod httpMethod = HttpMethod.valueOf(strings[0]);
            if (httpMethod == HttpMethod.POST) {
                throw new ServerException(HttpStatus.BAD_REQUEST);
            }
            request.setHttpMethod(httpMethod);
            request.setUri(strings[1]);

        } catch (IllegalArgumentException ex) {
            System.out.println("InjectUriMethod: " + ex.getMessage());
            ex.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public static void injectHeaders(BufferedReader reader, Request request) throws IOException {
        try {
            Map<String, String> headersMap = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    break;
                }
                String[] strings = line.split(": ");
                headersMap.put(strings[0], strings[1]);
            }
            request.setHeaders(headersMap);
        } catch (IOException ex) {
            System.out.println("InjectHeaders: " + ex.getMessage());
            ex.printStackTrace();
            throw new IOException();
        }
    }
}
