package ru.github.abdullinru.bankapp.bankApp.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Beneficiary beneficiary;



    public String generateRandomAccountNumber() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int random = (int) (1000 + Math.random() * (9999 - 1000));
            result.append(random).append(" ");
        }
        return result.toString().trim();
    }
    public Account(String number, BigDecimal balance, Beneficiary beneficiary) {
        this.number = number;
        this.balance = balance;
        this.beneficiary = beneficiary;
    }
}
