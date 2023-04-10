package com.hyunbennylog.api.exception;

/**
 * status : 404
 */
public class AlreadyUserExistException extends HyunbennylogException {

    private static final String MESSAGE = "해당 회원이 이미 존재합니다..";

    public AlreadyUserExistException() {
        super(MESSAGE);
    }

    public AlreadyUserExistException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
