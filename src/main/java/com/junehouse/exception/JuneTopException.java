package com.junehouse.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class JuneTopException extends RuntimeException {
    private final Map<String, String> validation = new HashMap<>();
    public JuneTopException(String message) {
        super(message);
    }
    // * 생성자 오버로딩
    public JuneTopException(String message, Throwable cause) {
        super(message, cause);
    }
    public abstract String getStatusCode();
    public void addValidation(String fieldName, String fieldMessage) {
        validation.put(fieldName, fieldMessage);
    }
}
