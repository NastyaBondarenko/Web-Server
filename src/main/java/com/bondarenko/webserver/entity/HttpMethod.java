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

    private final String methodName;

    HttpMethod(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public static HttpMethod getMethod(String methodName) {
        for (HttpMethod value : values()) {
            if (value.getMethodName().equals(methodName)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Not http method : " + methodName);
    }
}
