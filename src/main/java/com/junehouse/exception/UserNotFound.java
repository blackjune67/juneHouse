package com.junehouse.exception;

public class UserNotFound extends JuneTopException {
    private static final String MESSAGE = "존재하지 않는 사용자입니다.";
    public UserNotFound() {
        super(MESSAGE);
    }
    @Override
    public String getStatusCode() {
        return "404";
    }
}
