package ru.github.abdullinru.bankapp.bankApp.service;

import org.springframework.stereotype.Service;
import ru.github.abdullinru.bankapp.bankApp.model.Account;
import ru.github.abdullinru.bankapp.bankApp.repository.AccountRepository;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Account createAccount() {
        Account createAccount = new Account();
        createAccount.setBalance(BigDecimal.ZERO);
        createAccount.setNumber(createAccount.generateRandomAccountNumber());
        return createAccount;
    }
}
