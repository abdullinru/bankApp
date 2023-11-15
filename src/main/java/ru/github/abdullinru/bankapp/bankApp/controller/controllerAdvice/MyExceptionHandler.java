package ru.github.abdullinru.bankapp.bankApp.controller.controllerAdvice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.github.abdullinru.bankapp.bankApp.exception.AccountNotFoundException;
import ru.github.abdullinru.bankapp.bankApp.exception.BeneficiaryNotFoundException;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handlerException (IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(String.format("Error: %s", e.getMessage()));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handlerException (DataIntegrityViolationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(String.format("Error: %s", e.getMessage()));
    }
    @ExceptionHandler(BeneficiaryNotFoundException.class)
    public ResponseEntity<String> handlerException (BeneficiaryNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(String.format("Error: %s", e.getMessage()));
    }
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handlerException (AccountNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(String.format("Error: %s", e.getMessage()));
    }
}