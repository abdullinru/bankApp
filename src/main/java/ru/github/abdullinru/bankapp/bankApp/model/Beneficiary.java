package ru.github.abdullinru.bankapp.bankApp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "beneficiaries")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NamedEntityGraph(name = "beneficiary_entity-graph", attributeNodes = @NamedAttributeNode("accounts"))
public class Beneficiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String pin;

    @OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL)
    private List<Account> accounts;

    public Beneficiary(String name, String pin) {
        this.name = name;
        this.pin = pin;
    }

    public Beneficiary() {

    }
}
