package com.junehouse.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@ToString
public class ErrorResponse {
    private final String code;
    private final String message;

    private Validation validation;

//    private final Map<String, String> validation = new HashMap<>();

    public void addValidation(String fieldName, String errorMessage) {
//        this.validation.put(fieldName, errorMessage);
        Validation build = Validation.builder().fieldName(fieldName).errorMessage(errorMessage).build();
        this.validation = build;
    }
}
