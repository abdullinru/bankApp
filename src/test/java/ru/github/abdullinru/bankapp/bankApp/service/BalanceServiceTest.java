package ru.github.abdullinru.bankapp.bankApp.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.github.abdullinru.bankapp.bankApp.dto.ResponseAccountDto;
import ru.github.abdullinru.bankapp.bankApp.exception.AccountNotFoundException;
import ru.github.abdullinru.bankapp.bankApp.mapper.BeneficiaryMapper;
import ru.github.abdullinru.bankapp.bankApp.model.Account;
import ru.github.abdullinru.bankapp.bankApp.model.Beneficiary;
import ru.github.abdullinru.bankapp.bankApp.model.History;
import ru.github.abdullinru.bankapp.bankApp.model.OperationType;
import ru.github.abdullinru.bankapp.bankApp.repository.AccountRepository;
import ru.github.abdullinru.bankapp.bankApp.repository.HistoryRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private HistoryRepository historyRepository;
    @Spy
    private BeneficiaryMapper mapper;
    @InjectMocks
    private BalanceService balanceService;

    @Test
    public void depositPositiveTest() {

        boolean isTransfer = false;
        long accountId = 1L;
        BigDecimal amount = BigDecimal.valueOf(10);
        BigDecimal currentBalance = BigDecimal.valueOf(100);
        BigDecimal updateBalance = BigDecimal.valueOf(110);
        Beneficiary ruslan = new Beneficiary("ruslan", "1111");
        Account ruslanAccount = new Account("1111 2222 3333 4444", currentBalance, ruslan);
        History log1 = new History( LocalDateTime.now(), OperationType.deposit, null, 200L, BigDecimal.valueOf(100));

        Mockito.when(historyRepository.save(any(History.class))).thenReturn(null);
        ResponseAccountDto responseAccountDto = new ResponseAccountDto(accountId, "1111 2222 3333 4444", updateBalance, "ruslan");

        Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(ruslanAccount));
        Mockito.when(accountRepository.save(ruslanAccount)).thenReturn(ruslanAccount);

        Mockito.when(mapper.toResponseAccountDto(ruslanAccount)).thenReturn(responseAccountDto);

        Assertions.assertThat(balanceService.deposit(accountId, amount, isTransfer)).isEqualTo(responseAccountDto);
    }

    @Test
    public void depositNegativeTestAmountIncorrect() {

        boolean isTransfer = false;
        long accountId = 1L;
        BigDecimal amount = BigDecimal.valueOf(-100);
        BigDecimal currentBalance = BigDecimal.valueOf(100);
        BigDecimal updateBalance = BigDecimal.valueOf(110);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> balanceService.deposit(accountId, amount, isTransfer));
    }
    @Test
    public void depositNegativeTestAccountNotFound() {

        boolean isTransfer = false;
        long accountId = 1L;
        BigDecimal amount = BigDecimal.valueOf(10);
        BigDecimal currentBalance = BigDecimal.valueOf(100);
        BigDecimal updateBalance = BigDecimal.valueOf(110);
        Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(AccountNotFoundException.class)
                .isThrownBy(() -> balanceService.deposit(accountId, amount, isTransfer));
    }

    @Test
    public void withdrawPositiveTest() {

        boolean isTransfer = false;
        long accountId = 1L;
        String requestPin = "1111";
        String pin = "1111";
        BigDecimal amount = BigDecimal.valueOf(10);
        BigDecimal currentBalance = BigDecimal.valueOf(100);
        BigDecimal updateBalance = BigDecimal.valueOf(90);
        Beneficiary ruslan = new Beneficiary("ruslan", pin);
        Account ruslanAccount = new Account("1111 2222 3333 4444", currentBalance, ruslan);
        //History log1 = new History( LocalDateTime.now(), OperationType.deposit, null, 200L, BigDecimal.valueOf(100));

        Mockito.when(historyRepository.save(any(History.class))).thenReturn(null);
        ResponseAccountDto responseAccountDto = new ResponseAccountDto(accountId, "1111 2222 3333 4444", updateBalance, "ruslan");

        Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(ruslanAccount));
        Mockito.when(accountRepository.save(ruslanAccount)).thenReturn(ruslanAccount);

        Mockito.when(mapper.toResponseAccountDto(ruslanAccount)).thenReturn(responseAccountDto);

        Assertions.assertThat(balanceService.withdraw(accountId, amount, requestPin,  isTransfer)).isEqualTo(responseAccountDto);
    }

    @Test
    public void withdrawNegativeTestAmountIncorrect() {

        boolean isTransfer = false;
        long accountId = 1L;
        String requestPin = "1111";
        BigDecimal amount = BigDecimal.valueOf(-100);
        BigDecimal currentBalance = BigDecimal.valueOf(100);
        BigDecimal updateBalance = BigDecimal.valueOf(90);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> balanceService.withdraw(accountId, amount, requestPin, isTransfer));
    }
    @Test
    public void withdrawNegativeTestAccountNotFound() {

        boolean isTransfer = false;
        long accountId = 1L;
        BigDecimal amount = BigDecimal.valueOf(10);
        String requestPin = "1111";
        BigDecimal currentBalance = BigDecimal.valueOf(100);
        BigDecimal updateBalance = BigDecimal.valueOf(90);
        Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(AccountNotFoundException.class)
                .isThrownBy(() -> balanceService.withdraw(accountId, amount, requestPin, isTransfer));
    }
    @Test
    public void withdrawNegativeTestPinNotMatch() {

        boolean isTransfer = false;
        long accountId = 1L;
        String requestPin = "2222";
        String pin = "1111";
        BigDecimal amount = BigDecimal.valueOf(10);
        BigDecimal currentBalance = BigDecimal.valueOf(100);
        BigDecimal updateBalance = BigDecimal.valueOf(90);
        Beneficiary ruslan = new Beneficiary("ruslan", pin);
        Account ruslanAccount = new Account("1111 2222 3333 4444", currentBalance, ruslan);

        Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(ruslanAccount));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> balanceService.withdraw(accountId, amount, requestPin, isTransfer));
    }
    @Test
    public void withdrawNegativeTestNotEnoughMoney() {

        boolean isTransfer = false;
        long accountId = 1L;
        String requestPin = "2222";
        String pin = "1111";
        BigDecimal amount = BigDecimal.valueOf(1000);
        BigDecimal currentBalance = BigDecimal.valueOf(100);
        BigDecimal updateBalance = BigDecimal.valueOf(90);
        Beneficiary ruslan = new Beneficiary("ruslan", pin);
        Account ruslanAccount = new Account("1111 2222 3333 4444", currentBalance, ruslan);

        Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(ruslanAccount));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> balanceService.withdraw(accountId, amount, requestPin, isTransfer));
    }

}