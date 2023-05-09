package com.xcompany.nimble.base.exception;

public class StartServerException extends RuntimeException {
    public StartServerException(String message) {
        super(message);
    }

    public StartServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public StartServerException(Throwable cause) {
        super(cause);
    }
}
