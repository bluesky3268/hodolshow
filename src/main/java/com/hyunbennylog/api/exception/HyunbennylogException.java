package com.hyunbennylog.api.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class HyunbennylogException extends RuntimeException{

    public abstract int getStatusCode();

    private final Map<String, String> validation = new HashMap<>();

    public HyunbennylogException() {
    }

    public HyunbennylogException(String message) {
        super(message);
    }

    public HyunbennylogException(String message, Throwable cause) {
        super(message, cause);
    }

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
