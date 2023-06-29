package com.junehouse.exception;

import lombok.Getter;

/*
* status => 400
* */
@Getter
public class InvalidRequest extends JuneTopException {

    private static final String MESSAGE = "잘못된 요청입니다.";
    public String filedName;
    public String filedMessage;

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String filedName, String message) {
        super(MESSAGE);
        this.filedName = filedName;
        this.filedMessage = message;
    }

    @Override
    public String getStatusCode() {
        return "400";
    }
}
