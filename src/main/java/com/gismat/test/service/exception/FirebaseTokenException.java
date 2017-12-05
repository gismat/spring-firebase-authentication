package com.gismat.test.service.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FirebaseTokenException extends RuntimeException {

    String message;

    public FirebaseTokenException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
