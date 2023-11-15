package ru.github.abdullinru.bankapp.bankApp.dto;

import java.util.List;

public record ResponseBeneficiaryDto(Long id, String name, String pin, List<AccountDto> accounts) {
}
