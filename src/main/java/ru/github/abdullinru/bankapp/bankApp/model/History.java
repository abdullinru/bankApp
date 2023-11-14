package ru.github.abdullinru.bankapp.bankApp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "histories")
@NoArgsConstructor
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
    private Long receiveId;


    private enum OperationType {
        deposit, withdraw
    }

}
