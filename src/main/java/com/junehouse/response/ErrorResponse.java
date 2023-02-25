package com.junehouse.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class ErrorResponse {
    private final String code;
    private final String message;

    private Map<String, String> validation = new HashMap<>();

//    private Validation validation;

    @Builder
//    public ErrorResponse(String code, String message, Validation validation) {
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
//        this.validation = validation;
    }

    //    private final Map<String, String> validation = new HashMap<>();

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }
}
