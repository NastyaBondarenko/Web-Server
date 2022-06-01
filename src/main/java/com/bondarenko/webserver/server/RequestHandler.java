package com.bondarenko.webserver.server;

import com.bondarenko.webserver.entity.HttpStatus;
import com.bondarenko.webserver.entity.Request;
import com.bondarenko.webserver.entity.Response;
import com.bondarenko.webserver.exception.ServerException;
import com.bondarenko.webserver.util.ResponseWriter;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;

import static com.bondarenko.webserver.io.ResourceReader.readContent;
import static com.bondarenko.webserver.util.RequestReader.parseRequest;

public class RequestHandler {
    private final BufferedReader socketReader;
    private final BufferedOutputStream socketOutputStream;
    private final String webAppPath;

    public RequestHandler(BufferedReader socketReader, BufferedOutputStream socketOutputStream, String webAppPath) {
        this.socketReader = socketReader;
        this.socketOutputStream = socketOutputStream;
        this.webAppPath = webAppPath;
    }

    public Response handle() {
        ResponseWriter responseWriter = new ResponseWriter(socketOutputStream);
        Response response = new Response();
        try {
            Request request = parseRequest(socketReader);
            byte[] content = readContent(webAppPath, request.getUri());
            responseWriter.writeResponse(HttpStatus.OK, content);
        } catch (ServerException e) {
            response.setHttpStatus(e.getHttpStatus());
            responseWriter.writeResponse(e.getHttpStatus(), null);
        }
        return response;
    }
}
