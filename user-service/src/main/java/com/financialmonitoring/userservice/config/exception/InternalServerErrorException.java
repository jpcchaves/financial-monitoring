package com.financialmonitoring.userservice.config.exception;

import java.io.Serial;

public class InternalServerErrorException extends RuntimeException {

    @Serial private static final long serialVersionUID = 4447561484651753594L;

    public InternalServerErrorException(String message) {
        super(message);
    }
}
