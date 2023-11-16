package ru.github.abdullinru.bankapp.bankApp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.github.abdullinru.bankapp.bankApp.dto.ResponseAccountDto;
import ru.github.abdullinru.bankapp.bankApp.exception.AccountNotFoundException;
import ru.github.abdullinru.bankapp.bankApp.mapper.BeneficiaryMapper;
import ru.github.abdullinru.bankapp.bankApp.model.Account;
import ru.github.abdullinru.bankapp.bankApp.model.History;
import ru.github.abdullinru.bankapp.bankApp.model.OperationType;
import ru.github.abdullinru.bankapp.bankApp.repository.AccountRepository;
import ru.github.abdullinru.bankapp.bankApp.repository.HistoryRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class BalanceService {
    private final AccountRepository accountRepository;
    private final HistoryRepository historyRepository;
    private final BeneficiaryMapper mapper;

    public BalanceService(AccountRepository accountRepository, HistoryRepository historyRepository, BeneficiaryMapper mapper) {
        this.accountRepository = accountRepository;
        this.historyRepository = historyRepository;
        this.mapper = mapper;
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    //@Lock(LockModeType.PESSIMISTIC_READ)
    public ResponseAccountDto deposit(Long accountId, BigDecimal amount, boolean isTransfer) {
        chechAmount(amount);
        Account findAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("account is not found"));
        findAccount.setBalance(findAccount.getBalance().add(amount));
        accountRepository.save(findAccount);

        if (!isTransfer) {
            History historyLog = new History(
                    LocalDateTime.now(),
                    OperationType.deposit,
                    null,
                    accountId,
                    amount);
            historyRepository.save(historyLog);
        }

        return mapper.toResponseAccountDto(findAccount);
    }

    private void chechAmount(BigDecimal amount) {
        if (amount == null || amount.signum() == -1) {
            throw new IllegalArgumentException("incorrect value of deposit");
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    //@Lock(LockModeType.PESSIMISTIC_READ)
    public ResponseAccountDto withdraw(Long accountId, BigDecimal amount, String pin, boolean isTransfer) {
        chechAmount(amount);
        checkPin(pin);
        Account findAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account is not found"));

        comparePinCodes(pin, findAccount.getBeneficiary().getPin());
        if (findAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("not enouht money for withdraw");
        }
        findAccount.setBalance(findAccount.getBalance().subtract(amount));

        accountRepository.save(findAccount);


        if (!isTransfer) {
            History historyLog = new History(
                    LocalDateTime.now(),
                    OperationType.withdraw,
                    accountId,
                    null,
                    amount);
            historyRepository.save(historyLog);
        }

        return mapper.toResponseAccountDto(findAccount);
    }
    private void checkPin(String pinCode) {
        if (pinCode == null || !pinCode.matches("\\d{4}")) {
            throw new IllegalArgumentException("incorrect pincode");
        }
    }

    private void comparePinCodes(String pin1, String pin2) {
        if (!pin1.equals(pin2)) {
            throw new IllegalArgumentException("PIN codes do not match");
        }
    }
}
