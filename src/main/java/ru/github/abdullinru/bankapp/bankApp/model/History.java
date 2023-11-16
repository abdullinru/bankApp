package ru.github.abdullinru.bankapp.bankApp.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "histories")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    @Enumerated(value = EnumType.STRING)
    private OperationType type;
    private Long senderId;
    private Long receiverId;
    private BigDecimal changeBalance;

    public History(Long id, LocalDateTime dateTime, OperationType type, Long senderId, Long receiverId, BigDecimal changeBalance) {
        this.id = id;
        this.dateTime = dateTime;
        this.type = type;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.changeBalance = changeBalance;
    }

    public History() {

    }
}
