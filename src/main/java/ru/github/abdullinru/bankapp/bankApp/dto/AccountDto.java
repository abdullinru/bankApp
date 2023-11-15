package ru.github.abdullinru.bankapp.bankApp.dto;

import ru.github.abdullinru.bankapp.bankApp.model.Owner;

import java.math.BigDecimal;

public record AccountDto(String number, BigDecimal balance) {
}
