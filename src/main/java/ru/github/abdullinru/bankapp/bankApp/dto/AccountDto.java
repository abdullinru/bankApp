package ru.github.abdullinru.bankapp.bankApp.dto;

import java.math.BigDecimal;

public record AccountDto(String number, BigDecimal balance) {
}
