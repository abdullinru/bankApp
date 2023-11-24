package ru.github.abdullinru.bankapp.bankApp.controller.controllerAdvice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.github.abdullinru.bankapp.bankApp.exception.AccountNotFoundException;
import ru.github.abdullinru.bankapp.bankApp.exception.BeneficiaryNotFoundException;

@ControllerAdvice
@Slf4j
public class MyExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handlerException (IllegalArgumentException e) {
        log.error("Incorrect method parametr: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(String.format("Error: %s", e.getMessage()));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handlerException (DataIntegrityViolationException e) {
        log.error("Error: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(String.format("Error: %s", e.getMessage()));
    }
    @ExceptionHandler(BeneficiaryNotFoundException.class)
    public ResponseEntity<String> handlerException (BeneficiaryNotFoundException e) {
        log.error("Error: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(String.format("Error: %s", e.getMessage()));
    }
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handlerException (AccountNotFoundException e) {
        log.error("Error: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(String.format("Error: %s", e.getMessage()));
    }
}