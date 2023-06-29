package com.junehouse.exception;

public abstract class JuneTopException extends RuntimeException {
    public JuneTopException(String message) {
        super(message);
    }

    public JuneTopException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract String getStatusCode();
}
