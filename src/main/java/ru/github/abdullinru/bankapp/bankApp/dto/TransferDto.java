package ru.github.abdullinru.bankapp.bankApp.dto;

import java.math.BigDecimal;

public record TransferDto (long senderId, long receiverId, BigDecimal amount, String pin){


}
