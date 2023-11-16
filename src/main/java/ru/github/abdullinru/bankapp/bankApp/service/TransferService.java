package ru.github.abdullinru.bankapp.bankApp.service;

import org.springframework.stereotype.Service;
import ru.github.abdullinru.bankapp.bankApp.dto.ResponseAccountDto;
import ru.github.abdullinru.bankapp.bankApp.dto.TransferDto;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.github.abdullinru.bankapp.bankApp.model.History;
import ru.github.abdullinru.bankapp.bankApp.model.OperationType;
import ru.github.abdullinru.bankapp.bankApp.repository.HistoryRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransferService {
    private final BalanceService balanceService;
    private final HistoryRepository historyRepository;

    public TransferService(BalanceService balanceService, HistoryRepository historyRepository) {
        this.balanceService = balanceService;
        this.historyRepository = historyRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    //@Lock(LockModeType.PESSIMISTIC_READ)
    public ResponseAccountDto transfer(TransferDto transferDto) {
        Long senderId = transferDto.senderId();
        Long receiverId = transferDto.receiverId();
        BigDecimal amount = transferDto.amount();
        String pin = transferDto.pin();

        History historyLog = new History(
                LocalDateTime.now(),
                OperationType.transfer,
                senderId,
                receiverId,
                amount);
        historyRepository.save(historyLog);

        balanceService.withdraw(senderId, amount, pin, true);

        return balanceService.deposit(receiverId, amount, true);

    }
}