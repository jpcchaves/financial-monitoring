package com.financialmonitoring.userservice.config.exception;

import com.financialmonitoring.commonlib.dto.ExceptionResponseDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {

        logger.error(ex.getMessage());
        logger.error(Arrays.toString(ex.getStackTrace()));

        ExceptionResponseDTO exceptionResponse =
                ExceptionResponseDTO.builder()
                        .withMessage(ex.getMessage())
                        .withDetails(request.getDescription(false))
                        .build();

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ExceptionResponseDTO> handleAccessDeniedException(
            BadRequestException ex, WebRequest request) {

        ExceptionResponseDTO exceptionResponse =
                ExceptionResponseDTO.builder()
                        .withMessage(ex.getMessage())
                        .withDetails(request.getDescription(false))
                        .build();

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ExceptionResponseDTO> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
            WebRequest request) {
        ExceptionResponseDTO exceptionResponse =
                ExceptionResponseDTO.builder()
                        .withMessage(ex.getMessage())
                        .withDetails(request.getDescription(false))
                        .build();

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<String> errors = new ArrayList<>();

        ex.getBindingResult()
                .getAllErrors()
                .forEach(
                        error -> {
                            String errorMessage;

                            errorMessage = error.getDefaultMessage();

                            errors.add(errorMessage);
                        });

        ExceptionResponseDTO exceptionResponse =
                ExceptionResponseDTO.builder()
                        .withMessage(errors.getFirst())
                        .withDetails(request.getDescription(false))
                        .build();

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
