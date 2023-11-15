package ru.github.abdullinru.bankapp.bankApp.dto;

import ru.github.abdullinru.bankapp.bankApp.model.Account;

import java.util.List;

public record ResponseOwnerDto(Long id, String name, String pin, List<AccountDto> accounts) {
}
