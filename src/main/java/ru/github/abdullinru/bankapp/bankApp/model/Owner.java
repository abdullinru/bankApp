package ru.github.abdullinru.bankapp.bankApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

import java.util.List;

@Entity
@Table(name = "owners")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NamedEntityGraph(name = "owner_entity-graph", attributeNodes = @NamedAttributeNode("accounts"))
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String pin;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Account> accounts;

    public Owner(Long id, String name, String pin) {
        this.id = id;
        this.name = name;
        this.pin = pin;
    }

    public Owner() {

    }
}
