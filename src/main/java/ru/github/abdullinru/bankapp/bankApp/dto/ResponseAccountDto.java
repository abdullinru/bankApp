package ru.github.abdullinru.bankapp.bankApp.dto;

import ru.github.abdullinru.bankapp.bankApp.model.Beneficiary;

import java.math.BigDecimal;

public record ResponseAccountDto(Long id, String number, BigDecimal balance, String beneficiaryName) {
}
