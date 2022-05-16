package com.bondarenko.webserver.exception;

import com.bondarenko.webserver.entity.HttpStatus;

public class ServerException extends RuntimeException {
    private HttpStatus httpStatus;

    public ServerException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
