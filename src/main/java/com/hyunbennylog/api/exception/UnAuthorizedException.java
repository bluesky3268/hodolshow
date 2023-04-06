package com.hyunbennylog.api.exception;

public class UnAuthorizedException extends HyunbennylogException {

    private static final String MESSAGE = "인증이 필요합니다.";

    public UnAuthorizedException() {
        super(MESSAGE);
    }

    public UnAuthorizedException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
