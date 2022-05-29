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
        } catch (IOException e) {
            throw new ServerException(e, HttpStatus.BAD_REQUEST);
        }
    }

    public static void injectUriAndMethod(Request request, String requestLine) {
        String[] requestStartLine = requestLine.split(" ");
        HttpMethod requestHttpMethod = HttpMethod.valueOf(requestStartLine[0]);
        HttpMethod httpMethod = HttpMethod.getMethod(String.valueOf(requestHttpMethod));
        if (httpMethod != HttpMethod.GET) {
            throw new ServerException(HttpStatus.METHOD_NOT_ALLOWED);
        }
        request.setHttpMethod(requestHttpMethod);
        request.setUri(requestStartLine[1]);
    }

    public static void injectHeaders(BufferedReader reader, Request request) {
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
        } catch (Exception ex) {
            throw new ServerException(HttpStatus.BAD_REQUEST);
        }
    }
}
