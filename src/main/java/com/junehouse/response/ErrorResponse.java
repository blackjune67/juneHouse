package com.junehouse.response;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
//@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {
    private final String code;
    private final String message;

    private final Map<String, String> validation = new HashMap<>();

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
