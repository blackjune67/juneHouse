package com.junehouse.exception;

public class InvalidSign extends JuneTopException {

    private static final String MESSAGE = "아이디와 비밀번호가 올바르지 않습니다.";

    public InvalidSign() {
        super(MESSAGE);
    }

    @Override
    public String getStatusCode() {
        return "400";
    }
}
