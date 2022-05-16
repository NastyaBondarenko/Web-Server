package com.bondarenko.webserver.request;

import com.bondarenko.webserver.entity.HttpMethod;
import com.bondarenko.webserver.entity.Request;
import com.bondarenko.webserver.util.RequestReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.util.Map;

import static com.bondarenko.webserver.util.RequestReader.injectHeaders;
import static com.bondarenko.webserver.util.RequestReader.parseRequest;
import static org.junit.jupiter.api.Assertions.*;

public class RequestReaderTest {
    private Request request = new Request();

    @Test
    @DisplayName("test Inject Headers")
    public void testInjectHeaders() throws IOException {
        String headers = """
                Host: net.tutsplus.com
                User-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5 (.NET CLR 3.5.30729)
                Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
                Accept-Language: en-us,en;q=0.5
                Accept-Encoding: gzip,deflate
                Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7
                Keep-Alive: 300
                Connection: keep-alive
                Cookie: PHPSESSID=r2t5uvjq435r4q7ib3vtdjq120
                Pragma: no-cache
                Cache-Control: no-cache""";

        injectHeaders(new BufferedReader(new CharArrayReader(headers.toCharArray())), request);
        Map<String, String> actualHeaders = request.getHeaders();

        assertEquals(11, actualHeaders.size());
        assertTrue(actualHeaders.containsKey("Host"));
        assertTrue(actualHeaders.containsKey("User-Agent"));
        assertTrue(actualHeaders.containsKey("Accept"));
        assertFalse(actualHeaders.containsKey("NoExistingHeaders"));
        assertTrue(actualHeaders.remove("Host", "net.tutsplus.com"));
    }

    @Test
    @DisplayName("test Inject Headers when Map With Headers is Empty")
    public void testInjectHeaders_whenMapWithHeaders_isEmpty() throws IOException {
        String headers = "";

        injectHeaders(new BufferedReader(new CharArrayReader(headers.toCharArray())), request);
        Map<String, String> actualHeaders = request.getHeaders();

        assertEquals(0, actualHeaders.size());
        assertTrue(actualHeaders.isEmpty());
    }

    @Test
    @DisplayName("Parse Request when Get Uri")
    public void testParseRequest_whenGetUri() {
        String requestLine = """
                GET /hello.html HTTP/1.1
                Host: net.tutsplus.com
                User-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5 (.NET CLR 3.5.30729)
                Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
                Accept-Language: en-us,en;q=0.5""";

        request = parseRequest(new BufferedReader(new CharArrayReader(requestLine.toCharArray())));

        String expectedUri = "/hello.html";
        String actualUri = request.getUri();

        assertEquals(expectedUri, actualUri);
    }

    @Test
    @DisplayName("Parse Request when Get Http Method")
    public void testParseRequest_whenGetHttpMethod() {
        String requestLine = """
                GET /hello.html HTTP/1.1
                Host: net.tutsplus.com
                User-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5 (.NET CLR 3.5.30729)
                Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
                Accept-Language: en-us,en;q=0.5""";

        request = parseRequest(new BufferedReader(new CharArrayReader(requestLine.toCharArray())));

        HttpMethod expectedHttpMethod = HttpMethod.GET;
        HttpMethod actualHttpMethod = request.getHttpMethod();

        assertEquals(expectedHttpMethod, actualHttpMethod);
        assertEquals(4, request.getHeaders().size());
    }

    @Test
    @DisplayName("Inject Headers throw Runtime Exception when Headers Line Is Not Correct")
    public void testInjectHeaders_throwRuntimeException_whenHeadersLineIsNotCorrect() {
        Assertions.assertThrows(RuntimeException.class, () -> injectHeaders(new BufferedReader(new CharArrayReader("User-Agent Mozilla/4.0 ".toCharArray())), request));
    }

    @Test
    @DisplayName("Parse Request throw Runtime Exception when Request Line Is Empty")
    public void testParseRequest_throwRuntimeException_whenRequestLineIsEmpty() {
        String requestLine = "";
        Assertions.assertThrows(RuntimeException.class, () -> parseRequest(new BufferedReader(new CharArrayReader(requestLine.toCharArray()))));
    }

    @Test
    @DisplayName("Inject Uri And Method throw Runtime Exception when Http Method In Request Line is Not Exist")
    public void testInjectUriAndMethod_throwRuntimeException_whenHttpMethod_InRequestLine_isNotExist() {
        String requestLine = "/hello.html HTTP/1.1";
        Assertions.assertThrows(RuntimeException.class, () -> RequestReader.injectUriAndMethod(request, requestLine));
    }
}



