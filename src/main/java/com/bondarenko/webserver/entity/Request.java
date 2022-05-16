package com.bondarenko.webserver.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class Request {
    private String uri;
    private HttpMethod httpMethod;
    private Map<String, String> headers;
}
