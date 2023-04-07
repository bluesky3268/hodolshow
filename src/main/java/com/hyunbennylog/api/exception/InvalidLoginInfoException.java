package com.hyunbennylog.api.exception;

public class InvalidLoginInfoException extends HyunbennylogException{

    private static final String MESSAGE = "아이디 또는 비밀번호가 올바르지 않습니다.";

    public InvalidLoginInfoException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
