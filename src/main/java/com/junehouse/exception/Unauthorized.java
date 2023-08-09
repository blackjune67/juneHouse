package com.junehouse.exception;

public class Unauthorized extends JuneTopException {

    private static final String MESSAGE = "인증이 필요합니다.";

    public Unauthorized() {
        super(MESSAGE);
    }

    public Unauthorized(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public String getStatusCode() {
        return "401";
    }
}
