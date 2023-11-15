package ru.github.abdullinru.bankapp.bankApp.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public Account(Long id, String number, BigDecimal balance, Owner owner) {
        this.id = id;
        this.number = number;
        this.balance = balance;
        this.owner = owner;
    }

    public String generateRandomAccountNumber() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int random = (int) (1000 + Math.random() * (9999 - 1000));
            result.append(random).append(" ");
        }
        return result.toString().trim();
    }
    public Account() {

    }
}
