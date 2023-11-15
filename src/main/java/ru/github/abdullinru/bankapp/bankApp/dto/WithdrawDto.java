package ru.github.abdullinru.bankapp.bankApp.dto;

import java.math.BigDecimal;

public record WithdrawDto (long accountId, BigDecimal amount, String pin){

}
