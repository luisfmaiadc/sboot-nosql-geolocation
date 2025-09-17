package com.portfolio.luisfmdc.sboot_nosql_geolocation.config;

import com.portfolio.luisfmdc.model.StandardError;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.config.exception.ExternalApiException;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.config.exception.InvalidArgumentException;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.config.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e) {
        StandardError err = new StandardError(
                OffsetDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<StandardError> argumentNotValid(InvalidArgumentException e) {
        StandardError err = new StandardError(
                OffsetDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> constraintViolation(ConstraintViolationException e) {
        StandardError err = new StandardError(
                OffsetDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValid(MethodArgumentNotValidException e) {
        String fieldError = Objects.requireNonNull(e.getFieldError()).getField();
        StandardError err = new StandardError(
                OffsetDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Atributo " + fieldError + " inválido."
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<StandardError> duplicateKey(DuplicateKeyException e) {
        StandardError err = new StandardError(
                OffsetDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Já existe um local cadastrado com os dados informados."
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<StandardError> externalApiError(ExternalApiException e) {
        StandardError err = new StandardError(
                OffsetDateTime.now(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> genericException(Exception e) {
        String errorMessage = e.getMessage() == null ? "Ocorreu um erro inesperado no servidor." : e.getMessage();
        StandardError err = new StandardError(
                OffsetDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                errorMessage
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }
}
