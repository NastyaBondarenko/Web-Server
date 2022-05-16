package com.bondarenko.webserver.response;

import com.bondarenko.webserver.entity.HttpStatus;
import com.bondarenko.webserver.util.ResponseWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseWriterTest {
    private ByteArrayOutputStream byteArrayOutputStream;
    private BufferedOutputStream bufferedOutputStream;
    private ResponseWriter responseWriter;

    @BeforeEach
    public void setUp() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        responseWriter = new ResponseWriter(bufferedOutputStream);
    }

    @AfterEach
    public void close() throws IOException {
        bufferedOutputStream.close();
    }

    @Test
    @DisplayName("test ResponseWriter when Http Status Is Ok")
    public void testResponseWriter_whenHttpStatusIsOk() throws IOException {
        responseWriter.writeResponse(HttpStatus.OK, "Hello".getBytes());
        StringBuilder stringBuilder = new StringBuilder();
        byte[] array = byteArrayOutputStream.toByteArray();
        for (byte b : array) {
            stringBuilder.append((char) b);
        }
        String actual = stringBuilder.toString();

        String expected = """
                HTTP/1.1 200 OK\r
                \r
                Hello""";

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test Response Writer when Http Status Is Bad Request")
    public void testResponseWriter_whenHttpStatusIs_BadRequest() throws IOException {
        String line = "";
        responseWriter.writeResponse(HttpStatus.BAD_REQUEST, line.getBytes());

        StringBuilder stringBuilder = new StringBuilder();
        byte[] array = byteArrayOutputStream.toByteArray();
        for (byte b : array) {
            stringBuilder.append((char) b);
        }
        String expected = "HTTP/1.1 400 BAD_REQUEST\r\n";
        String actual = stringBuilder.toString();

        assertEquals(expected, actual);
    }
}
