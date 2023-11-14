package ru.github.abdullinru.bankapp.bankApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.github.abdullinru.bankapp.bankApp.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
