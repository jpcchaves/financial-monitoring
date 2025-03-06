package com.financialmonitoring.userservice.config.exception;

import java.io.Serial;

public class BadRequestException extends RuntimeException {

    @Serial private static final long serialVersionUID = 910417010156854980L;

    public BadRequestException(String message) {
        super(message);
    }
}
