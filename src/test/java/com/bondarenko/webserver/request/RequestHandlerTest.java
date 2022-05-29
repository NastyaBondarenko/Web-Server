package com.bondarenko.webserver.request;

import com.bondarenko.webserver.entity.HttpStatus;
import com.bondarenko.webserver.entity.Response;
import com.bondarenko.webserver.server.RequestHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.CharArrayReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class RequestHandlerTest {
    private BufferedReader socketReader;
    private RequestHandler requestHandler;
    private BufferedOutputStream mockOutputStream;
    private final String webAppPath = "src/test/resources/webapp";
    private Response response;
    private String requestLine;

    @BeforeEach
    public void setUp() {
        mockOutputStream = mock(BufferedOutputStream.class);
    }

    @Test
    @DisplayName("Handle Request throw Not Found when Uri Is Not Exist")
    public void testHandleRequest_throwNotFound_whenUriIsNotExist() {
        requestLine = "GET index123.html HTTP 1.1";
        socketReader = new BufferedReader(new CharArrayReader(requestLine.toCharArray()));
        requestHandler = new RequestHandler(socketReader, mockOutputStream, webAppPath);
        response = requestHandler.handle();

        HttpStatus actualHttpStatus = response.getHttpStatus();
        HttpStatus expectedHttpStatus = HttpStatus.NOT_FOUND;

        assertEquals(expectedHttpStatus, actualHttpStatus);
    }

    @Test
    @DisplayName("Handle Request throw IllegalArgumentException when RequestLine contains Not Http Method")
    public void testHandleRequest_throwIllegalArgumentException_whenRequestLine_containsNotHttpMethod() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            requestLine = "IncorrectMethod /index.html HTTP/1.1";

            socketReader = new BufferedReader(new CharArrayReader(requestLine.toCharArray()));
            requestHandler = new RequestHandler(socketReader, mockOutputStream, webAppPath);
            response = requestHandler.handle();
        });
    }

    @Test
    @DisplayName("Handle Request throw Method Not Allowed when Http Method Is Post")
    public void testHandleRequest_throwMethodNotAllowed_whenHttpMethodIsPost() {
        requestLine = "POST /index.html HTTP/1.1";
        socketReader = new BufferedReader(new CharArrayReader(requestLine.toCharArray()));
        requestHandler = new RequestHandler(socketReader, mockOutputStream, webAppPath);
        response = requestHandler.handle();

        HttpStatus actualHttpStatus = response.getHttpStatus();
        HttpStatus expectedHttpStatus = HttpStatus.METHOD_NOT_ALLOWED;

        assertEquals(expectedHttpStatus, actualHttpStatus);
    }

    @Test
    @DisplayName("Handle Request throw NullPointerException when Request Line Is Empty")
    public void testHandleRequest_throwNullPointerException_whenRequestLineIsEmpty() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            requestLine = "";
            socketReader = new BufferedReader(new CharArrayReader(requestLine.toCharArray()));
            requestHandler = new RequestHandler(socketReader, mockOutputStream, webAppPath);
            response = requestHandler.handle();
        });
    }
}