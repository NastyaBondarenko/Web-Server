package com.bondarenko.webserver.server;

import com.bondarenko.webserver.entity.HttpStatus;
import com.bondarenko.webserver.entity.Request;
import com.bondarenko.webserver.entity.Response;
import com.bondarenko.webserver.exception.ServerException;
import com.bondarenko.webserver.util.ResponseWriter;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.bondarenko.webserver.io.ResourceReader.readContent;
import static com.bondarenko.webserver.util.RequestReader.parseRequest;

public class RequestHandler {
    private final BufferedReader reader;
    private final BufferedOutputStream output;
    private final String webAppPath;

    public RequestHandler(BufferedReader reader, BufferedOutputStream output, String webAppPath) {
        if (reader == null || output == null || webAppPath == null) {
            throw new NullPointerException("Reader, writer or webAppPath, - can not be null");
        }
        this.reader = reader;
        this.output = output;
        this.webAppPath = webAppPath;
    }

    public Response handle() throws IOException {
        ResponseWriter responseWriter = new ResponseWriter(output);
        Response response = new Response();
        try {
            Request request = parseRequest(reader);
            byte[] content = readContent(webAppPath, request.getUri());
            responseWriter.writeResponse(HttpStatus.OK, content);
        } catch (ServerException exception) {
            response.setHttpStatus(exception.getHttpStatus());
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
            response.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
        } catch (RuntimeException exception) {
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
        } catch (FileNotFoundException exception) {
            response.setHttpStatus(HttpStatus.NOT_FOUND);
        } catch (IOException exception) {
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}

