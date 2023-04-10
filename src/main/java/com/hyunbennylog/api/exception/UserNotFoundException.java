package com.hyunbennylog.api.exception;

/**
 * status : 404
 */
public class UserNotFoundException extends HyunbennylogException {

    private static final String MESSAGE = "해당 회원은 존재하지 않습니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }

    public UserNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
