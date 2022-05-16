package com.bondarenko.webserver.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Response {
    private HttpStatus httpStatus;
    private byte[] content;
}
