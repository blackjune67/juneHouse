package com.junehouse.exception;

import lombok.Getter;

/*
* status => 400
* */
@Getter
public class InvalidRequest extends JuneTopException {

    private static final String MESSAGE = "잘못된 요청입니다.";
    private String fieldName;
    private String fieldMessage;
    private String validation1;

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);
        /*this.fieldName = fieldName;
        this.fieldMessage = message;*/
        addValidation(fieldName, message);
    }

    @Override
    public String getStatusCode() {
        return "400";
    }
}
