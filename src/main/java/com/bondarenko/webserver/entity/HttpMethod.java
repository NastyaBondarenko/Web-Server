package com.bondarenko.webserver.entity;

import lombok.Getter;

@Getter
public enum HttpMethod {
    GET("GET"),
    HEAD("HEAD"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE"),
    PATCH("PATCH"),
    CONNECT("CONNECT");

    private String uri;
    private final String httpMethod;

    HttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String requestLine() {// Request to Server:
        return httpMethod + "/" + uri + "HTTP/1.1";
    }
}
