package ru.github.abdullinru.bankapp.bankApp.dto;

import java.math.BigDecimal;

public record DepositDto(long accountId, BigDecimal amount) {

}
