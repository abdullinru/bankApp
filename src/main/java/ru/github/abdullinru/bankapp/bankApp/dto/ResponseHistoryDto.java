package ru.github.abdullinru.bankapp.bankApp.dto;

import ru.github.abdullinru.bankapp.bankApp.model.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ResponseHistoryDto(Long id, LocalDateTime dateTime, OperationType type, Long senderId, Long receiverId, BigDecimal changeBalance) {
}
