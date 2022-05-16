package com.bondarenko.webserver.util;

import com.bondarenko.webserver.entity.HttpStatus;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Objects;

public class ResponseWriter {
    private final BufferedOutputStream output;
    private final static byte[] LINE_END = "\r\n".getBytes();

    public ResponseWriter(BufferedOutputStream output) {
        this.output = output;
    }

    public void writeResponse(HttpStatus httpStatus, byte[] body) throws IOException {
        try {
            output.write(httpStatus.getStatusLine().getBytes());
            output.write(LINE_END);
            if (Objects.equals(httpStatus,HttpStatus.OK)){
                output.write(LINE_END);
                output.write(body);
            }
            output.flush();
        } catch (IOException ex) {
            System.out.println("File is not written: " + ex.getMessage());
            throw new IOException();
        }
    }
}

