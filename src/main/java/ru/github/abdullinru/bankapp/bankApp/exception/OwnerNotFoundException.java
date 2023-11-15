package ru.github.abdullinru.bankapp.bankApp.exception;

public class OwnerNotFoundException extends RuntimeException{
    public OwnerNotFoundException(String message) {
        super(message);
    }
}
