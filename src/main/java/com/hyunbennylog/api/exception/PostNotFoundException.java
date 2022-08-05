package com.hyunbennylog.api.exception;

/**
 * status : 404
 */
public class PostNotFoundException extends HyunbennylogException {

    private static final String MESSAGE = "해당 글은 존재하지 않습니다.";

    public PostNotFoundException() {
        super(MESSAGE);
    }

    public PostNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
