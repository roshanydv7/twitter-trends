package com.twitter.exceptions;

public class TwitterApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TwitterApiException(String message, Exception rootCause) {
        super(message, rootCause);
    }

    public TwitterApiException(String message) {
        super(message);
    }
}
