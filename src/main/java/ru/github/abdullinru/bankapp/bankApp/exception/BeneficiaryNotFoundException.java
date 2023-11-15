package ru.github.abdullinru.bankapp.bankApp.exception;

public class BeneficiaryNotFoundException extends RuntimeException{
    public BeneficiaryNotFoundException(String message) {
        super(message);
    }
}
