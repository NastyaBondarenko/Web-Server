package com.bondarenko.webserver.util;

import com.bondarenko.webserver.entity.HttpStatus;
import com.bondarenko.webserver.exception.ServerException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Objects;

public class ResponseWriter {
    private final BufferedOutputStream socketOutputStream;
    private final static byte[] LINE_END = "\r\n".getBytes();

    public ResponseWriter(BufferedOutputStream output) {
        this.socketOutputStream = output;
    }

    public void writeResponse(HttpStatus httpStatus, byte[] body) {
        try (socketOutputStream) {
            socketOutputStream.write(httpStatus.getStatusLine().getBytes());
            socketOutputStream.write(LINE_END);
            if (Objects.equals(httpStatus, HttpStatus.OK)) {
                socketOutputStream.write(LINE_END);
                socketOutputStream.write(body);
            }
        } catch (IOException ex) {
            throw new ServerException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}