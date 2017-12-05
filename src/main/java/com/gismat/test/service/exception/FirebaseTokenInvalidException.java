package com.gismat.test.service.exception;

public class FirebaseTokenInvalidException extends Throwable {
    public FirebaseTokenInvalidException(String message) {
        System.out.println(message);
    }
}
