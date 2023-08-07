package com.junehouse.exception;

public class Unauthorized extends JuneTopException {

    private static final String MESSAGE = "존재하지 않는 글입니다.";

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
