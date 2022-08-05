package com.hyunbennylog.api.exception;

public class PostNotFoundException extends RuntimeException {

    private static final String MESSAGE = "해당 글은 존재하지 않습니다.";

    public PostNotFoundException() {
        super(MESSAGE);
    }

    public PostNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
